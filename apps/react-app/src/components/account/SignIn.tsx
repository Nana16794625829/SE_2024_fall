// import * as React from 'react';
import CssBaseline from '@mui/material/CssBaseline';
import Stack from '@mui/material/Stack';
import AppTheme from '../../shared-theme/AppTheme.tsx';
import ColorModeSelect from '../../shared-theme/ColorModeSelect.tsx';
import SignInCard from './SignInCard.tsx';
import SignInPageContent from './SignInPageContent.tsx';
import {Typography} from "@mui/material";

export default function SignIn(props: { disableCustomTheme?: boolean }) {
    return (
        <AppTheme {...props}>
            <CssBaseline enableColorScheme />
            <ColorModeSelect sx={{ position: 'fixed', top: '1rem', right: '1rem' }} />
            <Stack
                component="main"
                sx={[
                    {
                        minHeight: '100vh',
                        justifyContent: 'center',
                        alignItems: 'center',
                        p: 2,
                    },
                    (theme) => ({
                        '&::before': {
                            content: '""',
                            display: 'block',
                            position: 'absolute',
                            zIndex: -1,
                            inset: 0,
                            backgroundImage:
                                'radial-gradient(ellipse at 50% 50%, hsl(210, 100%, 97%), hsl(0, 0%, 100%))',
                            backgroundRepeat: 'no-repeat',
                            ...theme.applyStyles('dark', {
                                backgroundImage:
                                    'radial-gradient(at 50% 50%, hsla(210, 100%, 16%, 0.5), hsl(220, 30%, 5%))',
                            }),
                        },
                    }),
                ]}
            >
                <Stack spacing={10} alignItems="center">
                    <Typography variant="h1" textAlign="center">
                        ISSLAB - Software Engineering 2025
                    </Typography>

                    <Stack
                        direction={{ xs: 'column-reverse', md: 'row' }}
                        spacing={{ xs: 4, sm: 8 }}
                        alignItems="center"
                        justifyContent="center"
                    >
                        <SignInPageContent />
                        <SignInCard />
                    </Stack>
                </Stack>
            </Stack>
        </AppTheme>
    );

}