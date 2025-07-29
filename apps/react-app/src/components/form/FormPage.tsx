import * as React from 'react';
import Box from '@mui/material/Box';
import Button from '@mui/material/Button';
import Card from '@mui/material/Card';
import CardContent from '@mui/material/CardContent';
import CssBaseline from '@mui/material/CssBaseline';
import Grid from '@mui/material/Grid';
import Stack from '@mui/material/Stack';
import Step from '@mui/material/Step';
import StepLabel from '@mui/material/StepLabel';
import Stepper from '@mui/material/Stepper';
import Typography from '@mui/material/Typography';
import Alert from '@mui/material/Alert';
import ChevronLeftRoundedIcon from '@mui/icons-material/ChevronLeftRounded';
import ChevronRightRoundedIcon from '@mui/icons-material/ChevronRightRounded';
import Rule from './Rule';
import Info from './Info';
import InfoMobile from './InfoMobile';
import Score from './Score';
import Review from './Review';
import AppTheme from '../../shared-theme/AppTheme';
import ColorModeIconDropdown from '../../shared-theme/ColorModeIconDropdown';

const steps = ['README', 'Submit ratings'];

const presenters = [
  { studentId: '112552001', name: '李小龍' },
  { studentId: '112552002', name: '張曼玉' },
  { studentId: '112552003', name: '周杰倫' },
  { studentId: '112552004', name: '林志玲' },
  { studentId: '112552005', name: '王力宏' },
  { studentId: '112552006', name: '蔡依林' },
  { studentId: '112552007', name: '謝金燕' },
  { studentId: '112552008', name: '黃鴻升' },
];

function getStepContent(
  step: number,
  scores: Record<string, string>,
  onScoreChange: (id: string, score: string) => void,
  error: string
) {
  switch (step) {
    case 0:
      return <Rule />;
    case 1:
      return (
        <Score 
          presenters={presenters}
          scores={scores}
          onScoreChange={onScoreChange}
          error={error}
        />
      );
    case 2:
      return <Review />;
    default:
      throw new Error('Unknown step');
  }
}

export default function FormPage(props: { disableCustomTheme?: boolean }) {
  const [activeStep, setActiveStep] = React.useState(0);
  const [scores, setScores] = React.useState<Record<string, string>>({});
  const [error, setError] = React.useState('');

  // 計算各等級數量
  const getScoreCount = () => {
    const counter = { A: 0, B: 0, C: 0 };
    Object.values(scores).forEach((score) => {
      if (score === 'A') counter.A += 1;
      if (score === 'B') counter.B += 1;
      if (score === 'C') counter.C += 1;
    });
    return counter;
  };

  const handleScoreChange = (studentId: string, score: string) => {
    setScores((prev) => ({ ...prev, [studentId]: score }));
    setError(''); // 清除錯誤訊息
  };

  const validateScores = () => {
    // 檢查是否所有人都有評分
    const allFilled = presenters.every((p) => scores[p.studentId]);
    if (!allFilled) {
      setError('⚠️ 請為每位 presenter 給分。');
      return false;
    }

    const count = getScoreCount();
    
    // 檢查至少一位 A
    if (count.A < 1) {
      setError('⚠️ 請至少選擇一位 A。');
      return false;
    }
    
    // 檢查至少一位 C
    if (count.C < 1) {
      setError('⚠️ 請至少選擇一位 C。');
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

  const handleSubmit = () => {
    if (validateScores()) {
      setError('');
      console.log('✅ Submitted scores:', scores);
      
      // 顯示詳細的評分結果
      const count = getScoreCount();
      console.log('📊 Score Summary:', count);
      console.log('📋 Detailed Results:');
      presenters.forEach((presenter) => {
        console.log(`${presenter.name} (${presenter.studentId}): ${scores[presenter.studentId]}`);
      });
      
      alert('✅ 成功送出');
      setActiveStep(activeStep + 1);
    }
  };

  return (
    <AppTheme {...props}>
      <CssBaseline enableColorScheme />
      <Box sx={{ position: 'fixed', top: '1rem', right: '1rem' }}>
        <ColorModeIconDropdown />
      </Box>

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
          size={{ xs: 12, sm: 5, lg: 4 }}
          sx={{
            display: { xs: 'none', md: 'flex' },
            flexDirection: 'column',
            backgroundColor: 'background.paper',
            borderRight: { sm: 'none', md: '1px solid' },
            borderColor: { sm: 'none', md: 'divider' },
            alignItems: 'start',
            pt: 16,
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
            <Info totalPrice={activeStep >= 2 ? '$144.97' : '$134.98'} />
          </Box>
        </Grid>
        
        <Grid
          size={{ sm: 12, md: 7, lg: 8 }}
          sx={{
            display: 'flex',
            flexDirection: 'column',
            maxWidth: '100%',
            width: '100%',
            backgroundColor: { xs: 'transparent', sm: 'background.default' },
            alignItems: 'start',
            pt: { xs: 0, sm: 16 },
            px: { xs: 2, sm: 10 },
            gap: { xs: 4, md: 8 },
          }}
        >
          <Box
            sx={{
              display: 'flex',
              justifyContent: { sm: 'space-between', md: 'flex-end' },
              alignItems: 'center',
              width: '100%',
              maxWidth: { sm: '100%', md: 600 },
            }}
          >
            <Box
              sx={{
                display: { xs: 'none', md: 'flex' },
                flexDirection: 'column',
                justifyContent: 'space-between',
                alignItems: 'flex-end',
                flexGrow: 1,
              }}
            >
              <Stepper
                id="desktop-stepper"
                activeStep={activeStep}
                sx={{ width: '100%', height: 40 }}
              >
                {steps.map((label) => (
                  <Step
                    sx={{ ':first-child': { pl: 0 }, ':last-child': { pr: 0 } }}
                    key={label}
                  >
                    <StepLabel>{label}</StepLabel>
                  </Step>
                ))}
              </Stepper>
            </Box>
          </Box>
          
          <Card sx={{ display: { xs: 'flex', md: 'none' }, width: '100%' }}>
            <CardContent
              sx={{
                display: 'flex',
                width: '100%',
                alignItems: 'center',
                justifyContent: 'space-between',
              }}
            >
              <div>
                <Typography variant="subtitle2" gutterBottom>
                  Selected products
                </Typography>
                <Typography variant="body1">
                  {activeStep >= 2 ? '$144.97' : '$134.98'}
                </Typography>
              </div>
              <InfoMobile totalPrice={activeStep >= 2 ? '$144.97' : '$134.98'} />
            </CardContent>
          </Card>
          
          <Box
            sx={{
              display: 'flex',
              flexDirection: 'column',
              flexGrow: 1,
              width: '100%',
              maxWidth: { sm: '100%', md: 600 },
              maxHeight: '720px',
              gap: { xs: 5, md: 'none' },
            }}
          >
            <Stepper
              id="mobile-stepper"
              activeStep={activeStep}
              alternativeLabel
              sx={{ display: { sm: 'flex', md: 'none' } }}
            >
              {steps.map((label) => (
                <Step
                  sx={{
                    ':first-child': { pl: 0 },
                    ':last-child': { pr: 0 },
                    '& .MuiStepConnector-root': { top: { xs: 6, sm: 12 } },
                  }}
                  key={label}
                >
                  <StepLabel
                    sx={{ '.MuiStepLabel-labelContainer': { maxWidth: '70px' } }}
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
                <Typography variant="body1" sx={{ color: 'text.secondary' }}>
                  Your rating has been submitted successfully.
                  <strong>&nbsp;#140396</strong>. We have recorded your evaluation
                  and will process the results accordingly.
                </Typography>
                <Button
                  variant="contained"
                  sx={{ alignSelf: 'start', width: { xs: '100%', sm: 'auto' } }}
                >
                  Go to dashboard
                </Button>
              </Stack>
            ) : (
              <React.Fragment>
                {getStepContent(activeStep, scores, handleScoreChange, error)}
                <Box
                  sx={[
                    {
                      display: 'flex',
                      flexDirection: { xs: 'column-reverse', sm: 'row' },
                      alignItems: 'end',
                      flexGrow: 1,
                      gap: 1,
                      pb: { xs: 12, sm: 0 },
                      mt: { xs: 2, sm: 0 },
                      mb: '60px',
                    },
                    activeStep !== 0
                      ? { justifyContent: 'space-between' }
                      : { justifyContent: 'flex-end' },
                  ]}
                >
                  {activeStep !== 0 && (
                    <Button
                      startIcon={<ChevronLeftRoundedIcon />}
                      onClick={handleBack}
                      variant="text"
                      sx={{ display: { xs: 'none', sm: 'flex' } }}
                    >
                      Previous
                    </Button>
                  )}
                  {activeStep !== 0 && (
                    <Button
                      startIcon={<ChevronLeftRoundedIcon />}
                      onClick={handleBack}
                      variant="outlined"
                      fullWidth
                      sx={{ display: { xs: 'flex', sm: 'none' } }}
                    >
                      Previous
                    </Button>
                  )}
                  <Button
                    variant="contained"
                    endIcon={<ChevronRightRoundedIcon />}
                    onClick={activeStep === steps.length - 1 ? handleSubmit : handleNext}
                    sx={{ width: { xs: '100%', sm: 'fit-content' } }}
                  >
                    {activeStep === steps.length - 1 ? 'Submit' : 'Next'}
                  </Button>
                </Box>
              </React.Fragment>
            )}
          </Box>
        </Grid>
      </Grid>
    </AppTheme>
  );
}