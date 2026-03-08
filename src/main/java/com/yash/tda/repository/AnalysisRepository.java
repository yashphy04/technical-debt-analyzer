package com.yash.tda.repository;

import com.yash.tda.model.Analysis;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AnalysisRepository extends JpaRepository<Analysis, Long> {

    boolean existsByProjectId(Long projectId);

    Optional<Analysis> findByProjectId(Long projectId);
}