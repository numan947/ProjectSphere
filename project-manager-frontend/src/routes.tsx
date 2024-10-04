import { createBrowserRouter } from "react-router-dom";
import Layout from "./pages/Layout.tsx";
import LandingPage from "./LandingPage.tsx";
import Home from "./pages/Home.tsx";
import ActivateAccount from "./domain/auth/pages/ActivateAccount.tsx";
import PrivateRoutes from "./PrivateRoutes.tsx";
import ResetPassword from "./domain/auth/pages/ResetPassword.tsx";

const router = createBrowserRouter([
  {
    path: "/",
    element: <Layout />,
    errorElement: <div>404 Not Found</div>,
    children: [
      {
        index: true,
        element: <LandingPage />,
      },
      {
        path: "activate-account",
        element: <ActivateAccount />,
      },
      {
        path: "reset-password",
        element: <ResetPassword />,
      },
      {
        element: <PrivateRoutes />,
        children: [
          {
            path: "home",
            element: <Home />,
          },
          {
            path: "projects",
            element: <div>Projects</div>,
          },
          {
            path: "issues",
            element: <div>Issues</div>,
          },
          {
            path: "profile",
            element: <div>Profile</div>,
          },
        ],
      },
    ],
  },
]);

export default router;
