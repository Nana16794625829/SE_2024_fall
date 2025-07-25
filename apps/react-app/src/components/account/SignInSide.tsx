// import * as React from 'react';
import CssBaseline from '@mui/material/CssBaseline';
import Stack from '@mui/material/Stack';
import AppTheme from '../../shared-theme/AppTheme.tsx';
import ColorModeSelect from '../../shared-theme/ColorModeSelect.tsx';
import SignInCard from './SignInCard.tsx';
import SignInPageContent from './SignInPageContent.tsx';
import {Typography} from "@mui/material";

export default function SignInSide(props: { disableCustomTheme?: boolean }) {
    return (
        <AppTheme {...props}>
            <CssBaseline enableColorScheme />
            <ColorModeSelect sx={{ position: 'fixed', top: '1rem', right: '1rem' }} />
            <Stack
                direction="column"
                component="main"
                sx={[
                    {
                        justifyContent: 'center',
                        height: 'calc((1 - var(--template-frame-height, 0)) * 100%)',
                        marginTop: 'max(40px - var(--template-frame-height, 0px), 0px)',
                        minHeight: '100%',
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
                <Stack
                    direction={{ xs: 'column', md: 'column' }}
                    sx={{
                        justifyContent: 'center',
                        alignItems: 'center',
                        gap: { xs: 2, sm: 4 },
                        p: 2,
                        mx: 'auto',
                    }}
                >
                    <Typography variant="h1" gutterBottom>
                        ISSLAB - Software Engineering 2025
                    </Typography>

                    <Stack
                        direction={{ xs: 'column-reverse', md: 'row' }}
                        sx={{
                            justifyContent: 'center',
                            gap: { xs: 6, sm: 12 },
                            p: 2,
                            mx: 'auto',
                        }}
                    >
                        <Stack
                            direction={{ xs: 'column-reverse', md: 'row' }}
                            sx={{
                                justifyContent: 'center',
                                gap: { xs: 6, sm: 12 },
                                p: { xs: 2, sm: 4 },
                                m: 'auto',
                            }}
                        >
                            <SignInPageContent />
                            <SignInCard />
                        </Stack>
                    </Stack>
                </Stack>
            </Stack>
        </AppTheme>
    );
}