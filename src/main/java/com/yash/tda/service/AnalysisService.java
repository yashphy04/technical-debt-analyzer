package com.yash.tda.service;

import com.yash.tda.model.Analysis;
import com.yash.tda.model.Project;
import com.yash.tda.repository.AnalysisRepository;
import com.yash.tda.repository.ProjectRepository;
import com.yash.tda.util.FileScannerUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Service
public class AnalysisService {

    @Autowired
    private AnalysisRepository analysisRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private RepoClonerService repoClonerService;

    @Autowired
    private FileScannerUtil fileScannerUtil;

    /**
     * Create a new analysis record
     */
    public Analysis createAnalysis(Long projectId) {

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        Analysis analysis = new Analysis();

        analysis.setProject(project);
        analysis.setStatus("PROCESSING");
        analysis.setTotalFiles(0);
        analysis.setTotalLines(0);
        analysis.setDebtScore(0.0);
        analysis.setRiskLevel(null);

        return analysisRepository.save(analysis);
    }

    /**
     * Run analysis asynchronously
     */
    @Async
    @Transactional
    public CompletableFuture<Void> runAnalysisAsync(Long analysisId) {

        Analysis analysis = analysisRepository.findById(analysisId)
                .orElseThrow(() -> new RuntimeException("Analysis not found"));

        try {

            Project project = projectRepository.findById(
                            analysis.getProject().getId())
                    .orElseThrow(() -> new RuntimeException("Project not found"));

            String repoUrl = project.getRepositoryUrl();

            System.out.println("Cloning repo: " + repoUrl);

            File repoDir = repoClonerService.cloneRepository(repoUrl, project.getId());

            System.out.println("Scanning repository...");

            Map<String, Integer> metrics = fileScannerUtil.scanRepository(repoDir);

            int totalFiles = metrics.getOrDefault("files", 0);
            int totalLines = metrics.getOrDefault("lines", 0);
            int todos = metrics.getOrDefault("todos", 0);
            int largeFiles = metrics.getOrDefault("largeFiles", 0);

            double debtScore = (totalLines / (double) Math.max(totalFiles, 1))
                    + (todos * 0.5)
                    + (largeFiles * 1.5);

            String riskLevel;

            if (debtScore < 20) {
                riskLevel = "LOW";
            } else if (debtScore < 50) {
                riskLevel = "MEDIUM";
            } else {
                riskLevel = "HIGH";
            }

            analysis.setTotalFiles(totalFiles);
            analysis.setTotalLines(totalLines);
            analysis.setDebtScore(debtScore);
            analysis.setRiskLevel(riskLevel);
            analysis.setStatus("COMPLETED");

        } catch (Exception e) {

            e.printStackTrace();
            analysis.setStatus("FAILED");
        }

        analysisRepository.save(analysis);

        return CompletableFuture.completedFuture(null);
    }
}