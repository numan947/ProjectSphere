// projects related hooks

import { useMutation, useQuery } from "@tanstack/react-query"
import { ProjectCreateRequest, ProjectShortResponse } from "../Entities";
import { AxiosError } from "axios";
import ProjectClient from "../ProjectClient";

export const useCreateProject = (successHandler:()=>void, errorHandler:(err:string)=>void) => {
    return useMutation<ProjectShortResponse, AxiosError, ProjectCreateRequest>({
        mutationKey: ["create-project"],
        mutationFn: ProjectClient.createProject,
        onSuccess: () => {
            successHandler();
        },
        onError: (error:AxiosError) => {
            const errorData = error.response?.data as { businessErrorDescription: string };
            errorHandler(errorData.businessErrorDescription);
        }
    });
}


export const useFetchProjectShortResponses = () => {
    return useQuery<ProjectShortResponse[]>({
        queryKey: ["all-projects-short"],
        queryFn: async () => {
            const response = await ProjectClient.getAllProjectsShort();
            return response.data;
        },
    });
}
