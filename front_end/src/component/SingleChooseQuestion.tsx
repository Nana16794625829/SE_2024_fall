import { Radio, Group } from '@mantine/core';
import { useState } from 'react';

interface SingleChooseQuestionProps {
    presenter: { name: string };
    onAnswerChange: (presenterName: string, value: "A" | "B" | "C") => void;
    disableA: boolean;  // 新增 disableA 來控制是否禁用A選項
    disableB: boolean;  // 新增 disableB 來控制是否禁用B選項
    disableC: boolean;  // 新增 disableC 來控制是否禁用C選項
}

export function SingleChooseQuestion({ presenter, onAnswerChange, disableA, disableB, disableC }: SingleChooseQuestionProps) {
    const [selectedValue, setSelectedValue] = useState<"A" | "B" | "C" | null>(null);
    const options: { value: "A" | "B" | "C"; label: string; disabled: boolean }[] = [
        { value: "A", label: "A", disabled: disableA },
        { value: "B", label: "B", disabled: disableB },
        { value: "C", label: "C", disabled: disableC }
    ];

    const handleChange = (value: "A" | "B" | "C") => {
        setSelectedValue(value);
        onAnswerChange(presenter.name, value);

    };

    return (
        <div>
            <p style={{ textAlign: 'left' }}>針對報告者{presenter.name}的評論</p>
            <Group mt="xs">
                {options.map((option) => (
                    <Radio
                        key={option.value}
                        value={option.value}
                        label={option.label}
                        name={`question-${presenter.name}`} // 確保同一組問題有相同的 name
                        disabled={option.disabled}
                        onChange={() => handleChange(option.value)}
                    />
                ))}

            </Group>
        </div>

    );



}

