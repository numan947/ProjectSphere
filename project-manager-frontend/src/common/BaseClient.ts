import axios from 'axios';
import useAuthStore from '../domain/auth/AuthStore';

const axiosInstance = axios.create({
    baseURL: 'http://localhost:9999/api/v1'
});

axiosInstance.interceptors.request.use((config)=>{
    const token = useAuthStore.getState().token;
    if(token){
        config.headers['Authorization'] = `Bearer ${token}`;
    }
    return config;
}, (error)=>{
    return Promise.reject(error);
})

export default axiosInstance;