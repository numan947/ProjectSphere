package com.numan947.pmbackend.primary_packages.project;

import com.numan947.pmbackend.exception.OperationNotPermittedException;
import com.numan947.pmbackend.primary_packages.project.dto.ProjectResponse;
import com.numan947.pmbackend.primary_packages.project.dto.ProjectRequest;
import com.numan947.pmbackend.primary_packages.project.dto.ProjectShortResponse;
import com.numan947.pmbackend.user.User;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProjectServiceImpl implements ProjectService{
    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;

    @Override
    public ProjectShortResponse createProject(ProjectRequest projectRequest, Authentication connectedUser) {
        User user = (User) connectedUser.getPrincipal();
        // create project
        Project project = projectMapper.toProject(projectRequest);
        project.setOwner(user);
        project.getTeamMembers().add(user);
        projectRepository.save(project);
        return projectMapper.toProjectShortResponse(project);
    }


    // return all projects that the user is a member of -> owner or team member
    @Override
    public List<ProjectShortResponse> getAllProjectsOfUser(Authentication connectedUser, String category, String tag, int page, int size) {
        // TODO: Implement pagination later here
        User user = (User) connectedUser.getPrincipal();
        List<Project> projects = projectRepository.findAllByTeamMembersContainingOrOwnerId(user.getId(), user.getId());

        //filter by category and tag
        if(category != null && !category.isEmpty()){
            projects.removeIf(project -> !project.getCategory().equals(category));
        }
        if(tag != null && !tag.isEmpty()){
            projects.removeIf(project -> !project.getTags().contains(tag));
        }

        return projects.stream().map(projectMapper::toProjectShortResponse).toList();
    }

    @Override
    public ProjectResponse getProjectById(String projectId, Authentication connectedUser) {
        User user = (User) connectedUser.getPrincipal();
        Project project = projectRepository.findById(projectId).
                orElseThrow(() -> new EntityNotFoundException("Project not found"));
        if(!project.getTeamMembers().contains(user) && !project.getOwner().equals(user)){
            throw new OperationNotPermittedException("User is not a member of this project");
        }
        return projectMapper.toProjectResponse(project);
    }

    @Override
    public void deleteProject(String projectId, Authentication connectedUser) {
        User user = (User) connectedUser.getPrincipal();
        Project project = projectRepository.findById(projectId).
                orElseThrow(() -> new EntityNotFoundException("Project not found"));
        if(!project.getOwner().equals(user)){
            throw new OperationNotPermittedException("User is not the owner of this project");
        }
        projectRepository.delete(project);
    }

    @Override
    public ProjectShortResponse updateProject(ProjectRequest projectRequest, Authentication connectedUser) {
        if (projectRequest.id() == null || projectRequest.id().isEmpty()) {
            throw new OperationNotPermittedException("Project id is required");
        }
        User user = (User) connectedUser.getPrincipal();
        Project project = projectRepository.findById(projectRequest.id()).
                orElseThrow(() -> new EntityNotFoundException("Project not found"));
        if(!project.getOwner().equals(user)){
            throw new OperationNotPermittedException("User is not the owner of this project");
        }
        project.setName(projectRequest.name());
        project.setDescription(projectRequest.description());
        project.setCategory(projectRequest.category());
        project.setTags(projectRequest.tags());
        projectRepository.save(project);
        return projectMapper.toProjectShortResponse(project);
    }

    @Override
    public void addMemberToProject(String projectId, String memberId, Authentication connectedUser) {

    }

    @Override
    public void removeMemberFromProject(String projectId, String memberId, Authentication connectedUser) {

    }

    @Override
    public List<ProjectShortResponse> searchProjects(String searchKey, Authentication connectedUser, int page, int size) {
        // TODO: Implement pagination later here
        User user = (User) connectedUser.getPrincipal();
        List<Project> projects = projectRepository.findAllByNameContainingOrDescriptionContaining(searchKey, searchKey, user.getId());
        return projects.stream().map(projectMapper::toProjectShortResponse).toList();
    }

    @Override
    public Optional<Project> findProjectByIdAndOwnerId(String projectId, String ownerId) {
        return projectRepository.findProjectByIdAndOwnerId(projectId, ownerId);
    }

    @Override
    public Optional<Project> findProjectById(String projectId) {
        return projectRepository.findById(projectId);
    }

    @Override
    public void updateProject(Project project) {
        projectRepository.save(project);
    }

    @Override
    public boolean isUserPartOfProject(String userId, String projectId) {
        Project project = projectRepository.findById(projectId).
                orElseThrow(() -> new EntityNotFoundException("Project not found"));
        return project.getTeamMembers().stream().anyMatch(user -> user.getId().equals(userId));
    }
}
