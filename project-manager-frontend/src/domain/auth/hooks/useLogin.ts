import { useMutation } from "@tanstack/react-query"
import authClient, { LoginRequest, LoginResponse } from "../client/authClient";
import { AxiosError } from "axios";
import useAuthStore from "../store/AuthStore";

const useLogin = (loginSuccessHandler: ()=>void, loginFailureHandler:(message:string)=>void) =>{
    const setUser = useAuthStore((state) => state.setUser);
    return useMutation<{data:LoginResponse}, AxiosError, LoginRequest>(
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
                // console.log("LOGIN SUCCESS")
                var tmp = data.data;
                setUser({
                    fullName: tmp.fullName,
                    email: tmp.email,
                    id: tmp.id,
                    token: tmp.token,
                });
                loginSuccessHandler();
            }
        }
    );
}

export default useLogin;