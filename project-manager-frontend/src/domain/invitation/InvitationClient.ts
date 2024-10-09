import axiosInstance from "../../common/BaseClient";
import { InvitationRequest, InvitationResponse } from "./Entities";

class InvitationClient{
    prefix = '/invitation'

    createInvitations = async (data: InvitationRequest)=>{
        const res = await axiosInstance.post(`${this.prefix}/create`,{},{
            params:{
                "project-id": data.projectId,
                "emails": data.emails.toString()
            }
        });
        return res;
    }


    getPendingInvitations = async ()=>{
        const res = await axiosInstance.get<InvitationResponse[]>(`${this.prefix}/get-pending-invites`);
        return res;
    }

    deleteOrRejectInvitation = async (invitationId: string)=>{
        const res = await axiosInstance.post(`${this.prefix}/reject`, {}, {
            params:{
                "invitation-id": invitationId
            }
        });
        return res;
    }

    acceptInvitation = async (invitationCode: string)=>{
        const res = await axiosInstance.post(`${this.prefix}/accept`, {}, {
            params:{
                "invitation-id": invitationCode
            }
        });
        return res;
    }

    
}

export default new InvitationClient();