package edu.course.lab02;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ProjectTaskTest {
    @Test
    void testCorrectObjectCreation() {
        ProjectTask task = new ProjectTask("TASK-1", "Create Auth", 5);
        assertEquals("TASK-1", task.getId());
        assertEquals("Create Auth", task.getTitle());
        assertEquals(5, task.getEstimatedHours());
        assertEquals(TaskStatus.TODO, task.getStatus());
        assertFalse(task.isCompleted());
    }

    @Test
    void testConstructorInvariants() {
        assertThrows(IllegalArgumentException.class, () -> new ProjectTask(null, "Task", 5));
        assertThrows(IllegalArgumentException.class, () -> new ProjectTask("   ", "Task", 5));
        assertThrows(IllegalArgumentException.class, () -> new ProjectTask("ID", null, 5));
        assertThrows(IllegalArgumentException.class, () -> new ProjectTask("ID", "", 5));
        assertThrows(IllegalArgumentException.class, () -> new ProjectTask("ID", "Task", -1));
    }

    @Test
    void testChangeStatus() {
        ProjectTask task = new ProjectTask("ID", "Task", 5);
        task.changeStatus(TaskStatus.LOADING);
        assertEquals(TaskStatus.LOADING, task.getStatus());
        assertFalse(task.isCompleted());

        task.changeStatus(TaskStatus.COMPLETED);
        assertTrue(task.isCompleted());
    }

    @Test
    void testChangeStatusThrowsOnNull() {
        ProjectTask task = new ProjectTask("ID", "Task", 5);
        assertThrows(IllegalArgumentException.class, () -> task.changeStatus(null));
    }

    @Test
    void testIncreaseEstimatedHours() {
        ProjectTask task = new ProjectTask("ID", "Task", 5);
        task.increaseEstimation(3);
        assertEquals(8, task.getEstimatedHours());
    }

    @Test
    void testIncreaseEstimatedHoursThrowsOnNonPositive() {
        ProjectTask task = new ProjectTask("ID", "Task", 5);
        assertThrows(IllegalArgumentException.class, () -> task.increaseEstimation(0));
        assertThrows(IllegalArgumentException.class, () -> task.increaseEstimation(-5));
    }
}
