import { useMutation } from "@tanstack/react-query";
import InvitationClient from "../InvitationClient";

const useCreateInvitations = () => {
    return useMutation(
        {
            mutationKey: ["createInvitations"],
            mutationFn: InvitationClient.createInvitations,
            onSuccess: () => {
                console.log("Invitations created successfully");
            },
            onError: (error) => {
                console.log("Error creating invitations", error);
            },
        }
    );
};

export default useCreateInvitations;