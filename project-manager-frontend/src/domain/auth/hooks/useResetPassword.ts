import { useMutation} from "@tanstack/react-query";
import authClient from "../client/authClient";
import { AxiosError } from "axios";

export const useResetPassword = (onSuccess:()=>void, onError:(msg:string)=>void) => {
    return useMutation<any, any, any>({
        mutationKey: ["resetPassword"],
        mutationFn: ({ password, token }: { password: string; token: string }) => authClient.resetPassword(password, token),
        onError: (error:AxiosError) => {
            const errorData = error.response?.data as { error?: string };
            onError(errorData.error||"An error occurred");
        },
        onSuccess: ()=>{
            onSuccess();
        }
    });
}
