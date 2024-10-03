import { useMutation } from "@tanstack/react-query"
import authClient, { LoginRequest, LoginResponse } from "../client/authClient";
import { AxiosError } from "axios";
import useAuthStore from "../store/AuthStore";

const useLogin = (loginSuccessHandler: ()=>void, loginFailureHandler:(message:string)=>void) =>{
    const setUser = useAuthStore((state) => state.setUser);
    return useMutation<LoginResponse, AxiosError, LoginRequest>(
        {
            mutationKey: ["login"],
            mutationFn: authClient.login,
            onError: (error:AxiosError) => {
                console.log(error);
                const errorMessage = (error.response?.data as { businessErrorDescription: string }).businessErrorDescription;
                console.log(errorMessage);
                loginFailureHandler(errorMessage);
            },
            onSuccess: (data) => {
                console.log(data);
                setUser({
                    fullName: data.fullName,
                    email: data.email,
                    id: data.id,
                    token: data.token,
                });
                loginSuccessHandler();
            }
        }
    );
}

export default useLogin;