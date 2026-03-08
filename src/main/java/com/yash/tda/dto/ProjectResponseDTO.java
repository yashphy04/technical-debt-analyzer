package com.yash.tda.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ProjectResponseDTO {

    private Long id;
    private String repositoryUrl;
    private String repositoryName;
    private LocalDateTime createdAt;
}