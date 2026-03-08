package com.yash.tda.repository;

import com.yash.tda.model.CodeIssue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CodeIssueRepository extends JpaRepository<CodeIssue, Long> {

    List<CodeIssue> findByAnalysisId(Long analysisId);
}