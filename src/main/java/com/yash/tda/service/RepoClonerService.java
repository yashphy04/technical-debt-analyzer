package com.yash.tda.service;

import org.eclipse.jgit.api.Git;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class RepoClonerService {

    private static final String BASE_DIR = "D:/tda/repos/";

    public File cloneRepository(String repoUrl, Long projectId) {

        try {

            File directory = new File(BASE_DIR + "project-" + projectId);

            if (directory.exists()) {
                return directory;
            }

            System.out.println("Cloning repository into: " + directory.getAbsolutePath());

            Git.cloneRepository()
                    .setURI(repoUrl)
                    .setDirectory(directory)
                    .call();

            return directory;

        } catch (Exception e) {
            throw new RuntimeException("Failed to clone repository", e);
        }
    }
}