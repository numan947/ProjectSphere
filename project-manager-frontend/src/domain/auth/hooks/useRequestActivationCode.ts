import { useMutation, useQuery } from "@tanstack/react-query";
import authClient from "../client/authClient";

export const useRequestActivationCode = () => {
    return useMutation<any, any, string>({
        mutationKey: ["requestActivationCode"],
        mutationFn: authClient.resendActivationCode,
    });
}
