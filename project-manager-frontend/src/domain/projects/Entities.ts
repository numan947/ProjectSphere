export interface ProjectShortResponse {
    id: number;
    name: string;
    categories: string[];
    memberCount: number;
    issueCount: number;
    tags: string[];
}

export interface ProjectCreateRequest{
    name: string;
    description: string;
    categories: string[];
    tags: string[];
}