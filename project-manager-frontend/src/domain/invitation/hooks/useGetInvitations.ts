import { useQuery } from "@tanstack/react-query";
import { InvitationResponse } from "../Entities";
import InvitationClient from "../InvitationClient";


export const useGetPendingInvitations = () => {
    return useQuery<InvitationResponse[]>({
      queryKey: ["all-pending-invitations"],
      queryFn: async () => {
        const response = await InvitationClient.getPendingInvitations();
        console.log(response.data);
        return response.data;
      },
      refetchOnWindowFocus: false,
      refetchOnReconnect: true,
      staleTime: 1000 * 60 * 20
    });
  };
  