package com.yash.tda.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class AnalysisResponseDTO {

    private Long id;
    private int totalFiles;
    private int totalLines;
    private double debtScore;
    private String riskLevel;
    private String status;
    private LocalDateTime createdAt;
}