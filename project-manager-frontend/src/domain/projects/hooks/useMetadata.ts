// react query for fetching all tags

import { useQuery } from "@tanstack/react-query";
import ProjectClient from "../ProjectClient";

export const useTags = () => {
  return useQuery<string[]>({
    queryKey: ["tags"],
    queryFn: async () => {
      const response = await ProjectClient.getAllTags();
      return response.data;
    }
  });
};

export const useCategories = () => {
  return useQuery<string[]>({
    queryKey: ["categories"],
    queryFn: async () => {
      const response = await ProjectClient.getAllCategories();
      return response.data;
    },
  });
};