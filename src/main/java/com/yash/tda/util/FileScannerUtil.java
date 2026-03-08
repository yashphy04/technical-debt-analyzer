package com.yash.tda.util;

import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

@Component
public class FileScannerUtil {

    public Map<String, Integer> scanRepository(File repoDir) {

        Map<String, Integer> metrics = new HashMap<>();

        int totalFiles = 0;
        int totalLines = 0;
        int todos = 0;
        int largeFiles = 0;

        try (Stream<java.nio.file.Path> paths = Files.walk(repoDir.toPath())) {

            for (java.nio.file.Path path : (Iterable<java.nio.file.Path>) paths::iterator) {

                File file = path.toFile();

                if (file.isFile() && file.getName().endsWith(".java")) {

                    totalFiles++;

                    try {

                        long lines = Files.lines(path).count();
                        totalLines += lines;

                        if (lines > 500) {
                            largeFiles++;
                        }

                        long todoCount = Files.lines(path)
                                .filter(line -> line.contains("TODO"))
                                .count();

                        todos += todoCount;

                    } catch (IOException ignored) {
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        metrics.put("files", totalFiles);
        metrics.put("lines", totalLines);
        metrics.put("todos", todos);
        metrics.put("largeFiles", largeFiles);

        return metrics;
    }
}