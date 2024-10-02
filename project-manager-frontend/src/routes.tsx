import {createBrowserRouter} from "react-router-dom";
import Layout from "./pages/Layout.tsx";

const router = createBrowserRouter([{
    path: "/",
    element: <Layout/>,
    errorElement: <div>404 Not Found</div>,
    children: [
        {
            index: true,
            element: <div>Home</div>
        },
        {
            path: "projects",
            element: <div>Projects</div>
        },
        {
            path: "issues",
            element: <div>Issues</div>
        },
        {
            path: "profile",
            element: <div>Profile</div>
        }
    ]
}]);

export default router;