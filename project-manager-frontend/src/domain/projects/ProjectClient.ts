import axiosInstance from "../../common/BaseClient";
import { ProjectCreateRequest, ProjectShortResponse } from "./Entities";

class ProjectClient{
    prefix = '/projects'

    getAllTags = ()=>{
        return axiosInstance.get<string[]>('/users/usable-tags')
    }

    getAllCategories = ()=>{
        return axiosInstance.get<string[]>('/users/usable-categories')
    }

    createProject = async (data: ProjectCreateRequest)=>{
        const res = await axiosInstance.post<ProjectCreateRequest, ProjectShortResponse>(`${this.prefix}/create`, data);
        return res;
    }


    getAllProjectsShort = async ()=>{
        const res = await axiosInstance.get<ProjectShortResponse[]>(`${this.prefix}`);
        return res;
    }



}

export default new ProjectClient();