// import * as React from 'react';
import CssBaseline from '@mui/material/CssBaseline';
import Stack from '@mui/material/Stack';
import AppTheme from '../../shared-theme/AppTheme.tsx';
import ColorModeSelect from '../../shared-theme/ColorModeSelect.tsx';
import SignInCard from './SignInCard.tsx';
import SignInPageContent from './SignInPageContent.tsx';
import {Typography} from "@mui/material";
import {backgroundGradientStyle} from "../../styles/backgroundStyles.tsx";

export default function SignIn(props: { disableCustomTheme?: boolean }) {
    return (
        <AppTheme {...props}>
            <CssBaseline enableColorScheme />
            <ColorModeSelect sx={{ position: 'fixed', top: '1rem', right: '1rem' }} />
            <Stack
                component="main"
                sx={(theme) => ({
                    minHeight: '100vh',
                    justifyContent: 'center',
                    alignItems: 'center',
                    p: 2,
                    ...backgroundGradientStyle(theme),
                })}
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