package com.yash.tda.repository;

import com.yash.tda.model.FileMetrics;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FileMetricsRepository extends JpaRepository<FileMetrics, Long> {

    List<FileMetrics> findByAnalysisId(Long analysisId);
}