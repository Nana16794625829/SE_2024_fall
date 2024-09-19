import express from 'express';
import path from 'path';

const app = express();

app.use(express.static(path.join(__dirname, '../public')));

app.get('/', (req, res) => {
    res.send('OK lalalalala');
});

app.get('/:studentId', (req, res) => {
    let studentId = req.params.studentId;
    res.sendFile(path.join(__dirname, '../public', 'index.html'));

});

const Port = process.env.PORT || 3000;
app.listen(Port, () => {
    console.log(`Listening on port ${Port}...`);
});
