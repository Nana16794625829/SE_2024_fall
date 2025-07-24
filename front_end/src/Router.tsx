import { Navigate, useRoutes } from "react-router-dom";
import { FormPage } from "./pages/FormPage";
import { ThankYouPage } from "./pages/thankYouPage";

export const App = () => {
    return useRoutes([
        { path: "/", element: <Navigate to="/formpage" /> },
        { path: "/formpage", element: <FormPage /> },
        { path: "/formpage/thankyoupage", element: <ThankYouPage /> },
    ]);
};