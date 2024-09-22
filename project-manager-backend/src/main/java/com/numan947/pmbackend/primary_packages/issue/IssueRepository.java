package com.numan947.pmbackend.primary_packages.issue;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface IssueRepository extends JpaRepository<Issue, String> {
    @Query("SELECT i FROM Issue i WHERE i.project.id = :projectId")
    List<Issue> findAllByProjectId(String projectId);

    Optional<Issue> findByProjectIdAndId(String projectId, String issueId);


}
