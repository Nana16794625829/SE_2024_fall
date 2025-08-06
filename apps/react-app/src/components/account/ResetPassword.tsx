import React from "react";
import AppTheme from "../../shared-theme/AppTheme.tsx";
import CssBaseline from "@mui/material/CssBaseline";
import CardWrapper from "../CardWrapper.tsx";
import Typography from "@mui/material/Typography";
import FormControl from "@mui/material/FormControl";
import FormLabel from "@mui/material/FormLabel";
import TextField from "@mui/material/TextField";
import Box from "@mui/material/Box";
import Stack from "@mui/material/Stack";
import {backgroundGradientStyle} from "../../styles/backgroundStyles.tsx";
import Button from "@mui/material/Button";
import SendIcon from '@mui/icons-material/Send';


export default function ResetPassword(props: { disableCustomTheme?: boolean }) {
    return (
        <AppTheme {...props}>
            <CssBaseline enableColorScheme />
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

            <Stack
                direction={{ xs: 'column-reverse', md: 'row' }}
                spacing={{ xs: 4, sm: 8}}
                alignItems="center"
                justifyContent="center"
            >
            <CardWrapper>
                <Typography
                    component="h1"
                    variant="h4"
                    sx={{ width: '100%', fontSize: 'clamp(2rem, 10vw, 2.15rem)' }}
                >
                    Reset password
                </Typography>
                <Box
                    component="form"
                    noValidate
                    sx={{ display: 'flex', flexDirection: 'column', width: '100%', gap: 2 }}
                >
                    <FormControl>
                        <Stack spacing={2}>
                        <FormLabel htmlFor="newPassword">New password</FormLabel>
                        <TextField
                            id="newPassword"
                            name="newPassword"
                            placeholder="請輸入新密碼"
                            autoComplete="newPassword"
                            autoFocus
                            required
                            fullWidth
                            variant="outlined"
                        />
                        <FormLabel htmlFor="newPasswordCheck">Enter password again</FormLabel>
                        <TextField
                            id="newPasswordCheck"
                            name="newPasswordCheck"
                            placeholder="再次輸入新密碼"
                            autoComplete="newPasswordCheck"
                            autoFocus
                            required
                            fullWidth
                            variant="outlined"
                        />
                        </Stack>
                    </FormControl>
                    <Button type="submit" fullWidth variant="contained" endIcon={<SendIcon />}>
                        Submit
                    </Button>
                </Box>
            </CardWrapper>
            </Stack>
            </Stack>
        </AppTheme>
        )
}