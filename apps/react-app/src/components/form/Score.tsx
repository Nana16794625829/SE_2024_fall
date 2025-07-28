import React, { useState, useEffect } from 'react';
import {
  Box,
  Typography,
  RadioGroup,
  FormControlLabel,
  Radio,
  Paper,
  Divider,
  Button,
  Alert,
} from '@mui/material';

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

const maxA = 2;
const maxB = 6;
const maxC = 2;

export default function Score() {
  const [scores, setScores] = useState<Record<string, string>>({});
  const [error, setError] = useState('');
  const [count, setCount] = useState({ A: 0, B: 0, C: 0 });

  useEffect(() => {
    const counter = { A: 0, B: 0, C: 0 };
    Object.values(scores).forEach((v) => {
      if (v === 'A') counter.A += 1;
      if (v === 'B') counter.B += 1;
      if (v === 'C') counter.C += 1;
    });
    setCount(counter);
  }, [scores]);

  const handleChange = (id: string, score: string) => {
    setScores((prev) => ({ ...prev, [id]: score }));
  };

  const handleSubmit = () => {
    const allFilled = presenters.every((p) => scores[p.studentId]);
    if (!allFilled) {
      setError('⚠️ 請為每位 presenter 給分。');
      return;
    }
    if (count.A < 1) {
      setError('⚠️ 請至少選擇一位 A。');
      return;
    }
    if (count.C < 1) {
      setError('⚠️ 請至少選擇一位 C。');
      return;
    }
    setError('');
    console.log('✅ Submitted scores:', scores);
    alert('✅ 成功送出');
  };

  return (
    <Box display="flex" flexDirection="column" gap={3}>
      {error && <Alert severity="warning">{error}</Alert>}
      {presenters.map((p) => (
        <Paper key={p.studentId} sx={{ p: 2 }}>
          <Box
            display="flex"
            alignItems="center"
            justifyContent="space-between"
            flexWrap="wrap"
            gap={1}
          >
            <Box display="flex" alignItems="center" gap={2} flexWrap="wrap">
              <Typography sx={{ minWidth: '160px' }}>
                <strong>學號：</strong>{p.studentId}
              </Typography>
              <Divider orientation="vertical" flexItem />
              <Typography sx={{ minWidth: '160px' }}>
                <strong>姓名：</strong>{p.name}
              </Typography>
            </Box>
            <RadioGroup
              row
              value={scores[p.studentId] || ''}
              onChange={(e) => handleChange(p.studentId, e.target.value)}
            >
              <FormControlLabel
                value="A"
                control={<Radio />}
                label="A"
                disabled={count.A >= maxA && scores[p.studentId] !== 'A'}
              />
              <FormControlLabel
                value="B"
                control={<Radio />}
                label="B"
                disabled={count.B >= maxB && scores[p.studentId] !== 'B'}
              />
              <FormControlLabel
                value="C"
                control={<Radio />}
                label="C"
                disabled={count.C >= maxC && scores[p.studentId] !== 'C'}
              />
            </RadioGroup>
          </Box>
        </Paper>
      ))}
      <Box display="flex" justifyContent="center" mt={2}>
        <Button variant="contained" onClick={handleSubmit}>
          提交
        </Button>
      </Box>
    </Box>
  );
}
