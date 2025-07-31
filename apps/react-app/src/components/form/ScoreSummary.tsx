import React from 'react';
import { List, ListItem, ListItemText, Typography, Chip, Box } from '@mui/material';

interface ScoreSummaryProps {
    count: { A: number; B: number; C: number };
    maxA: number;
    maxB: number;
    maxC: number;
    getChipColor: (grade: 'A' | 'B' | 'C', current: number, max: number) => 'error' | 'warning' | 'success';
}

export default function ScoreSummary({ count, maxA, maxB, maxC, getChipColor }: ScoreSummaryProps) {
    const scores = [
        { name: 'A', count: count.A, max: maxA },
        { name: 'B', count: count.B, max: maxB },
        { name: 'C', count: count.C, max: maxC },
    ];

    return (
        <List disablePadding>
            {scores.map((score) => (
                <ListItem key={score.name} sx={{ py: 1, px: 0}}>
                    <ListItemText
                        sx={{ mr: 2}}
                        primary={score.name}
                    />
                    <Box display="flex" alignItems="center" gap={1}>
                        <Chip
                            label={`${score.count}/${score.max}`}
                            color={getChipColor(score.name as 'A' | 'B' | 'C', score.count, score.max)}
                            variant="outlined"
                            size="large"
                            sx={{ minWidth: 'fit-content' }}
                        />
                    </Box>
                </ListItem>
            ))}
        </List>
    );
}