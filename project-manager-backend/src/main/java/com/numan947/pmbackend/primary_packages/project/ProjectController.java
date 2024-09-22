package com.numan947.pmbackend.primary_packages.project;

import com.numan947.pmbackend.exception.OperationNotPermittedException;
import com.numan947.pmbackend.primary_packages.project.dto.ProjectRequest;
import com.numan947.pmbackend.primary_packages.project.dto.ProjectResponse;
import com.numan947.pmbackend.primary_packages.project.dto.ProjectShortResponse;
import com.numan947.pmbackend.user.User;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.MethodArgumentNotValidException;
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

    @PostMapping("/create")
    public ResponseEntity<ProjectShortResponse>CreateProject( // tested
                                                              @RequestBody @Valid ProjectRequest projectRequest,
                                                              Authentication auth
    ){
        return ResponseEntity.ok(projectService.createProject(projectRequest, auth));
    }


    @GetMapping
    public ResponseEntity<List<ProjectShortResponse>>getAllProjects(//tested
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String tag,
            @RequestParam(defaultValue = defaultPage) Integer page,
            @RequestParam(defaultValue = defaultPageSize) Integer size,
            Authentication auth
    ){
        return ResponseEntity.ok(projectService.getAllProjectsOfUser(auth, category, tag, page, size));
    }

    @GetMapping("/{project-id}")
    public ResponseEntity<ProjectResponse>getProjectById(//tested
            @PathVariable("project-id") @NotBlank @NotEmpty @NotBlank @Valid  String projectId,
            Authentication auth
    ){
        return ResponseEntity.ok(projectService.getProjectById(projectId, auth));


    }
    @DeleteMapping("/delete/{project-id}")
    public ResponseEntity<Void>deleteProject( // tested
            @PathVariable("project-id") @NotBlank @NotEmpty @NotBlank @Valid String projectId,
            Authentication auth
    ){
        projectService.deleteProject(projectId, auth);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/own-projects")
    public ResponseEntity<List<ProjectShortResponse>>getOwnProjects(//tested
            Authentication auth
    ){
        return ResponseEntity.ok(projectService.getAllOwnProjects(auth));
    }


    @GetMapping("/team-projects")
    public ResponseEntity<List<ProjectShortResponse>>getTeamProjects(//tested
                                                                    Authentication auth
    ){
        return ResponseEntity.ok(projectService.getAllTeamProjectsOfUser(auth));
    }

    @PutMapping("/update")
    public ResponseEntity<ProjectShortResponse>updateProject( // tested
            @RequestBody @Valid ProjectRequest projectRequest,
            Authentication auth
    ){
        return ResponseEntity.ok(projectService.updateProject(projectRequest, auth));
    }

    @GetMapping("/search")
    public List<ProjectShortResponse>searchProject( // tested
            @RequestParam("search-key") @Valid @NotNull @NotEmpty @NotBlank String searchKey,
            Authentication auth,
            @RequestParam(defaultValue = defaultPage) Integer page,
            @RequestParam(defaultValue = defaultPageSize) Integer size
    ){
        return projectService.searchProjects(searchKey, auth, page, size);
    }


}
