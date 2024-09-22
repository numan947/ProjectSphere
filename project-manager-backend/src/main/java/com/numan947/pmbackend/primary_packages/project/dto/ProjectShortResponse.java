package com.numan947.pmbackend.primary_packages.project.dto;

import com.numan947.pmbackend.user.dto.UserResponse;
import lombok.*;

import java.util.List;
/**
 * ProjectShortResponse is a data transfer object (DTO) that represents the response for Project Object when they are shown in lists.
 *
 * Fields:
 * - id: The unique identifier of the project.
 * - name: The name of the project.
 * - description: A brief description of the project.
 * - category: The category to which the project belongs.
 * - tags: A list of tags associated with the project.
 * - memberCount: The number of members in the project.
 * - issueCount: The number of issues associated with the project.
 * - owner: The owner of the project, represented by a UserResponse object.
 *
 * Annotations:
 * - @Getter: Lombok annotation to generate getter methods for all fields.
 * - @Setter: Lombok annotation to generate setter methods for all fields.
 * - @Builder: Lombok annotation to implement the builder pattern for the class.
 * - @AllArgsConstructor: Lombok annotation to generate a constructor with all fields as parameters.
 * - @NoArgsConstructor: Lombok annotation to generate a no-argument constructor.
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
// DATA TRANSFER OBJECT
public class ProjectShortResponse {
    private String id;
    private String name;
    private String description;
    private String category;
    private List<String> tags;
    private Integer memberCount;
    private Integer issueCount;
    private UserResponse owner;
}
