package com.yash.tda.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "file_metrics")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FileMetrics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String filePath;

    private int linesOfCode;

    private int methodCount;

    private int complexityScore;

    @ManyToOne
    @JoinColumn(name = "analysis_id", nullable = false)
    private Analysis analysis;
}