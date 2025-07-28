import React from 'react'
import SignInSide from "./components/account/SignInSide.tsx";
import {Route, Routes } from 'react-router-dom';
import SignUpPage from "./components/account/SignUpPage.tsx";
import FormPage from "./components/form/FormPage.tsx";
import ProtectedRoute from './components/common/ProtectedRoute.tsx';

const App: React.FC = () => {
    return (
        <Routes>
            <Route path="/" element={<SignInSide/>}/>
            <Route path="/material-ui/getting-started/templates/sign-in/" element={<SignUpPage/>}/>
            <Route path="/material-ui/getting-started/templates/form/" 
                element={
                <ProtectedRoute>
                    <FormPage />
                </ProtectedRoute>
                }/>
        </Routes>
    );
}
export default App
