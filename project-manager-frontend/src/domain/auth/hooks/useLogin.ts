import { useMutation } from "@tanstack/react-query"
import authClient  from "../AuthClient";
import { LoginRequest, LoginResponse } from "../Entities";
import { AxiosError } from "axios";
import useAuthStore from "../AuthStore";

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