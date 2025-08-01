import { useEffect, useState } from 'react';
import api from "../lib/axios.ts";
import {Presenter} from "../types/presenter.ts";

export const usePresenters = (week: string) => {
    const [presenters, setPresenters] = useState<Presenter[]>([]);
    const [error, setError] = useState<string | null>(null);
    
    console.log('🔄 usePresenters hook 被調用, week:', week); // 這會顯示 hook 調用次數

    useEffect(() => {
        console.log('🔍 實際發送 API 請求 for week:', week); // 這會顯示實際 API 調用
        
        api.get(`/api/presenter/${week}`)
            .then(res => {
                console.log('✅ API 回傳資料:', res.data);
                setPresenters(res.data)
            })
            .catch(err => {
                console.error('取得 presenter 失敗', err);
                setError('無法取得報告者資訊');
                setPresenters([]);
            });
    }, [week]);

    return { presenters, error };
};