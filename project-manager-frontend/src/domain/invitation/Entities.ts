export interface InvitationRequest{
    projectId: string;
    emails: string[];
}

export interface InvitationResponse{
    invitationId: string;
    invitationCode: string;
    projectId: string;
    userEmail: string;
    projectName: string;
    expiryDate: string;
}