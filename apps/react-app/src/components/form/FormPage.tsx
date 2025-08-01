import * as React from 'react';
import Box from '@mui/material/Box';
import Button from '@mui/material/Button';
import CssBaseline from '@mui/material/CssBaseline';
import Grid from '@mui/material/Grid';
import Stack from '@mui/material/Stack';
import Step from '@mui/material/Step';
import StepLabel from '@mui/material/StepLabel';
import Stepper from '@mui/material/Stepper';
import Typography from '@mui/material/Typography';
import IconButton from '@mui/material/IconButton';
import Tooltip from '@mui/material/Tooltip';
import ChevronLeftRoundedIcon from '@mui/icons-material/ChevronLeftRounded';
import ChevronRightRoundedIcon from '@mui/icons-material/ChevronRightRounded';
import ClearIcon from '@mui/icons-material/Clear';
import Rule from './Rule';
import Info from './Info';
import Score from './Score';
import Comment from './Comment.tsx';
import AppTheme from '../../shared-theme/AppTheme';
import {useCallback, useEffect, useState} from "react";
import api from "../../lib/axios.ts";
import dayjs from 'dayjs';
import {usePresenters} from "../../hook/usePresenters.ts";
import {Presenter} from "../../types/presenter.ts";

const steps = ['README', 'Submit Ratings', 'Comments'];

function getCurrentWeek(startDateStr: string): string {
    const today = dayjs();
    const start = dayjs(startDateStr);
    let diffDays = today.diff(start, 'day');
    if(diffDays < 0) {
        diffDays = dayjs('2025-09-01').diff(start, 'days')
    }
    return (Math.floor(diffDays / 7) + 1).toString();
}

function getMaxRatings(totalPeople: number) {
    const maxA = totalPeople <= 7 ? 1 : 2;
    const maxC = totalPeople <= 7 ? 1 : 2;
    const maxB = totalPeople - 2;

    return { A: maxA, B: maxB, C: maxC };
}

function getStepContent(
    step: number,
    presenters: Presenter[],
    maxRatings: { A: number; B: number; C: number },
    scores: Record<string, string>,
    onScoreChange: (id: string, score: string) => void,
    error: string,
    handleCountChange: (newCount: { A: number; B: number; C: number }) => void
) {
    console.log('getStepContent 收到的 presenters:', {
        presenters,
        type: typeof presenters,
        isArray: Array.isArray(presenters)
    });

    switch (step) {
        case 0:
            return <Rule presenterCount={presenters.length} maxRatings={maxRatings} />;
        case 1:
            return (
                <Score
                    presenters={presenters}
                    scores={scores}
                    onScoreChange={onScoreChange}
                    error={error}
                    onCountChange={handleCountChange}
                    maxRatings={maxRatings}
                />
            );
        case 2:
            return <Comment />;
        default:
            throw new Error('Unknown step');
    }
}


const STORAGE_KEY = 'presenter_scores';
const STEP_STORAGE_KEY = 'presenter_step';

export default function FormPage(props: { disableCustomTheme?: boolean }) {
    const token = localStorage.getItem('token');

    const validateToken = () => {
        if (!token) {
            setError('請重新登入');
            return;
        }
    }

    const week = getCurrentWeek('2025-09-01'); // 114 Fall 第一周起始日

    const { presenters } = usePresenters(week, token);

    const maxRatings = getMaxRatings(presenters.length);

    const [userId, setUserId] = useState<string>('');

    React.useEffect(() => {
        validateToken();

        api.get('/api/auth/me',{
            headers: {
                Authorization: `Bearer ${token}`,
            },
        })
            .then((res) => {
                setUserId(res.data.username);
            })
            .catch((err) => {
                console.error('取得登入資訊失敗', err);
                setError('無法取得使用者資訊，請重新登入');
            });
    }, []);

    const [count, setCount] = useState({A: 0, B: 0, C: 0});

    const handleCountChange = useCallback((newCount: { A: number; B: number; C: number }) => {
        setCount(newCount);
    }, []);

    // 從 sessionStorage 恢復狀態
    const [activeStep, setActiveStep] = React.useState(() => {
        if (typeof window !== 'undefined') {
            const savedStep = sessionStorage.getItem(STEP_STORAGE_KEY);
            return savedStep ? parseInt(savedStep, 10) : 0;
        }
        return 0;
    });

    const [scores, setScores] = React.useState<Record<string, string>>(() => {
        if (typeof window !== 'undefined') {
            const savedScores = sessionStorage.getItem(STORAGE_KEY);
            return savedScores ? JSON.parse(savedScores) : {};
        }
        return {};
    });

    const [error, setError] = React.useState('');
    const [hasRestoredData, setHasRestoredData] = React.useState(false);

    // 檢查是否有恢復的資料
    React.useEffect(() => {
        if (typeof window !== 'undefined') {
            const savedScores = sessionStorage.getItem(STORAGE_KEY);
            const savedStep = sessionStorage.getItem(STEP_STORAGE_KEY);
            if (savedScores && Object.keys(JSON.parse(savedScores)).length > 0) {
                setHasRestoredData(true);
            }
        }
    }, []);

    // 當 scores 變化時自動保存到 sessionStorage
    React.useEffect(() => {
        if (typeof window !== 'undefined') {
            sessionStorage.setItem(STORAGE_KEY, JSON.stringify(scores));
        }
    }, [scores]);

    // 當 activeStep 變化時自動保存到 sessionStorage
    React.useEffect(() => {
        if (typeof window !== 'undefined') {
            sessionStorage.setItem(STEP_STORAGE_KEY, activeStep.toString());
        }
    }, [activeStep]);

    const handleScoreChange = (studentId: string, score: string) => {
        setScores((prev) => ({...prev, [studentId]: score}));
        setError(''); // 清除錯誤訊息
        setHasRestoredData(false); // 清除恢復資料提示
    };

    {getStepContent(
        activeStep,
        presenters,
        maxRatings,
        scores,
        handleScoreChange,
        error,
        handleCountChange
    )}

    const clearAllData = () => {
        if (confirm('確定要清除所有評分資料嗎？此操作無法復原。')) {
            setScores({});
            setActiveStep(0);
            setError('');
            setHasRestoredData(false);
            if (typeof window !== 'undefined') {
                sessionStorage.removeItem(STORAGE_KEY);
                sessionStorage.removeItem(STEP_STORAGE_KEY);
            }
        }
    };

    const validateScores = () => {
        // 檢查是否所有人都有評分
        const allFilled = presenters.every((p) => scores[p.studentId]);
        if (!allFilled) {
            setError('請為每位 presenter 給分。');
            return false;
        }

        // const count = getScoreCount();

        // 檢查至少一位 A
        if (count.A < 1) {
            setError('請至少選擇一位 A。');
            return false;
        }

        // 檢查至少一位 C
        if (count.C < 1) {
            setError('請至少選擇一位 C。');
            return false;
        }

        return true;
    };

    const handleNext = () => {
        if (activeStep === 1) {
            // 在評分頁面點擊下一步時進行驗證
            if (!validateScores()) {
                return;
            }
        }
        setActiveStep(activeStep + 1);
    };

    const handleBack = () => {
        setActiveStep(activeStep - 1);
        setError(''); // 返回時清除錯誤訊息
    };

    const handleSubmit = async () => {
        if (!validateScores()) return;

        setError('');

        const payload = {
            submitterId: userId,
            week: week,
            scores: Object.entries(scores).map(([presenterId, score]) => ({
                presenterId,
                score,
            })),
            submitDateTime: "20250801",
            comment: "front-end-test"
        };

        try {
            const res = await api.post('/api/form-processing/', payload);

            console.log('✅ Submitted:', payload);
            console.log('Response from server:', res.data);

            // 清除 sessionStorage
            sessionStorage.removeItem(STORAGE_KEY);
            sessionStorage.removeItem(STEP_STORAGE_KEY);

            alert('成功送出');
            setActiveStep(activeStep + 1);

        } catch (err) {
            console.error('提交失敗', err);
            setError('送出失敗，請稍後再試');
        }
    };

    return (
        <AppTheme {...props}>
            <CssBaseline enableColorScheme/>

            <Grid
                container
                sx={{
                    height: {
                        xs: '100%',
                        sm: 'calc(100dvh - var(--template-frame-height, 0px))',
                    },
                    mt: {
                        xs: 4,
                        sm: 0,
                    },
                }}
            >
                <Grid
                    size={{xs: 12, sm: 4, lg: 3}}
                    sx={{
                        display: {xs: 'none', md: 'flex'},
                        flexDirection: 'column',
                        backgroundColor: 'background.paper',
                        borderRight: {sm: 'none', md: '1px solid'},
                        borderColor: {sm: 'none', md: 'divider'},
                        alignItems: 'start',
                        py: 16,
                        px: 10,
                        gap: 4,
                    }}
                >
                    <Box
                        sx={{
                            display: 'flex',
                            flexDirection: 'column',
                            flexGrow: 1,
                            width: '100%',
                            maxWidth: 500,
                        }}
                    >
                        <Info count={count} maxRatings={maxRatings} week={week} />
                    </Box>
                </Grid>

                <Grid
                    size={{sm: 12, md: 8, lg: 9}}
                    sx={{
                        display: 'flex',
                        flexDirection: 'column',
                        maxWidth: '100%',
                        width: '100%',
                        backgroundColor: {xs: 'transparent', sm: 'background.default'},
                        alignItems: 'center',
                        pt: {xs: 0, sm: 16},
                        px: {xs: 2, sm: 10},
                        gap: {xs: 4, md: 8},
                    }}
                >
                    <Box
                        sx={{
                            display: 'flex',
                            justifyContent: {sm: 'space-between', md: 'flex-end'},
                            alignItems: 'center',
                            width: '100%',
                            maxWidth: {sm: '100%', md: '50%'},
                        }}
                    >
                        <Box
                            sx={{
                                display: {xs: 'none', md: 'flex'},
                                flexDirection: 'column',
                                justifyContent: 'space-between',
                                alignItems: 'flex-end',
                                flexGrow: 1,
                            }}
                        >
                            <Stepper
                                id="desktop-stepper"
                                activeStep={activeStep}
                                sx={{width: '100%', height: 40}}
                            >
                                {steps.map((label) => (
                                    <Step
                                        sx={{':first-child': {pl: 0}, ':last-child': {pr: 0}}}
                                        key={label}
                                    >
                                        <StepLabel>{label}</StepLabel>
                                    </Step>
                                ))}
                            </Stepper>
                        </Box>
                    </Box>
                    <Box
                        sx={{
                            display: 'flex',
                            flexDirection: 'column',
                            flexGrow: 1,
                            width: '100%',
                            maxWidth: {sm: '100%', md: '50%'},
                            // maxHeight: '720px',
                            gap: {xs: 5, md: 'none'},
                        }}
                    >
                        <Stepper
                            id="mobile-stepper"
                            activeStep={activeStep}
                            alternativeLabel
                            sx={{display: {sm: 'flex', md: 'none'}}}
                        >
                            {steps.map((label) => (
                                <Step
                                    sx={{
                                        ':first-child': {pl: 0},
                                        ':last-child': {pr: 0},
                                        '& .MuiStepConnector-root': {top: {xs: 6, sm: 12}},
                                    }}
                                    key={label}
                                >
                                    <StepLabel
                                        sx={{'.MuiStepLabel-labelContainer': {maxWidth: '70px'}}}
                                    >
                                        {label}
                                    </StepLabel>
                                </Step>
                            ))}
                        </Stepper>

                        {activeStep === steps.length ? (
                            <Stack spacing={2} useFlexGap>
                                <Typography variant="h1">📦</Typography>
                                <Typography variant="h5">Thank you for your rating!</Typography>
                                <Typography variant="body1" sx={{color: 'text.secondary'}}>
                                    Your rating has been submitted successfully.
                                    We have recorded your evaluation
                                    and will process the results accordingly.
                                </Typography>
                                {/*<Button*/}
                                {/*    variant="contained"*/}
                                {/*    sx={{alignSelf: 'start', width: {xs: '100%', sm: 'auto'}}}*/}
                                {/*>*/}
                                {/*    Go to dashboard*/}
                                {/*</Button>*/}
                            </Stack>
                        ) : (
                            <React.Fragment>
                                {getStepContent(activeStep, scores, handleScoreChange, error, handleCountChange)}
                                <Box
                                    sx={[
                                        {
                                            display: 'flex',
                                            flexDirection: {xs: 'column-reverse', sm: 'row'},
                                            alignItems: 'end',
                                            flexGrow: 1,
                                            gap: 1,
                                            pb: {xs: 12, sm: 0},
                                            mt: {xs: 2, sm: 0},
                                            mb: '60px',
                                        },
                                        activeStep !== 0
                                            ? {justifyContent: 'space-between'}
                                            : {justifyContent: 'flex-end'},
                                    ]}
                                >
                                    {activeStep !== 0 && (
                                        <Button
                                            startIcon={<ChevronLeftRoundedIcon/>}
                                            onClick={handleBack}
                                            variant="text"
                                            sx={{display: {xs: 'none', sm: 'flex'}}}
                                        >
                                            Previous
                                        </Button>
                                    )}
                                    {activeStep !== 0 && (
                                        <Button
                                            startIcon={<ChevronLeftRoundedIcon/>}
                                            onClick={handleBack}
                                            variant="outlined"
                                            fullWidth
                                            sx={{display: {xs: 'flex', sm: 'none'}}}
                                        >
                                            Previous
                                        </Button>
                                    )}
                                    <Box sx={{ display: 'flex', justifyContent: 'space-between', minWidth: '160px'}}>
                                        {(Object.keys(scores).length > 0 && activeStep === 1) && (
                                            <Tooltip title="清除所有評分資料">
                                                <IconButton
                                                    onClick={clearAllData}
                                                    sx={{
                                                        bgcolor: 'background.paper',
                                                        '&:hover': {bgcolor: 'error.light'}
                                                    }}
                                                >
                                                    <ClearIcon/>
                                                </IconButton>
                                            </Tooltip>
                                        )}
                                        <Button
                                            variant="contained"
                                            endIcon={<ChevronRightRoundedIcon/>}
                                            onClick={activeStep === steps.length - 1 ? handleSubmit : handleNext}
                                            sx={{width: {xs: '100%', sm: 'fit-content'}}}
                                        >
                                            {activeStep === steps.length - 1 ? 'Submit' : 'Next'}
                                        </Button>
                                    </Box>
                                </Box>
                            </React.Fragment>
                        )}
                    </Box>
                </Grid>
            </Grid>
        </AppTheme>
    );
}