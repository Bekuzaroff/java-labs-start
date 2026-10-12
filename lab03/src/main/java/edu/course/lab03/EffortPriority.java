package edu.course.lab03;



public class EffortPriority implements PriorityPolicy{
    @Override
    public int calculatePriority(ProjectTask task) {
        return -task.getEstimatedHours();
    }
}
