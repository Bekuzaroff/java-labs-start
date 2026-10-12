package edu.course.lab03;



public class StatusPriority implements PriorityPolicy{
    @Override
    public int calculatePriority(ProjectTask task) {
        if (task == null) {
            throw new IllegalArgumentException("task is null");
        }
        switch (task.getStatus()){
            case TaskStatus.LOADING -> {
                return 2;
            }
            case TaskStatus.TODO -> {
                return 1;
            }
            case TaskStatus.COMPLETED -> {
                return 0;
            }
        }
        return 0;
    }
}
