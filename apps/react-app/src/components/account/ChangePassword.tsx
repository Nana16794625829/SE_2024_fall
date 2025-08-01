import Stack from '@mui/material/Stack';
import Typography from '@mui/material/Typography';
import LoginIcon from '@mui/icons-material/Login';
import ColorModeSelect from "../../shared-theme/ColorModeSelect.tsx";
import CssBaseline from "@mui/material/CssBaseline";
import AppTheme from "../../shared-theme/AppTheme.tsx";

const items = [
    {
        icon: <LoginIcon sx={{ color: 'text.secondary' }} />,
        title: '暫時的註冊頁面',
        description: '暫時的註冊頁面',
    },
];

export default function ChangePassword(props: { disableCustomTheme?: boolean }) {
    return (
        <AppTheme {...props}>
            <CssBaseline enableColorScheme />
            <ColorModeSelect sx={{ position: 'fixed', top: '1rem', right: '1rem' }} />
            <Stack
                    sx={{ flexDirection: 'column', alignSelf: 'center', gap: 4, maxWidth: 450 }}
                >
                    {items.map((item, index) => (
                        <Stack key={index} direction="row" sx={{ gap: 2 }}>
                            {item.icon}
                            <div>
                                <Typography gutterBottom sx={{ fontWeight: 'medium' }}>
                                    {item.title}
                                </Typography>
                                <Typography variant="body2" sx={{ color: 'text.secondary' }}>
                                    {item.description}
                                </Typography>
                            </div>
                        </Stack>
                    ))}
                </Stack>
            </AppTheme>

    );
}