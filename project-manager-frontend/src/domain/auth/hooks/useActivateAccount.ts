import { useMutation } from "@tanstack/react-query";
import authClient from "../client/authClient";

export const useActivateAccount = () => {
    return useMutation<any, any, string>({
        mutationKey: ["activateAccount"],
        mutationFn: authClient.activateAccount,
    });
}

export default useActivateAccount;