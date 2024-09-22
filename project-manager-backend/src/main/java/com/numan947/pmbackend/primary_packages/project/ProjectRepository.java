package com.numan947.pmbackend.primary_packages.project;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project, String> {
    List<Project> findByOwnerId(String ownerId);

    List<Project>findProjectByNameContainingAndOwnerId(String partialName, String ownerId);

    @Query("SELECT p FROM Project p JOIN p.teamMembers m WHERE m.id = :memberId")
    List<Project>findProjectByMembersId(String memberId);

    @Query("SELECT p FROM Project p JOIN p.teamMembers m WHERE m.id = :memberId OR p.owner.id = :ownerId") // TODO: as all owners are also team members,
                                                                                                            // this query is inefficient -- make it efficient
    List<Project> findAllByTeamMembersContainingOrOwnerId(String memberId, String ownerId);

    @Query("SELECT p FROM Project p JOIN p.teamMembers m WHERE m.id = :memberId AND p.name LIKE %:nameKey% OR p.description LIKE %:descriptionKey%")
    List<Project>findAllByNameContainingOrDescriptionContaining(String nameKey, String descriptionKey, String memberId);

    @Query("SELECT p FROM Project p WHERE p.owner.id = :ownerId AND p.id = :projectId")
    Optional<Project> findProjectByIdAndOwnerId(String projectId, String ownerId);
    Optional<Project> findProjectById(String projectId);
}
