import { useMutation } from "@tanstack/react-query";
import authClient, { RegisterRequest } from "../client/authClient";
import {AxiosError} from "axios";

const useRegister = (registerSuccessHandler:()=>void, registerFailureHandler:(message:string)=>void) => {

    return useMutation<any, AxiosError, RegisterRequest>({
        mutationKey: ["register"],
        mutationFn: authClient.register,
        onSuccess: () => {
            registerSuccessHandler();
        },
        onError: (error:AxiosError) => {
            const errorData = error.response?.data as { businessErrorDescription?: string };
            registerFailureHandler(`Registration Failed! ${errorData.businessErrorDescription}`);
        }
    });
}

export default useRegister;
