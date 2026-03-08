package com.yash.tda.controller;

import com.yash.tda.model.Analysis;
import com.yash.tda.repository.AnalysisRepository;
import com.yash.tda.service.AnalysisService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AnalysisController {

    private final AnalysisService analysisService;
    private final AnalysisRepository analysisRepository;

    public AnalysisController(AnalysisService analysisService,
                              AnalysisRepository analysisRepository) {
        this.analysisService = analysisService;
        this.analysisRepository = analysisRepository;
    }

    @PostMapping("/api/projects/{projectId}/analysis")
    public ResponseEntity<Analysis> createAnalysis(@PathVariable Long projectId) {

        Analysis analysis = analysisService.createAnalysis(projectId);

        // Trigger async execution
        analysisService.runAnalysisAsync(analysis.getId());

        return ResponseEntity.ok(analysis);
    }

    @GetMapping("/api/analysis/{analysisId}")
    public ResponseEntity<Analysis> getAnalysis(@PathVariable Long analysisId) {
        return ResponseEntity.ok(
                analysisRepository.findById(analysisId)
                        .orElseThrow(() -> new RuntimeException("Analysis not found"))
        );
    }
}