import useAuthStore from "./domain/auth/store/AuthStore";
import { Navigate, Outlet } from "react-router-dom";

const PrivateRoutes = () => {
  const token = useAuthStore.getState().token;
  // console.log(token);
  if (!token) {
    return <Navigate to="/" />;
  }
  return <Outlet />;
};

export default PrivateRoutes;
