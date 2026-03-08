package com.yash.tda.controller;

import com.yash.tda.dto.ProjectResponseDTO;
import com.yash.tda.model.Project;
import com.yash.tda.service.ProjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping
    public ProjectResponseDTO createProject(@Valid @RequestBody Project project) {
        return projectService.createProject(project);
    }

    @GetMapping
    public Page<ProjectResponseDTO> getAllProjects(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).descending());
        return projectService.getAllProjects(pageable);
    }
}