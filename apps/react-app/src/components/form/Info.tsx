import * as React from 'react';
import List from '@mui/material/List';
import ListItem from '@mui/material/ListItem';
import ListItemText from '@mui/material/ListItemText';
import Typography from '@mui/material/Typography';

const scores = [
    {
        name: 'A',
        // desc: 'Monthly subscription',
        count: '2',
    },
    {
        name: 'B',
        // desc: 'Included in the Professional plan',
        count: '3',
    },
    {
        name: 'C',
        // desc: 'Devices needed for development',
        count: '4',
    },
    {
        name: '無評分',
        count: '1'
    }
];

interface InfoProps {
    totalcount: string;
}

export default function Info({ totalcount }: InfoProps) {
    return (
        <React.Fragment>
            <Typography variant="subtitle2" sx={{ color: 'text.secondary' }}>
                目前評分總計
            </Typography>
            <Typography variant="h4" gutterBottom>
                {totalcount}
            </Typography>
            <List disablePadding>
                {scores.map((score) => (
                    <ListItem key={score.name} sx={{ py: 1, px: 0 }}>
                        <ListItemText
                            sx={{ fontSize: 50, mr: 2}}
                            primary={score.name}
                            // slotProps={{
                            //     primary: {
                            //         sx: { fontSize: '1.9rem' },
                            //     },
                            // }}
                            // secondary={score.desc}
                        />
                        <Typography variant="body1" sx={{ fontSize: '1.2rem', fontWeight: '500' }}>
                            {score.count}
                        </Typography>
                    </ListItem>
                ))}
            </List>
        </React.Fragment>
    );
}