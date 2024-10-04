import { useMutation } from "@tanstack/react-query";
import authClient from "../client/authClient";
import { AxiosError } from "axios";

export const useActivateAccount = (onSuccessfulActivation:()=>void, onFailedActiation:(msg:string)=>void) => {
    return useMutation<any, any, string>({
        mutationKey: ["activateAccount"],
        mutationFn: authClient.activateAccount,
        onSuccess: ()=>{
            onSuccessfulActivation();
        },
        onError: (error:AxiosError)=>{
            const errorData = error.response?.data as { businessErrorDescription?: string };
            onFailedActiation(errorData.businessErrorDescription||"An error occurred");
        }
    });
}

export default useActivateAccount;