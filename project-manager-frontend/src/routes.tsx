import { createBrowserRouter } from "react-router-dom";
import Layout from "./pages/Layout.tsx";
import Home from "./pages/Home.tsx";

const router = createBrowserRouter([
  {
    path: "/",
    element: <Layout />,
    errorElement: <div>404 Not Found</div>,
    children: [
      {
        index: true,
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
]);

export default router;
