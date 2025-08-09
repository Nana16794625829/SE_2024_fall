import { AppBar, Toolbar, Typography } from '@mui/material';
import { useTheme } from '@mui/material/styles';

export default function Header() {
    const theme = useTheme();

    return (
        <AppBar
            position="fixed"
            elevation={0}
            sx={{
                color: theme.palette.mode === 'dark' ? 'white' : 'hsl(220, 30%, 6%)',
                backgroundColor: theme.palette.mode === 'dark'
                    ? 'black'
                    : 'hsla(0, 0%, 100%, 0.9)',
                '&.MuiAppBar-root': {
                    [theme.breakpoints.up('sm')]: {
                        backgroundColor: 'transparent',
                    },
                },
            }}
        >
            <Toolbar>
                <Typography variant="h6" sx={{ flexGrow: 1 }}>
                    ISSLAB
                </Typography>
                {/* 右側按鈕/連結 */}
            </Toolbar>
        </AppBar>
    );
}