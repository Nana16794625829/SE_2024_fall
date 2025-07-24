export const presenters = [
    { name: '1' },
    { name: '2' },
    { name: '3' },
    { name: '4' },
    { name: '5' },
    { name: '6' },
    { name: '7' },
    { name: '8' },
];


export const studentInfoInitValue =
{
    studentID: '',
    studentName: '',
    studentEmail: '',
    comments: presenters.reduce((acc, presenter) => {
        acc[presenter.name] = ''; // 初始值設置為空字串
        return acc;
    }, {} as Record<string, "A" | "B" | "C" | "">)
};

