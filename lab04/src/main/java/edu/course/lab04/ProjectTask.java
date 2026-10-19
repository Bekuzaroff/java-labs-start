package edu.course.lab04;

public class ProjectTask {
    private final String id;
    private final String title;
    private TaskStatus taskStatus;
    private int estimated_hours;

    ProjectTask(String id, String title, int estimated_hours) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("id can not be null or blank");
        }
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("title can not be null or blank");
        }
        if (estimated_hours < 0) {
            throw new IllegalArgumentException("estimated hourse cant be negative: " + estimated_hours);
        }
        this.id = id;
        this.title = title;
        this.taskStatus = TaskStatus.TODO;
        this.estimated_hours = estimated_hours;
    }


    void changeStatus(TaskStatus newStatus) {
        if (newStatus == null) {
            throw new IllegalArgumentException("new status can't be null");
        }
        this.taskStatus = newStatus;
    }

    boolean isCompleted() {
        return this.taskStatus == TaskStatus.COMPLETED;
    }

    void increaseEstimation(int hours) {
        if (hours <= 0) {
            throw new IllegalArgumentException("hours can't be zero or negative");
        }
        this.estimated_hours += hours;
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public TaskStatus getStatus() { return taskStatus; }
    public int getEstimatedHours() { return estimated_hours; }
}
