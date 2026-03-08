package com.yash.tda.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "projects")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Repository URL is required")
    @Column(nullable = false)
    private String repositoryUrl;

    @NotBlank(message = "Repository name is required")
    @Column(nullable = false)
    private String repositoryName;

    private LocalDateTime createdAt;

    //@OneToOne(mappedBy = "project", cascade = CascadeType.ALL)
    @JsonManagedReference
    //private Analysis analysis;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
}