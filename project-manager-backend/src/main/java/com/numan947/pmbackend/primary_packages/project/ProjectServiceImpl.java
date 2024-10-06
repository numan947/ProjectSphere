package com.numan947.pmbackend.primary_packages.project;

import com.numan947.pmbackend.exception.OperationNotPermittedException;
import com.numan947.pmbackend.primary_packages.issue.Issue;
import com.numan947.pmbackend.primary_packages.project.dto.ProjectResponse;
import com.numan947.pmbackend.primary_packages.project.dto.ProjectRequest;
import com.numan947.pmbackend.primary_packages.project.dto.ProjectShortResponse;
import com.numan947.pmbackend.user.User;
import com.numan947.pmbackend.user.UserService;
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
    private final UserService userService;
    private final ProjectMapper projectMapper;

    @Override
    public ProjectShortResponse createProject(ProjectRequest projectRequest, Authentication connectedUser) {
        User user = (User) connectedUser.getPrincipal();
        // set the primitive and regular fields from the request
        Project project = projectMapper.toProject(projectRequest);
        // set the owner and team members
        project.setOwner(user); // set the owner of the project
        project.getTeamMembers().add(user); // add the owner to the team members
        projectRepository.save(project);

        userService.addProjectToUser(user.getId(), project); // add the project to the user
        return projectMapper.toProjectShortResponse(project); // list item response for the newly created project
    }


    // return all projects that the user is a member of -> owner or team member
    @Override
    public List<ProjectShortResponse> getAllProjectsOfUser(Authentication connectedUser, List<String> categories, List<String>tags, int page, int size) {
        // TODO: Implement pagination later here
        User user = (User) connectedUser.getPrincipal();
        List<Project> projects = projectRepository.findAllByTeamMembersContainingOrOwnerId(user.getId(), user.getId());

        //filter by category and tag
        if (!categories.isEmpty()) {
            projects = projects.stream().filter(project->project.getCategories().stream().anyMatch(categories::contains)).toList();
        }
        if (!tags.isEmpty()) {
            projects = projects.stream().filter(project -> project.getTags().stream().anyMatch(tags::contains)).toList();
        }

        return projects.stream().map(projectMapper::toProjectShortResponse).toList();
    }

    @Override
    public ProjectResponse getProjectById(String projectId, Authentication connectedUser) {
        User user = (User) connectedUser.getPrincipal();
        Project project = projectRepository.findById(projectId).
                orElseThrow(() -> new EntityNotFoundException("Project not found"));

        if(!project.getTeamMembers().stream().map(User::getId).toList().contains(user.getId())){ // compare Ids not objects
            throw new OperationNotPermittedException("User is not a member of this project");
        }
        return projectMapper.toProjectResponse(project);
    }

    @Override
    public void deleteProject(String projectId, Authentication connectedUser) {
        User user = (User) connectedUser.getPrincipal();
        Project project = projectRepository.findById(projectId).
                orElseThrow(() -> new EntityNotFoundException("Project not found"));
        if(!project.getOwner().getId().equals(user.getId())){
            throw new OperationNotPermittedException("User is not the owner of this project");
        }
        userService.removeProjectFromUser(user.getId(), project); // remove the project from the user
        projectRepository.delete(project); // delete the project
    }

    @Override
    public ProjectShortResponse updateProject(ProjectRequest projectRequest, Authentication connectedUser) {
        if (projectRequest.id() == null || projectRequest.id().isEmpty()) {
            throw new OperationNotPermittedException("Project id is required");
        }
        User user = (User) connectedUser.getPrincipal();
        Project project = projectRepository.findById(projectRequest.id()).
                orElseThrow(() -> new EntityNotFoundException("Project not found"));
        if(!project.getOwner().getId().equals(user.getId())){
            throw new OperationNotPermittedException("User is not the owner of this project");
        }
        project.setName(projectRequest.name());
        project.setDescription(projectRequest.description());
        project.setCategories(projectRequest.categories());
        project.setTags(projectRequest.tags());
        projectRepository.save(project);
        return projectMapper.toProjectShortResponse(project);
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

    @Override
    public List<ProjectShortResponse> getAllTeamProjectsOfUser(Authentication auth) {
        User user = (User) auth.getPrincipal();
        return projectRepository.findAllTeamProjectsByUserId(user.getId()).stream().map(projectMapper::toProjectShortResponse).toList();
    }

    @Override
    public List<ProjectShortResponse> getAllOwnProjectsOfUser(Authentication auth) {
        User user = (User) auth.getPrincipal();
        List<Project> projects = projectRepository.findAllByOwnerId(user.getId());
        return projects.stream().map(projectMapper::toProjectShortResponse).toList();
    }


    @Override
    public void addTeamMemberToProject(String projectId, String memberId) {
        // validate project and user
        Project project = projectRepository.findProjectById(projectId).orElseThrow(
                () -> new EntityNotFoundException("Project not found")
        ); // check if the project exists
        User user = userService.findByUserId(memberId).orElseThrow(() -> new EntityNotFoundException("User not found"));
        if (project.getTeamMembers().stream().anyMatch(member -> member.getId().equals(memberId))) {
            throw new OperationNotPermittedException("User is already a member of the project");
        } // check if the user is already a member of the project -> this also checks if the user is the owner of the project because the owner is also a member

        project.getTeamMembers().add(user);
        projectRepository.save(project);
        userService.addProjectToUser(memberId, project);
    }

    @Override
    public void removeTeamMemberFromProject(String projectId, String memberId) {
        Project project = projectRepository.findProjectById(projectId).orElseThrow(
                () -> new EntityNotFoundException("Project not found")
        ); // check if the project exists
        User user = userService.findByUserId(memberId).orElseThrow(() -> new EntityNotFoundException("User not found"));
        if (project.getOwner().getId().equals(memberId)) {
            throw new OperationNotPermittedException("Owner cannot be removed from the project");
        } // check if the user is the owner of the project

        if (project.getTeamMembers().stream().noneMatch(member -> member.getId().equals(memberId))) {
            throw new OperationNotPermittedException("User is not a member of the project");
        } // check if the user is a member of the project

        project.getTeamMembers().removeIf(member -> member.getId().equals(memberId));
        projectRepository.save(project);
        userService.removeProjectFromUser(memberId, project);
    }

    @Override
    public void addIssueToProject(String projectId, Issue issue) {
        Project project = projectRepository.findProjectById(projectId).orElseThrow(
                () -> new EntityNotFoundException("Project not found")
        );
        project.getIssues().add(issue);
        projectRepository.save(project);
    }

    @Override
    public void removeIssueFromProject(String projectId, Issue issue) {
        Project project = projectRepository.findProjectById(projectId).orElseThrow(
                () -> new EntityNotFoundException("Project not found")
        );
        project.getIssues().remove(issue);
        projectRepository.save(project);
    }
}
