import { useMutation } from "@tanstack/react-query";
import InvitationClient from "../InvitationClient";

const useAcceptInvitation = () => {
    return useMutation(
        {
            mutationKey: ["acceptInvitation"],
            mutationFn: InvitationClient.acceptInvitation,
            onSuccess: () => {
                console.log("Invitations Accepted");
            },
            onError: (error) => {
                console.log("Error accepting invitation", error);
            },
        }
    );
};

export default useAcceptInvitation;