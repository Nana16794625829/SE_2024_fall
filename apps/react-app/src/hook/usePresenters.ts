import { useEffect, useState } from 'react';
import api from "../lib/axios.ts";
import {Presenter} from "../types/presenter.ts";

export const usePresenters = (week: string, token: string | null) => {
    const [presenters, setPresenters] = useState<Presenter[]>([]);
    const [error, setError] = useState<string | null>(null);
    console.log('🔍 Fetching presenters for week:', week, 'token?', !!token);

    useEffect(() => {
        if (!token) {
            setError('請重新登入');
            setPresenters([]);
            return;
        }

        api.get(`/api/presenter/${week}`, {
            headers: {
                Authorization: `Bearer ${token}`,
            },
        })
            .then(res => {
                console.log('✅ API 回傳資料:', res.data);
                setPresenters(res.data)
            })
            .catch(err => {
                console.error('取得 presenter 失敗', err);
                setError('無法取得報告者資訊');
                setPresenters([]);
            });
    }, [week, token]);

    return { presenters, error };
};
