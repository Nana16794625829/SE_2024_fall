import React, {useEffect, useState} from 'react';
import {
    Box,
    Typography,
    RadioGroup,
    FormControlLabel,
    Radio,
    Paper,
    Divider,
    Alert,
    Chip,
    Grid,
} from '@mui/material';

interface Presenter {
    order: string;
    studentId: string;
    name: string;
}

interface ScoreProps {
    presenters: Presenter[];
    scores: Record<string, string>;
    onScoreChange: (studentId: string, score: string) => void;
    error: string;
    onCountChange: (count: { A: number; B: number; C: number }) => void;
}

const maxA = 2;
const maxB = 6;
const maxC = 2;

export default function Score({presenters, scores, onScoreChange, error, onCountChange}: ScoreProps) {
    const [count, setCount] = useState({A: 0, B: 0, C: 0});

    // useEffect(() => {
    //     const counter = {A: 0, B: 0, C: 0};
    //     Object.values(scores).forEach((score) => {
    //         if (score === 'A') counter.A += 1;
    //         if (score === 'B') counter.B += 1;
    //         if (score === 'C') counter.C += 1;
    //     });
    //     setCount(counter);
    // }, [scores]);

    useEffect(() => {
        const counter = {A: 0, B: 0, C: 0};
        Object.values(scores).forEach((score) => {
            if (score === 'A') counter.A += 1;
            if (score === 'B') counter.B += 1;
            if (score === 'C') counter.C += 1;
        });
        setCount(counter)
        onCountChange(counter);  // 通知 FormPage 更新 count
    }, [scores, onCountChange]);

    const handleChange = (studentId: string, score: string) => {
        onScoreChange(studentId, score);
    };

    // const getChipColor = (grade: 'A' | 'B' | 'C', currentCount: number, maxCount: number) => {
    //     if (currentCount >= maxCount) return 'error';
    //     if (currentCount === maxCount - 1) return 'warning';
    //     return 'success';
    // };

    return (

        <Box display="flex" flexDirection="column" gap={3}>
            {/* 顯示錯誤訊息 */}
            {error && <Alert severity="warning">{error}</Alert>}

            {/* 顯示目前評分統計 */}
            <Paper sx={{p: 2, bgcolor: 'background.default'}}>
                <Box mt={2}>
                    <Typography variant="h6" color="text.secondary">
                        規則提醒
                    </Typography>
                    <Typography variant="body2" color="text.secondary">
                        所有人都要評分，且至少一位 A 與至少一位 C
                    </Typography>
                    {Object.keys(scores).length > 0 && (
                        <Typography variant="body2" color="success.main" sx={{mt: 0.5}}>
                            您的評分進度會自動保存，刷新頁面後可恢復
                        </Typography>
                    )}
                </Box>
            </Paper>

            {/* 評分表單 */}
            {presenters.map((presenter, index) => (
                <Paper
                    key={presenter.studentId}
                    sx={{
                        p: 3,
                        border: scores[presenter.studentId] ? '2px solid' : '1px solid',
                        borderColor: scores[presenter.studentId] ? 'primary.main' : 'divider',
                        transition: 'border-color 0.2s ease-in-out',
                        '&:hover': {
                            borderColor: 'primary.light',
                        }
                    }}
                >
                    <Box
                        display="flex"
                        alignItems="center"
                        justifyContent="space-between"
                        flexWrap="wrap"
                        gap={2}
                        sx={{
                            // 在小螢幕時確保有足夠空間
                            '@media (max-width: 900px)': {
                                flexDirection: 'column',
                                alignItems: 'stretch',
                                '& > :first-of-type': {
                                    justifyContent: 'center'
                                },
                                '& > :last-of-type': {
                                    justifyContent: 'center'
                                }
                            }
                        }}
                    >
                        {/* 學生資訊 */}
                        <Box display="flex" alignItems="center" gap={2} flexWrap="wrap" sx={{minWidth: 'fit-content'}}>
                            <Typography
                                variant="body1"
                                sx={{
                                    minWidth: '10px',
                                    fontWeight: 'medium',
                                    whiteSpace: 'nowrap'
                                }}
                            >
                                {presenter.order}
                            </Typography>
                            <Divider orientation="vertical" flexItem/>
                            <Typography
                                variant="body1"
                                sx={{
                                    minWidth: '120px',
                                    fontWeight: 'medium',
                                    whiteSpace: 'nowrap'
                                }}
                            >
                                {presenter.name}
                            </Typography>
                        </Box>

                        {/* 評分選項 */}
                        <RadioGroup
                            row
                            value={scores[presenter.studentId] || ''}
                            onChange={(e) => handleChange(presenter.studentId, e.target.value)}
                            sx={{
                                gap: 1,
                                flexWrap: 'nowrap', // 防止換行
                                minWidth: 'fit-content'
                            }}
                        >
                            <FormControlLabel
                                value="A"
                                control={
                                    <Radio
                                        sx={{
                                            '&.Mui-checked': {
                                                color: 'success.main',
                                            },
                                        }}
                                    />
                                }
                                label={
                                    <Box display="flex" alignItems="center" gap={0.3} sx={{whiteSpace: 'nowrap'}}>
                                        <Typography variant="body1" fontWeight="bold">A</Typography>
                                        {count.A >= maxA && scores[presenter.studentId] !== 'A' && (
                                            <Typography variant="caption" color="error" sx={{fontSize: '0.65rem'}}>
                                                (已滿)
                                            </Typography>
                                        )}
                                    </Box>
                                }
                                disabled={count.A >= maxA && scores[presenter.studentId] !== 'A'}
                                sx={{
                                    '& .MuiFormControlLabel-label': {
                                        fontWeight: scores[presenter.studentId] === 'A' ? 'bold' : 'normal',
                                    },
                                    minWidth: 'fit-content',
                                    mr: 0.5
                                }}
                            />
                            <FormControlLabel
                                value="B"
                                control={
                                    <Radio
                                        sx={{
                                            '&.Mui-checked': {
                                                color: 'warning.main',
                                            },
                                        }}
                                    />
                                }
                                label={
                                    <Box display="flex" alignItems="center" gap={0.3} sx={{whiteSpace: 'nowrap'}}>
                                        <Typography variant="body1" fontWeight="bold">B</Typography>
                                        {count.B >= maxB && scores[presenter.studentId] !== 'B' && (
                                            <Typography variant="caption" color="error" sx={{fontSize: '0.65rem'}}>
                                                (已滿)
                                            </Typography>
                                        )}
                                    </Box>
                                }
                                disabled={count.B >= maxB && scores[presenter.studentId] !== 'B'}
                                sx={{
                                    '& .MuiFormControlLabel-label': {
                                        fontWeight: scores[presenter.studentId] === 'B' ? 'bold' : 'normal',
                                    },
                                    minWidth: 'fit-content',
                                    mr: 0.5
                                }}
                            />
                            <FormControlLabel
                                value="C"
                                control={
                                    <Radio
                                        sx={{
                                            '&.Mui-checked': {
                                                color: 'error.main',
                                            },
                                        }}
                                    />
                                }
                                label={
                                    <Box display="flex" alignItems="center" gap={0.3} sx={{whiteSpace: 'nowrap'}}>
                                        <Typography variant="body1" fontWeight="bold">C</Typography>
                                        {count.C >= maxC && scores[presenter.studentId] !== 'C' && (
                                            <Typography variant="caption" color="error" sx={{fontSize: '0.65rem'}}>
                                                (已滿)
                                            </Typography>
                                        )}
                                    </Box>
                                }
                                disabled={count.C >= maxC && scores[presenter.studentId] !== 'C'}
                                sx={{
                                    '& .MuiFormControlLabel-label': {
                                        fontWeight: scores[presenter.studentId] === 'C' ? 'bold' : 'normal',
                                    },
                                    minWidth: 'fit-content'
                                }}
                            />
                        </RadioGroup>
                    </Box>

                    {/* 顯示目前選擇的評分 */}
                    {/*{scores[presenter.studentId] && (*/}
                    {/*    <Box mt={1} display="flex" justifyContent="flex-end">*/}
                    {/*        <Chip*/}
                    {/*            label={`已選擇：${scores[presenter.studentId]} 等級`}*/}
                    {/*            size="small"*/}
                    {/*            color={*/}
                    {/*                scores[presenter.studentId] === 'A' ? 'success' :*/}
                    {/*                    scores[presenter.studentId] === 'B' ? 'warning' : 'error'*/}
                    {/*            }*/}
                    {/*            variant="filled"*/}
                    {/*        />*/}
                    {/*    </Box>*/}
                    {/*)}*/}
                </Paper>
            ))}
        </Box>
    );
}