// import * as React from 'react';
// import Box from '@mui/material/Box';
import Stack from '@mui/material/Stack';
import Typography from '@mui/material/Typography';
import LoginIcon from '@mui/icons-material/Login';
import PersonAddAlt1 from '@mui/icons-material/PersonAddAlt1';
import EmailIcon from '@mui/icons-material/Email';
// import RuleIcon from '@mui/icons-material/Rule';
// import GroupIcon from '@mui/icons-material/Group';
// import SentimentVerySatisfiedIcon from '@mui/icons-material/SentimentVerySatisfied';
// import SentimentNeutralIcon from '@mui/icons-material/SentimentNeutral';
// import SentimentDissatisfiedIcon from '@mui/icons-material/SentimentDissatisfied';
import ManageSearchIcon from '@mui/icons-material/ManageSearch';
// import WorkOffIcon from '@mui/icons-material/WorkOff';

const items = [
    {
        icon: <LoginIcon sx={{ color: 'text.secondary' }} />,
        title: '請先登入',
        description: '投票前請先登入系統，以確保身分驗證與紀錄儲存。',
    },
    {
        icon: <PersonAddAlt1 sx={{ color: 'text.secondary' }} />,
        title: '第一次使用嗎？',
        description: '請先註冊帳號並輸入可接收通知的信箱，該信箱也將作為後續登入用帳號。',
    },
    {
        icon: <EmailIcon sx={{ color: 'text.secondary' }} />,
        title: '使用常用信箱',
        description: '請使用你平常會查看的電子郵件，以利後續接收評分結果與登入驗證。',
    },
    // {
    //     icon: <RuleIcon sx={{ color: 'text.secondary' }} />,
    //     title: '反饋規則',
    //     description: '請對每位報告者填寫 A/B/C 回饋，且整體評分必須符合指定人數範圍。',
    // },
    // {
    //     icon: <GroupIcon sx={{ color: 'text.secondary' }} />,
    //     title: '9 位報告者',
    //     description: '本週共有 9 位同學報告，請對每一位完成一次回饋評分。',
    // },
    // {
    //     icon: <SentimentVerySatisfiedIcon sx={{ color: 'text.secondary' }} />,
    //     title: 'A 級回饋',
    //     description: '請選出 1 至 2 位同學給予 A 級評價（表現最佳者）。',
    // },
    // {
    //     icon: <SentimentNeutralIcon sx={{ color: 'text.secondary' }} />,
    //     title: 'B 級回饋',
    //     description: '請給予 5 至 7 位同學 B 級評價（普通但合格）。',
    // },
    // {
    //     icon: <SentimentDissatisfiedIcon sx={{ color: 'text.secondary' }} />,
    //     title: 'C 級回饋',
    //     description: '最多選出 1 至 2 位同學為 C 級（表現需改進）。',
    // },
    {
        icon: <ManageSearchIcon sx={{ color: 'text.secondary' }} />,
        title: '評分審核機制',
        description: '系統會自動檢驗每位同學的評分分布，請務必遵守規則以避免退件。',
    },
    // {
    //     icon: <WorkOffIcon sx={{ color: 'text.secondary' }} />,
    //     title: '在職班免評分',
    //     description: '在職專班同學無需參與本次評分，系統將自動略過處理。',
    // },
];

export default function SignInPageContent() {
    return (
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
    );
}