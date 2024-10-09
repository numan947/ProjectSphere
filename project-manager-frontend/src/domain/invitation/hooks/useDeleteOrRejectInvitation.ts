import { useMutation } from "@tanstack/react-query";
import InvitationClient from "../InvitationClient";

const useDeleteOrRejectInvitation = () => {
    return useMutation(
        {
            mutationKey: ["deleteOrRejectInvitation"],
            mutationFn: InvitationClient.deleteOrRejectInvitation,
            onSuccess: () => {
                console.log("Invitation deleted or rejected successfully");
            },
            onError: (error) => {
                console.log("error deleting invitation", error);
            },
        }
    );
};

export default useDeleteOrRejectInvitation;