package edu.course.lab03;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TaskPrioritizerTest {

    @Test
    public void testEffortPriorityPolicy() {
        PriorityPolicy policy = new EffortPriority();
        ProjectTask t1 = new ProjectTask("5", "Task 1", 2);
        ProjectTask t2 = new ProjectTask("3", "Task 2", 12);

        assertEquals(-2, policy.calculatePriority(t1));
        assertEquals(-12, policy.calculatePriority(t2));
    }

    @Test
    public void testStatusPriorityPolicy() {
        PriorityPolicy policy = new StatusPriority();
        ProjectTask t1 = new ProjectTask("5", "Task 1", 2);
        ProjectTask t2 = new ProjectTask("3", "Task 2", 12);

        t1.changeStatus(TaskStatus.LOADING);
        t2.changeStatus(TaskStatus.TODO);

        assertEquals(2, policy.calculatePriority(t1));
        assertEquals(1, policy.calculatePriority(t2));
    }

    @Test
    public void testTaskPrioritizerWithDifferentPolicies() {
        ProjectTask t1 = new ProjectTask("5", "Task 1", 2);
        ProjectTask t2 = new ProjectTask("3", "Task 2", 12);

        // 1. Тестируем с EffortPolicy (t1 выиграет: 2 > 12)
        TaskPrioritizer prioritizer1 = new TaskPrioritizer(new EffortPriority());
        assertEquals(t1, prioritizer1.chooseTask(t1, t2));

        // 2. Тестируем со StatusPolicy (t2 выиграет: LOADING > TODO)
        t2.changeStatus(TaskStatus.LOADING);
        t1.changeStatus(TaskStatus.TODO);
        TaskPrioritizer prioritizer2 = new TaskPrioritizer(new StatusPriority());
        assertEquals(t2, prioritizer2.chooseTask(t1, t2));
    }

}
