import React, {useEffect, useState} from "react";
import AppTheme from "../../shared-theme/AppTheme.tsx";
import CssBaseline from "@mui/material/CssBaseline";
import CardWrapper from "../CardWrapper.tsx";
import Typography from "@mui/material/Typography";
import FormControl from "@mui/material/FormControl";
import FormLabel from "@mui/material/FormLabel";
import Box from "@mui/material/Box";
import Stack from "@mui/material/Stack";
import {backgroundGradientStyle} from "../../styles/backgroundStyles.tsx";
import Button from "@mui/material/Button";
import SendIcon from '@mui/icons-material/Send';
import api from "../../lib/axios.ts";
import OutlinedInput from '@mui/material/OutlinedInput';
import InputAdornment from "@mui/material/InputAdornment";
import IconButton from "@mui/material/IconButton";
import Visibility from '@mui/icons-material/Visibility';
import VisibilityOff from "@mui/icons-material/VisibilityOff";
import {FormHelperText} from "@mui/material";
import axios from "axios";


export default function ResetPassword(props: { disableCustomTheme?: boolean }) {
    const [matchError, setMatchError] = useState<string | null>(null);
    const [showPassword, setShowPassword] = React.useState(false);

    const handleClickShowPassword = () => setShowPassword((show) => !show);
    const handleMouseDownPassword = (event: React.MouseEvent<HTMLButtonElement>) => {
        event.preventDefault();
    };
    const handleMouseUpPassword = (event: React.MouseEvent<HTMLButtonElement>) => {
        event.preventDefault();
    };

    const handleSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault();

        const token = new URLSearchParams(window.location.search).get('token');
        const newPassword = (document.getElementById('newPassword') as HTMLInputElement).value;
        const newPasswordCheck = (document.getElementById('newPasswordCheck') as HTMLInputElement).value;

        if (newPassword !== newPasswordCheck) {
            setMatchError('兩次輸入密碼不相同');
            return;
        }

        if (!newPassword || !token) {
            alert('密碼或驗證連結無效');
            return;
        }
        
        try {
            const res = await axios.post(
                '/api/password/reset',
                { newPassword },
                {
                    headers: {
                        Authorization: `Bearer ${token}`,
                    },
                }
            );
            console.log('Response from server:', res.data);
            alert('密碼修改成功');
        } catch (err) {
            console.error('密碼修改失敗', err);
            alert('密碼修改失敗，請稍後再試');
        }
    };

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
                                onSubmit={handleSubmit}
                                noValidate
                                sx={{ display: 'flex', flexDirection: 'column', width: '100%', gap: 2 }}
                            >
                                <Stack spacing={2}>
                                    {/* 新密碼欄位 */}
                                    <FormControl required fullWidth variant="outlined">
                                        <FormLabel htmlFor="newPassword">New password</FormLabel>
                                        <OutlinedInput
                                            id="newPassword"
                                            type={showPassword ? 'text' : 'password'}
                                            endAdornment={
                                                <InputAdornment position="end">
                                                    <IconButton
                                                        aria-label={showPassword ? 'hide the password' : 'display the password'}
                                                        onClick={handleClickShowPassword}
                                                        onMouseDown={handleMouseDownPassword}
                                                        onMouseUp={handleMouseUpPassword}
                                                        edge="end"
                                                    >
                                                        {showPassword ? <VisibilityOff /> : <Visibility />}
                                                    </IconButton>
                                                </InputAdornment>
                                            }
                                            label="New password"
                                        />
                                    </FormControl>

                                    {/* 確認密碼欄位 */}
                                    <FormControl required fullWidth variant="outlined" error={!!matchError}>
                                        <FormLabel htmlFor="newPasswordCheck">Enter password again</FormLabel>
                                        <OutlinedInput
                                            id="newPasswordCheck"
                                            type={showPassword ? 'text' : 'password'}
                                            endAdornment={
                                                <InputAdornment position="end">
                                                    <IconButton
                                                        aria-label={showPassword ? 'hide the password' : 'display the password'}
                                                        onClick={handleClickShowPassword}
                                                        onMouseDown={handleMouseDownPassword}
                                                        onMouseUp={handleMouseUpPassword}
                                                        edge="end"
                                                    >
                                                        {showPassword ? <VisibilityOff /> : <Visibility />}
                                                    </IconButton>
                                                </InputAdornment>
                                            }
                                            label="Enter password again"
                                        />
                                        {!!matchError && <FormHelperText>{matchError}</FormHelperText>}
                                    </FormControl>
                                </Stack>

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
