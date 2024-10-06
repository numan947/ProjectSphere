import axiosInstance from "../../common/BaseClient";
import { InvitationRequest } from "./Entities";

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
}

export default new InvitationClient();