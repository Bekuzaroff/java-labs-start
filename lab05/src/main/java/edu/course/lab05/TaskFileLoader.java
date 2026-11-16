package edu.course.lab05;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public final class TaskFileLoader {

    private static final String EXPECTED_HEADER = "id;title;category;estimatedHours;status";

    public LoadResult load(Path path) {
        List<String> lines;
        try {
            lines = Files.readAllLines(path, StandardCharsets.UTF_8);
        } catch (IOException cause) {
            throw new DataLoadingException("Cannot read " + path, cause);
        }

        if (lines.isEmpty()) {
            throw new DataLoadingException("File is empty: " + path, null);
        }

        String header = lines.get(0).trim();
        if (!EXPECTED_HEADER.equals(header)) {
            throw new DataLoadingException("Invalid header: " + header, null);
        }

        List<ProjectTask> validTasks = new ArrayList<>();
        List<ValidationError> errors = new ArrayList<>();

        for (int i = 1; i < lines.size(); i++) {
            String line = lines.get(i);
            int lineNumber = i + 1;

            if (line.isBlank()) {
                continue;
            }

            String[] parts = line.split(";", -1);
            if (parts.length != 5) {
                errors.add(new ValidationError(lineNumber, "columns", "Expected 5 columns, got " + parts.length));
                continue;
            }

            String id = parts[0].trim();
            String title = parts[1].trim();
            String category = parts[2].trim();
            String hoursStr = parts[3].trim();
            String statusStr = parts[4].trim();

            if (id.isEmpty()) {
                errors.add(new ValidationError(lineNumber, "id", "id cannot be empty"));
                continue;
            }
            if (title.isEmpty()) {
                errors.add(new ValidationError(lineNumber, "title", "title cannot be empty"));
                continue;
            }
            if (category.isEmpty()) {
                errors.add(new ValidationError(lineNumber, "category", "category cannot be empty"));
                continue;
            }

            int estimatedHours;
            try {
                estimatedHours = Integer.parseInt(hoursStr);
            } catch (NumberFormatException e) {
                errors.add(new ValidationError(lineNumber, "estimatedHours", "Invalid integer: " + hoursStr));
                continue;
            }

            if (estimatedHours < 0) {
                errors.add(new ValidationError(lineNumber, "estimatedHours", "Cannot be negative: " + estimatedHours));
                continue;
            }

            TaskStatus status;
            try {
                status = TaskStatus.valueOf(statusStr);
            } catch (IllegalArgumentException e) {
                errors.add(new ValidationError(lineNumber, "status", "Unknown status: " + statusStr));
                continue;
            }

            validTasks.add(new ProjectTask(id, title, category, estimatedHours, status));
        }

        return new LoadResult(validTasks, errors);
    }
}
