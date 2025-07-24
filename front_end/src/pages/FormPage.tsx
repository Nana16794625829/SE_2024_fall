import { Button, Group, TextInput } from '@mantine/core';
import { SingleChooseQuestion } from "../component/SingleChooseQuestion";
import { useForm } from '@mantine/form';
import { studentInfoInitValue, presenters } from "../component/formSetting";
import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';



export function FormPage() {
    //定義表單
    const form = useForm({
        mode: 'uncontrolled',
        initialValues: studentInfoInitValue,

    });

    const navigate = useNavigate();

    //追蹤每個選項的選擇次數
    const [maxA, maxB, maxC] = [2, 6, 2];
    const [ansCount, setAnsCount] = useState({ A: 0, B: 0, C: 0, empty: 0 });
    const [tmpAns, settmpAns] = useState(
        presenters.reduce((acc, presenter) => {
            acc[presenter.name] = ""; // 每個 name 的 ans 初始化為空字串
            return acc;
        }, {} as Record<string, "A" | "B" | "C" | "">)
    );
    const handleAnswerChange = (presenterName: string, value: "A" | "B" | "C") => {
        settmpAns(prevState => ({
            ...prevState,
            [presenterName]: value,  // 更新指定的 presenterName 的值
        }));

    };
    //按一次單選題選項它會呼叫兩次 handleAnswerChange，+/- 1 變 +/-2，不知道為啥
    //所以不直接對 ansCount 做加減，而是先算完再直接設定
    useEffect(() => {
        const inHandleRecord = { A: 0, B: 0, C: 0, empty: 0 };
        Object.values(tmpAns).forEach(answer => {
            if (answer === "A") {
                inHandleRecord.A += 1;
            } else if (answer === "B") {
                inHandleRecord.B += 1;
            } else if (answer === "C") {
                inHandleRecord.C += 1;
            } else {
                inHandleRecord.empty += 1;
            }
        });

        setAnsCount(prevState => ({
            ...prevState,
            A: inHandleRecord.A,
            B: inHandleRecord.B,
            C: inHandleRecord.C,
            empty: inHandleRecord.empty
        }));
    }, [tmpAns]);



    //提交成功執行的動作，之後要串到後端
    const handleSubmit = (values: typeof studentInfoInitValue) => {
        //檢查是否每題都有寫
        const hasEmptyFields = Object.keys(values).some((key) => {
            const filledAllSCQ = (ansCount.empty !== 0);
            const value = values[key as keyof typeof values];
            return value === '' || value === null || value === undefined || filledAllSCQ;
        });

        if (hasEmptyFields) {
            alert('請填寫所有的欄位');
            return; // 阻止提交
        }

        //A 或 C 沒有選
        if (ansCount.A < 1) {
            alert('請至少選擇一個 A');
            return;
        }
        if (ansCount.C < 1) {
            alert('請至少選擇一個 C');
            return;
        }

        //單選題答案塞進form.value
        const updatedValues = {
            ...values,
            comments: {
                ...values.comments,
                ...tmpAns,  // 將 tmpAns 的值放入 comments 對應欄位
            },
        };
        console.log(updatedValues);
        //給DB的回傳值是 updatedValues
        //感謝頁面
        navigate("thankyoupage");

    };


    return (
        <form onSubmit={form.onSubmit(handleSubmit)} style={{ fontSize: '22px' }}>
            <TextInput label="學號" {...form.getInputProps('studentID')} />
            <Button type="button">自動填入</Button>
            <TextInput label="姓名" {...form.getInputProps('studentName')} />
            <TextInput label="Email" {...form.getInputProps('studentEmail')} />
            <div style={{ margin: '60px 0' }}>
                {presenters.map((presenter) => (
                    <SingleChooseQuestion
                        key={presenter.name}
                        presenter={presenter}
                        onAnswerChange={handleAnswerChange}
                        disableA={ansCount.A >= maxA}
                        disableB={ansCount.B >= maxB}
                        disableC={ansCount.C >= maxC} />
                ))}
            </div>

            <Group mt="md" style={{ display: "flex", justifyContent: "center" }}>
                <Button type="submit">提交</Button>
            </Group>
        </form>


    )

}

//待辦事項
//有空欄位不給提交
//選項會重複計數(第一個選擇的選項+1，其餘的期望+-1，實際+-2)

