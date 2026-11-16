package edu.course.lab05;

public class ProjectTask {
    private final String id;
    private final String title;
    private final String category;
    private TaskStatus taskStatus;
    private int estimatedHours;

    public ProjectTask(String id, String title, String category, int estimatedHours, TaskStatus status) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("id can not be null or blank");
        }
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("title can not be null or blank");
        }
        if (category == null || category.isBlank()) {
            throw new IllegalArgumentException("category can not be null or blank");
        }
        if (estimatedHours < 0) {
            throw new IllegalArgumentException("estimated hours cant be negative: " + estimatedHours);
        }
        if (status == null) {
            throw new IllegalArgumentException("status can not be null");
        }
        this.id = id;
        this.title = title;
        this.category = category;
        this.estimatedHours = estimatedHours;
        this.taskStatus = status;
    }

    public ProjectTask(String id, String title, int estimatedHours) {
        this(id, title, "general", estimatedHours, TaskStatus.TODO);
    }

    public void changeStatus(TaskStatus newStatus) {
        if (newStatus == null) {
            throw new IllegalArgumentException("new status can't be null");
        }
        this.taskStatus = newStatus;
    }

    public boolean isCompleted() {
        return this.taskStatus == TaskStatus.COMPLETED;
    }

    public void increaseEstimation(int hours) {
        if (hours <= 0) {
            throw new IllegalArgumentException("hours can't be zero or negative");
        }
        this.estimatedHours += hours;
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getCategory() { return category; }
    public TaskStatus getStatus() { return taskStatus; }
    public int getEstimatedHours() { return estimatedHours; }
}
