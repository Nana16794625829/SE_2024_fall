import { useEffect, useState } from 'react';
import api from "../lib/axios.ts";
import {Presenter} from "../types/presenter.ts";

export const usePresenters = () => {
    const [presenters, setPresenters] = useState<Presenter[]>([]);
    const [error, setError] = useState<string | null>(null);
    

    useEffect(() => {
        api.get(`/api/presenter/`)
            .then(res => {
                setPresenters(res.data)
            })
            .catch(err => {
                console.error('取得 presenter 失敗', err);
                setError('無法取得報告者資訊');
                setPresenters([]);
            });
    }, []);

    return { presenters, error };
};