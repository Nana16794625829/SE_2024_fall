import * as React from 'react';
import Typography from "@mui/material/Typography";
import Box from "@mui/material/Box";
import TextField from "@mui/material/TextField";

export default function Comment() {
    return (
        <Box>
        <Typography variant="h6" component="div" sx={{ color: 'text.secondary' }}>
            對本周的課程或報告有什麼心得與回饋嗎？歡迎下方留言讓我們知道
        </Typography>
            <Box
                sx={{ '& > :not(style)': { mt: 2, width: '100%'} }}
                autoComplete="off"
            >
                <TextField
                    id="outlined-basic"
                    label="comment"
                    variant="outlined"
                    sx={{
                        '& .MuiInputBase-root': {
                            height: '56px', // 自定義高度
                        }
                    }}
                />
            </Box>
        </Box>
    );
}