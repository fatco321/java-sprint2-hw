package taskmanagers;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.tasktraker.controller.InMemoryTaskManager;
import ru.yandex.practicum.tasktraker.tasks.Epic;
import ru.yandex.practicum.tasktraker.tasks.Subtask;
import ru.yandex.practicum.tasktraker.tasks.Task;
import ru.yandex.practicum.tasktraker.tasks.TaskStatus;

import static org.junit.jupiter.api.Assertions.*;

public class HistoryManagerTest {
    private InMemoryTaskManager taskManager = new InMemoryTaskManager();
    private Task task = new Task("Task_Test", "Test", TaskStatus.NEW);
    private Epic epic = new Epic("Epic_Test", "Test");
    private Subtask subtask = new Subtask("Subtask_Test", "Test", TaskStatus.NEW, 2);

    @Test
    void test1_ShouldGetEmptyHistory() {
        assertTrue(taskManager.history().isEmpty());
    }

    @Test
    void test2_ShouldGetDoubleHistory() {
        taskManager.addTask(task);
        taskManager.addEpic(epic);
        taskManager.addSubtask(subtask);
        taskManager.getTask(1);
        taskManager.getTask(1);
        taskManager.getEpic(2);
        assertEquals(2, taskManager.history().size());
    }

    @Test
    void test3_ShouldDeleteInHistoryBeginning() {
        taskManager.addTask(task);
        taskManager.addEpic(epic);
        taskManager.addSubtask(subtask);
        taskManager.getTask(1);
        taskManager.getEpic(2);
        taskManager.getSubtask(3);
        taskManager.getInMemoryHistoryManager().remove(1);
        assertEquals(2, taskManager.history().size());
    }

    @Test
    void test4_ShouldDeleteInHistoryMiddle() {
        taskManager.addTask(task);
        taskManager.addEpic(epic);
        taskManager.addSubtask(subtask);
        taskManager.getTask(1);
        taskManager.getEpic(2);
        taskManager.getSubtask(3);
        taskManager.getInMemoryHistoryManager().remove(2);
        assertEquals(2, taskManager.history().size());
    }

    @Test
    void test5_ShouldDeleteInHistoryEnd() {
        taskManager.addTask(task);
        taskManager.addEpic(epic);
        taskManager.addSubtask(subtask);
        taskManager.getTask(1);
        taskManager.getEpic(2);
        taskManager.getSubtask(3);
        taskManager.getInMemoryHistoryManager().remove(3);
        assertEquals(2, taskManager.history().size());
    }
}
