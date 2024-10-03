import axiosInstance from "../../../common/BaseClient";

export interface LoginResponse{
    id: string;
    token: string;
    email: string;
    fullName: string;
}

export interface LoginRequest{
    email: string;
    password: string;
}

export interface RegisterRequest{
    email: string;
    password: string;
    fname: string;
    lname: string;
}

class AuthClient{
    prefix = '/auth'
    login = async (loginReq:LoginRequest)=>{
        const res = await axiosInstance
            .post<LoginRequest, LoginResponse>(`${this.prefix}/login`, loginReq);
        return res;
    }

    register = async (registerReq:RegisterRequest)=>{
        const res = await axiosInstance
            .post<RegisterRequest, LoginResponse>(`${this.prefix}/register`, registerReq);
        return res;
    }

    resendActivationCode = async (email:string)=>{
        // console.log(email.email);
        const res = await axiosInstance
            .get(`${this.prefix}/resend-activation?email=${email}`);
        return res;
    }

    activateAccount = async (pin:string)=>{
        const res = await axiosInstance
            .get(`${this.prefix}/activate`, 
            {
                params: {
                    'activation-code': pin
                }
            }
            );
        return res;
    }
}

export default new AuthClient();