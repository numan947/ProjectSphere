package com.numan947.pmbackend.primary_packages.project;

import com.numan947.pmbackend.primary_packages.project.dto.ProjectRequest;
import com.numan947.pmbackend.primary_packages.project.dto.ProjectResponse;
import com.numan947.pmbackend.primary_packages.project.dto.ProjectShortResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
@Tag(name = "Project", description = "Project related endpoints")
public class ProjectController {
    private final String defaultPage = "0";
    private final String defaultPageSize = "5";
    private final ProjectService projectService;

    @GetMapping
    public ResponseEntity<List<ProjectShortResponse>>getAllProjects(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String tag,
            @RequestParam(defaultValue = defaultPage) Integer page,
            @RequestParam(defaultValue = defaultPageSize) Integer size,
            Authentication auth
    ){
        return ResponseEntity.ok(projectService.getAllProjectsOfUser(auth, category, tag, page, size));
    }

    @GetMapping("/{project-id}")
    public ResponseEntity<ProjectResponse>getProjectById(
            @PathVariable("project-id") String projectId,
            Authentication auth
    ){
        return ResponseEntity.ok(projectService.getProjectById(projectId, auth));
    }

    @PostMapping("/create")
    public ResponseEntity<ProjectShortResponse>CreateProject(
            @RequestBody ProjectRequest projectRequest,
            Authentication auth
    ){
        return ResponseEntity.ok(projectService.createProject(projectRequest, auth));
    }

    @PatchMapping("/update/")
    public ResponseEntity<ProjectShortResponse>updateProject(
            @RequestBody ProjectRequest projectRequest,
            Authentication auth
    ){
        return ResponseEntity.ok(projectService.updateProject(projectRequest, auth));
    }

    @DeleteMapping("/delete/{project-id}")
    public ResponseEntity<Void>deleteProject(
            @PathVariable("project-id") String projectId,
            Authentication auth
    ){
        projectService.deleteProject(projectId, auth);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/search")
    public List<ProjectShortResponse>searchProject(
            @RequestParam String searchKey,
            Authentication auth,
            @RequestParam(defaultValue = defaultPage) Integer page,
            @RequestParam(defaultValue = defaultPageSize) Integer size
    ){
        return projectService.searchProjects(searchKey, auth, page, size);
    }

}
