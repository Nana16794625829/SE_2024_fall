import React, { useEffect, useState } from 'react';
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
  studentId: string;
  name: string;
}

interface ScoreProps {
  presenters: Presenter[];
  scores: Record<string, string>;
  onScoreChange: (studentId: string, score: string) => void;
  error: string;
}

const maxA = 2;
const maxB = 6;
const maxC = 2;

export default function Score({ presenters, scores, onScoreChange, error }: ScoreProps) {
  const [count, setCount] = useState({ A: 0, B: 0, C: 0 });

  useEffect(() => {
    const counter = { A: 0, B: 0, C: 0 };
    Object.values(scores).forEach((score) => {
      if (score === 'A') counter.A += 1;
      if (score === 'B') counter.B += 1;
      if (score === 'C') counter.C += 1;
    });
    setCount(counter);
  }, [scores]);

  const handleChange = (studentId: string, score: string) => {
    onScoreChange(studentId, score);
  };

  const getChipColor = (grade: 'A' | 'B' | 'C', currentCount: number, maxCount: number) => {
    if (currentCount >= maxCount) return 'error';
    if (currentCount === maxCount - 1) return 'warning';
    return 'success';
  };

  return (
    <Box display="flex" flexDirection="column" gap={3}>
      {/* 顯示錯誤訊息 */}
      {error && <Alert severity="warning">{error}</Alert>}
      
      {/* 顯示目前評分統計 */}
      <Paper sx={{ p: 2, bgcolor: 'background.default' }}>
        <Typography variant="h6" gutterBottom>
          評分統計
        </Typography>
        <Grid container spacing={2}>
          <Grid item xs={4}>
            <Chip
              label={`A 等級: ${count.A}/${maxA}`}
              color={getChipColor('A', count.A, maxA)}
              variant="outlined"
              size="small"
            />
          </Grid>
          <Grid item xs={4}>
            <Chip
              label={`B 等級: ${count.B}/${maxB}`}
              color={getChipColor('B', count.B, maxB)}
              variant="outlined"
              size="small"
            />
          </Grid>
          <Grid item xs={4}>
            <Chip
              label={`C 等級: ${count.C}/${maxC}`}
              color={getChipColor('C', count.C, maxC)}
              variant="outlined"
              size="small"
            />
          </Grid>
        </Grid>
        
        <Box mt={2}>
          <Typography variant="body2" color="text.secondary">
            ✅ 規則提醒：所有人都要評分，至少一位 A，至少一位 C
          </Typography>
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
          >
            {/* 學生資訊 */}
            <Box display="flex" alignItems="center" gap={2} flexWrap="wrap">
              <Typography 
                variant="body1" 
                sx={{ 
                  minWidth: '140px',
                  fontWeight: 'medium',
                }}
              >
                學號：{presenter.studentId}
              </Typography>
              <Divider orientation="vertical" flexItem />
              <Typography 
                variant="body1" 
                sx={{ 
                  minWidth: '120px',
                  fontWeight: 'medium',
                }}
              >
                姓名：{presenter.name}
              </Typography>
            </Box>

            {/* 評分選項 */}
            <RadioGroup
              row
              value={scores[presenter.studentId] || ''}
              onChange={(e) => handleChange(presenter.studentId, e.target.value)}
              sx={{ gap: 1 }}
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
                  <Box display="flex" alignItems="center" gap={0.5}>
                    <Typography variant="body1" fontWeight="bold">A</Typography>
                    {count.A >= maxA && scores[presenter.studentId] !== 'A' && (
                      <Typography variant="caption" color="error">(已滿)</Typography>
                    )}
                  </Box>
                }
                disabled={count.A >= maxA && scores[presenter.studentId] !== 'A'}
                sx={{
                  '& .MuiFormControlLabel-label': {
                    fontWeight: scores[presenter.studentId] === 'A' ? 'bold' : 'normal',
                  },
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
                  <Box display="flex" alignItems="center" gap={0.5}>
                    <Typography variant="body1" fontWeight="bold">B</Typography>
                    {count.B >= maxB && scores[presenter.studentId] !== 'B' && (
                      <Typography variant="caption" color="error">(已滿)</Typography>
                    )}
                  </Box>
                }
                disabled={count.B >= maxB && scores[presenter.studentId] !== 'B'}
                sx={{
                  '& .MuiFormControlLabel-label': {
                    fontWeight: scores[presenter.studentId] === 'B' ? 'bold' : 'normal',
                  },
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
                  <Box display="flex" alignItems="center" gap={0.5}>
                    <Typography variant="body1" fontWeight="bold">C</Typography>
                    {count.C >= maxC && scores[presenter.studentId] !== 'C' && (
                      <Typography variant="caption" color="error">(已滿)</Typography>
                    )}
                  </Box>
                }
                disabled={count.C >= maxC && scores[presenter.studentId] !== 'C'}
                sx={{
                  '& .MuiFormControlLabel-label': {
                    fontWeight: scores[presenter.studentId] === 'C' ? 'bold' : 'normal',
                  },
                }}
              />
            </RadioGroup>
          </Box>
          
          {/* 顯示目前選擇的評分 */}
          {scores[presenter.studentId] && (
            <Box mt={1} display="flex" justifyContent="flex-end">
              <Chip
                label={`已選擇：${scores[presenter.studentId]} 等級`}
                size="small"
                color={
                  scores[presenter.studentId] === 'A' ? 'success' :
                  scores[presenter.studentId] === 'B' ? 'warning' : 'error'
                }
                variant="filled"
              />
            </Box>
          )}
        </Paper>
      ))}

      {/* 進度提示 */}
      <Paper sx={{ p: 2, bgcolor: 'info.light', color: 'info.contrastText' }}>
        <Typography variant="body2" textAlign="center">
          已評分：{Object.keys(scores).length} / {presenters.length} 位同學
        </Typography>
      </Paper>
    </Box>
  );
}