package com.yash.tda.repository;

import com.yash.tda.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProjectRepository extends JpaRepository<Project, Long> {
    boolean existsByRepositoryUrl(String repositoryUrl);
}