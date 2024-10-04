
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