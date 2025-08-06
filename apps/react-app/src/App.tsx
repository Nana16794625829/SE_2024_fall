import React from 'react'
import SignIn from "./components/account/SignIn.tsx";
import {Route, Routes } from 'react-router-dom';
import ChangePassword from "./components/account/ChangePassword.tsx";
import FormPage from "./components/form/FormPage.tsx";
import ProtectedRoute from './components/common/ProtectedRoute.tsx';
import {ROUTES} from "./constants/routes.ts";
import ResetPassword from "./components/account/ResetPassword.tsx";

const App: React.FC = () => {
    return (
        <Routes>
            <Route path={ROUTES.SIGN_IN} element={<SignIn/>}/>
            <Route path={ROUTES.CHANGE_PASSWORD} element={<ChangePassword/>}/>
            <Route path={ROUTES.RESET_PASSWORD} element={<ResetPassword/>}/>
            <Route path={ROUTES.FORM}
                element={
                <ProtectedRoute>
                    <FormPage />
                </ProtectedRoute>
                }/>
        </Routes>
    );
}
export default App
