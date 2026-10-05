package edu.course.lab02;

public record TaskId(String value) {
    public TaskId {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Идентификатор задачи не может быть пустым");
        }
        if (!value.matches("[A-Z]+-\\d+")) {
            throw new IllegalArgumentException("wrong ID format,");
        }
    }
}
