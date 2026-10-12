package edu.course.lab03;


public class TaskPrioritizer {
    private PriorityPolicy priorityPolicy;

    TaskPrioritizer(PriorityPolicy priorityPolicy){
        this.priorityPolicy = priorityPolicy;
    }

    ProjectTask chooseTask(ProjectTask t1, ProjectTask t2){
        int r1 = priorityPolicy.calculatePriority(t1);
        int r2 = priorityPolicy.calculatePriority(t2);

        return (r1 >= r2) ? t1 : t2;
    }

    PriorityPolicy getPolicy () { return this.priorityPolicy; }
}
