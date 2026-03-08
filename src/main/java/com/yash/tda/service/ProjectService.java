package com.yash.tda.service;

import com.yash.tda.dto.ProjectResponseDTO;
import com.yash.tda.exception.BusinessException;
import com.yash.tda.model.Project;
import com.yash.tda.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;

    @Transactional
    public ProjectResponseDTO createProject(Project project) {

        if (projectRepository.existsByRepositoryUrl(project.getRepositoryUrl())) {
            throw new BusinessException("Project already exists with this repository URL");
        }

        Project saved = projectRepository.save(project);

        return mapToDTO(saved);
    }

    public Page<ProjectResponseDTO> getAllProjects(Pageable pageable) {
        return projectRepository.findAll(pageable)
                .map(this::mapToDTO);
    }

    private ProjectResponseDTO mapToDTO(Project project) {
        return ProjectResponseDTO.builder()
                .id(project.getId())
                .repositoryUrl(project.getRepositoryUrl())
                .repositoryName(project.getRepositoryName())
                .createdAt(project.getCreatedAt())
                .build();
    }
}