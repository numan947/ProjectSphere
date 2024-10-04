import { useMutation, useQuery } from "@tanstack/react-query";
import authClient from "../client/authClient";
import { AxiosError } from "axios";

export const useRequestActivationCode = (onSuccess:()=>void, onError:(msg:string)=>void) => {
    return useMutation<any, any, string>({
        mutationKey: ["requestActivationCode"],
        mutationFn: authClient.resendActivationCode,
        onError: (error:AxiosError) => {
            const errorData = error.response?.data as { error?: string };
            onError(errorData.error||"An error occurred");
        },
        onSuccess: ()=>{
            onSuccess();
        }
    });
}
