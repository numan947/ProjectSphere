import axiosInstance from "../../common/BaseClient";
import { LoginRequest, LoginResponse, RegisterRequest } from "./Entities";

class AuthClient{
    prefix = '/auth'
    login = async (loginReq:LoginRequest)=>{
        const res = await axiosInstance
            .post<LoginRequest, {data:LoginResponse}>(`${this.prefix}/login`, loginReq);
        return res;
    }

    register = async (registerReq:RegisterRequest)=>{
        const res = await axiosInstance
            .post<RegisterRequest, any>(`${this.prefix}/register`, registerReq);
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

    forgotPassword = async (email:string)=>{
        const res = await axiosInstance
            .get(`${this.prefix}/forgot-password?email=${email}`);
        return res;
    }

    resetPassword = async (password:string, token:string)=>{
        const res = await axiosInstance
            .post(`${this.prefix}/reset-password`, {
                password: password,
                resetcode: token
            });
        return res;
    }


}

export default new AuthClient();