export interface ProjectShortResponse {
    id: number;
    name: string;
    description: string;
    category: string;
    memberCount: number;
    issueCount: number;
    tags: string[];
}