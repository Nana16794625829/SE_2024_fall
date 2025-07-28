// mock-server/index.js
const express = require('express');
const app = express();
const PORT = 3000;

app.use(express.json());

app.post('/se/2025/api/auth/login', (req, res) => {
    const { username, password } = req.body;
    console.log('✅ Received login:', { username, password });

    res.json({ token: 'mock-token-abc' });
});


app.get('/se/2025/api/auth/me', (req, res) => {
    console.log("hello");

    res.json({ username: '111111' });
    // res.status(401);
});

app.listen(PORT, () => {
    console.log(`🚀 Mock server is running at http://localhost:${PORT}`);
});