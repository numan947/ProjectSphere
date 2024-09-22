package com.numan947.pmbackend.primary_packages.project;

import com.numan947.pmbackend.primary_packages.chatmessage.dto.ChatResponse;
import com.numan947.pmbackend.primary_packages.project.dto.ProjectResponse;
import com.numan947.pmbackend.primary_packages.project.dto.ProjectRequest;
import com.numan947.pmbackend.primary_packages.project.dto.ProjectShortResponse;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface ProjectService {

    ProjectShortResponse createProject(ProjectRequest projectRequest, Authentication connectedUser);

    List<ProjectShortResponse> getAllProjectsOfUser(Authentication connectedUser, String category, String tag, int page, int size);

    ProjectResponse getProjectById(String projectId, Authentication connectedUser);

    void deleteProject(String projectId, Authentication connectedUser);

    ProjectShortResponse updateProject(ProjectRequest projectRequest, Authentication connectedUser);

    void addMemberToProject(String projectId, String memberId, Authentication connectedUser);

    void removeMemberFromProject(String projectId, String memberId, Authentication connectedUser);

    ChatResponse getChatByProjectId(String projectId, Authentication connectedUser);

    List<ProjectShortResponse> searchProjects(String searchKey, Authentication connectedUser, int page, int size);
}
