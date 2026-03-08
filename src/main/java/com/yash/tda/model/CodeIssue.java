package com.yash.tda.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "code_issues")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CodeIssue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String filePath;

    private String issueType;   // LONG_METHOD, GOD_CLASS, HIGH_COMPLEXITY

    private String severity;    // LOW, MEDIUM, HIGH

    @Column(length = 1000)
    private String description;

    @ManyToOne
    @JoinColumn(name = "analysis_id", nullable = false)
    private Analysis analysis;
}