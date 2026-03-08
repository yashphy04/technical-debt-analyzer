package com.yash.tda.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "analysis",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "project_id")
        })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Analysis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int totalFiles;

    private int totalLines;

    private double debtScore;

    private String riskLevel;

    private String status; // PROCESSING, COMPLETED, FAILED

    private LocalDateTime createdAt;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false, unique = true)
    @JsonBackReference
    private Project project;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
}