package taskManagers;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.tasktraker.controller.*;
import ru.yandex.practicum.tasktraker.tasks.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TaskManagerTest {
    InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();
    Task task = new Task("Task_Test", "Test", TaskStatus.NEW);
    Epic epic = new Epic("Epic_Test", "Test");
    Subtask subtask = new Subtask("Subtask_Test", "Test", TaskStatus.NEW, 2);

    @Test
    void test1_ShouldAddTasks() {
        inMemoryTaskManager.addTask(task);
        inMemoryTaskManager.addEpic(epic);
        inMemoryTaskManager.addSubtask(subtask);
        assertEquals(task, inMemoryTaskManager.getTask(1));
        assertEquals(epic, inMemoryTaskManager.getEpic(2));
        assertEquals(subtask, inMemoryTaskManager.getSubtask(3));
    }

    @Test
    void test2_ShouldAddEmptyTasks() {
        task = null;
        epic = null;
        subtask = null;
        inMemoryTaskManager.addTask(task);
        inMemoryTaskManager.addEpic(epic);
        inMemoryTaskManager.addSubtask(subtask);
        assertNull(inMemoryTaskManager.getTask(1));
        assertNull(inMemoryTaskManager.getEpic(2));
        assertNull(inMemoryTaskManager.getSubtask(3));
    }

    @Test
    void test3_ShouldAddSubtaskWithOutEpic() {
        inMemoryTaskManager.addSubtask(subtask);
        assertNull(inMemoryTaskManager.getSubtask(1));
    }

    @Test
    void test4_ShouldGetTasksWithIncorrectID() {
        assertNull(inMemoryTaskManager.getTask(66));
        assertNull(inMemoryTaskManager.getEpic(33));
        assertNull(inMemoryTaskManager.getSubtask(55));
    }

    @Test
    void test5_ShouldGetAllTask() {
        List<Task> testListTask = new ArrayList<>();
        inMemoryTaskManager.addTask(task);
        testListTask.add(task);
        task = new Task("Task_Test2", "Test", TaskStatus.NEW);
        inMemoryTaskManager.addTask(task);
        testListTask.add(task);
        assertEquals(testListTask, inMemoryTaskManager.getAllTask());
    }

    @Test
    void test6_ShouldGetAllEpic() {
        List<Epic> testListEpic = new ArrayList<>();
        inMemoryTaskManager.addEpic(epic);
        testListEpic.add(epic);
        epic = new Epic("Epic_Test2", "Test");
        inMemoryTaskManager.addEpic(epic);
        testListEpic.add(epic);
        assertEquals(testListEpic, inMemoryTaskManager.getAllEpic());
    }

    @Test
    void test7_ShouldGetAllSubtask() {
        List<Subtask> testListSubtask = new ArrayList<>();
        inMemoryTaskManager.addTask(task);
        inMemoryTaskManager.addEpic(epic);
        inMemoryTaskManager.addSubtask(subtask);
        testListSubtask.add(subtask);
        subtask = new Subtask("Subtask_Test2", "Test", TaskStatus.NEW, 2);
        inMemoryTaskManager.addSubtask(subtask);
        testListSubtask.add(subtask);
        assertEquals(testListSubtask, inMemoryTaskManager.getAllSubtask());
    }

    @Test
    void test8_ShouldGetAllTasksWithEmptyList() {
        assertTrue(inMemoryTaskManager.getAllTask().isEmpty());
        assertTrue(inMemoryTaskManager.getAllEpic().isEmpty());
        assertTrue(inMemoryTaskManager.getAllSubtask().isEmpty());
    }

    @Test
    void test9_ShouldDeleteTask() {
        inMemoryTaskManager.addTask(task);
        inMemoryTaskManager.deleteTask(1);
        assertNull(inMemoryTaskManager.getTask(1));
    }

    @Test
    void test10_ShouldDeleteEpic() {
        inMemoryTaskManager.addEpic(epic);
        subtask = new Subtask("Subtask_Test", "Test", TaskStatus.NEW, 1);
        inMemoryTaskManager.addSubtask(subtask);
        inMemoryTaskManager.deleteEpic(1);
        assertNull(inMemoryTaskManager.getEpic(1));
        assertNull(inMemoryTaskManager.getSubtask(2));
    }

    @Test
    void test11_ShouldDeleteSubtask() {
        inMemoryTaskManager.addEpic(epic);
        subtask = new Subtask("Subtask_Test", "Test", TaskStatus.NEW, 1);
        inMemoryTaskManager.addSubtask(subtask);
        inMemoryTaskManager.deleteSubtask(2);
        assertNull(inMemoryTaskManager.getSubtask(2));
    }

    @Test
    void test12_ShouldDeleteTasksWithIncorrectID() {
        inMemoryTaskManager.deleteTask(44);
        inMemoryTaskManager.deleteEpic(77);
        inMemoryTaskManager.deleteSubtask(788);
        assertNull(inMemoryTaskManager.getTask(1));
        assertNull(inMemoryTaskManager.getEpic(2));
        assertNull(inMemoryTaskManager.getSubtask(1));
    }

    @Test
    void test13_ShouldDeleteAllTasks() {
        inMemoryTaskManager.addTask(task);
        inMemoryTaskManager.addEpic(epic);
        inMemoryTaskManager.addSubtask(subtask);
        inMemoryTaskManager.deleteAllTask();
        inMemoryTaskManager.deleteAllEpic();
        inMemoryTaskManager.deleteAllSubtask();
        assertTrue(inMemoryTaskManager.getAllTask().isEmpty());
        assertTrue(inMemoryTaskManager.getAllEpic().isEmpty());
        assertTrue(inMemoryTaskManager.getAllSubtask().isEmpty());
    }

    @Test
    void test14_ShouldDeleteAllTasksWithOutTasks() {
        inMemoryTaskManager.deleteAllTask();
        inMemoryTaskManager.deleteAllEpic();
        inMemoryTaskManager.deleteAllSubtask();
        assertTrue(inMemoryTaskManager.getAllTask().isEmpty());
        assertTrue(inMemoryTaskManager.getAllEpic().isEmpty());
        assertTrue(inMemoryTaskManager.getAllSubtask().isEmpty());
    }

    @Test
    void test15_ShouldUpdateTask() {
        inMemoryTaskManager.addTask(task);
        Task task1 = new Task("Task_Test2", "Test", TaskStatus.IN_PROGRESS);
        inMemoryTaskManager.updateTask(1, task1);
        assertNotEquals(task, inMemoryTaskManager.getTask(1));
    }

    @Test
    void test16_ShouldUpdateTaskThrowsException() {
        inMemoryTaskManager.addTask(task);
        final NullPointerException exception = assertThrows(
                NullPointerException.class,
                () -> {
                    task = null;
                    inMemoryTaskManager.updateTask(1, task);
                    inMemoryTaskManager.getTask(1);
                }
        );
        assertNull(exception.getMessage());
    }

    @Test
    void test17_ShouldUpdateTaskWithIncorrectID() {
        inMemoryTaskManager.addTask(task);
        Task task1 = new Task("Task_Test2", "Test", TaskStatus.IN_PROGRESS);
        inMemoryTaskManager.updateTask(3, task1);
        assertNotEquals(task1, inMemoryTaskManager.getTask(1));
    }

    @Test
    void test18_ShouldUpdateEpic() {
        inMemoryTaskManager.addEpic(epic);
        Epic epic1 = new Epic("Epic_Test2", "Test");
        inMemoryTaskManager.updateEpic(1, epic1);
        assertNotEquals(epic, inMemoryTaskManager.getEpic(1));
    }

    @Test
    void test19_ShouldUpdateEpicThrowsException() {
        inMemoryTaskManager.addEpic(epic);
        final NullPointerException exception = assertThrows(
                NullPointerException.class,
                () -> {
                    epic = null;
                    inMemoryTaskManager.updateEpic(1, epic);
                    inMemoryTaskManager.getEpic(1);
                }
        );
        assertNull(exception.getMessage());
    }

    @Test
    void test20_ShouldUpdateEpicWithIncorrectIDThrowsException() {
        inMemoryTaskManager.addEpic(epic);
        Epic epic1 = new Epic("Epic_Test2", "Test");
        final NullPointerException exception = assertThrows(
                NullPointerException.class,
                () -> inMemoryTaskManager.updateEpic(6, epic1)
        );
        assertNull(exception.getMessage());
    }

    @Test
    void test21_ShouldUpdateSubtask() {
        inMemoryTaskManager.addEpic(epic);
        subtask = new Subtask("Subtask_Test", "Test", TaskStatus.NEW, 1);
        inMemoryTaskManager.addSubtask(subtask);
        Subtask subtask1 = new Subtask("Subtask_Test2", "Test",
                TaskStatus.IN_PROGRESS, 1);
        inMemoryTaskManager.updateSubtask(2, subtask1);
        assertEquals(subtask1, inMemoryTaskManager.getSubtask(2));
    }

    @Test
    void test21_ShouldUpdateSubtaskWithIncorrectID() {
        inMemoryTaskManager.addEpic(epic);
        subtask = new Subtask("Subtask_Test", "Test", TaskStatus.NEW, 1);
        inMemoryTaskManager.addSubtask(subtask);
        Subtask subtask1 = new Subtask("Subtask_Test2", "Test",
                TaskStatus.IN_PROGRESS, 1);
        inMemoryTaskManager.updateSubtask(6, subtask1);
        assertNotEquals(subtask1, inMemoryTaskManager.getSubtask(2));
    }
}
