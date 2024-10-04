import { useMutation} from "@tanstack/react-query";
import authClient from "../AuthClient";
import { AxiosError } from "axios";

export const useResetPassword = (onSuccess:()=>void, onError:(msg:string)=>void) => {
    return useMutation<any, any, any>({
        mutationKey: ["resetPassword"],
        mutationFn: ({ password, token }: { password: string; token: string }) => authClient.resetPassword(password, token),
        onError: (error:AxiosError) => {
            console.log(error);
            const errorData = error.response?.data as { businessErrorDescription?: string };
            onError(errorData.businessErrorDescription||"An error occurred");
        },
        onSuccess: ()=>{
            onSuccess();
        }
    });
}
