package tasks;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.tasktraker.controller.*;
import ru.yandex.practicum.tasktraker.tasks.*;

import static org.junit.jupiter.api.Assertions.*;

class EpicTest {
    private InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();
    private Subtask subtask = new Subtask("Subtask-1", "0", TaskStatus.NEW, 1);
    private Subtask subtask1 = new Subtask("Subtask-1", "0", TaskStatus.NEW, 1);
    private Epic epic = new Epic("Epic_TEST", "TEST");

    @Test
    void test1_ShoulEpicStattusWithOutSubtasks() {
        inMemoryTaskManager.addEpic(epic);
        assertEquals(TaskStatus.NEW, inMemoryTaskManager.getEpic(1).getStatus());
    }

    @Test
    void test2_EpicStattusWithSubtasksNew() {
        inMemoryTaskManager.addEpic(epic);
        inMemoryTaskManager.addSubtask(subtask);
        inMemoryTaskManager.addSubtask(subtask1);
        assertEquals(TaskStatus.NEW, inMemoryTaskManager.getEpic(1).getStatus());
    }

    @Test
    void test3_EpicStatusWithSubtasksDone() {
        inMemoryTaskManager.addEpic(epic);
        subtask.setStatus(TaskStatus.DONE);
        subtask1.setStatus(TaskStatus.DONE);
        inMemoryTaskManager.addSubtask(subtask);
        inMemoryTaskManager.addSubtask(subtask1);
        assertEquals(TaskStatus.DONE, inMemoryTaskManager.getEpic(1).getStatus());
    }

    @Test
    void test4_EpicStatusWithSubtasksNewAndDone() {
        inMemoryTaskManager.addEpic(epic);
        subtask1.setStatus(TaskStatus.DONE);
        inMemoryTaskManager.addSubtask(subtask);
        inMemoryTaskManager.addSubtask(subtask1);
        assertEquals(TaskStatus.IN_PROGRESS, inMemoryTaskManager.getEpic(1).getStatus());
    }

    @Test
    void test5_EpicStatusWithSubtasksInProgress() {
        inMemoryTaskManager.addEpic(epic);
        subtask.setStatus(TaskStatus.IN_PROGRESS);
        subtask1.setStatus(TaskStatus.IN_PROGRESS);
        inMemoryTaskManager.addSubtask(subtask);
        inMemoryTaskManager.addSubtask(subtask1);
        assertEquals(TaskStatus.IN_PROGRESS, inMemoryTaskManager.getEpic(1).getStatus());
    }
}