package taskmanagers;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.tasktraker.controller.TaskManager;
import ru.yandex.practicum.tasktraker.tasks.Epic;
import ru.yandex.practicum.tasktraker.tasks.Subtask;
import ru.yandex.practicum.tasktraker.tasks.Task;
import ru.yandex.practicum.tasktraker.tasks.TaskStatus;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

abstract class TaskManagerTest<T extends TaskManager> {
    private T manager;
    private Task task = new Task("Task_Test", "Test", TaskStatus.NEW);
    private Epic epic = new Epic("Epic_Test", "Test");
    private Subtask subtask = new Subtask("Subtask_Test", "Test", TaskStatus.NEW, 1);

    public void setManager(T manager) {
        this.manager = manager;
    }

    @Test
    void test01_addTask() {
        manager.addTask(task);
        assertEquals(1, manager.getAllTask().size());
    }

    @Test
    void test02_addNullTask() {
        manager.addTask(null);
        assertTrue(manager.getAllTask().isEmpty());
    }

    @Test
    void test03_addEpic() {
        manager.addEpic(epic);
        assertEquals(1, manager.getAllEpic().size());
    }

    @Test
    void test04_addNullEpic() {
        manager.addEpic(null);
        assertTrue(manager.getAllEpic().isEmpty());
    }

    @Test
    void test05_addSubtaskWithEpic() {
        manager.addEpic(epic);
        manager.addSubtask(subtask);
        assertEquals(1, manager.getAllSubtask().size());
    }

    @Test
    void test06_addSubtaskWithOutEpic() {
        manager.addSubtask(subtask);
        assertTrue(manager.getAllSubtask().isEmpty());
    }

    @Test
    void test07_getTasksWithCorrectId() {
        manager.addEpic(epic);
        manager.addSubtask(subtask);
        manager.addTask(task);
        assertEquals(epic, manager.getEpic(1));
        assertEquals(subtask, manager.getSubtask(2));
        assertEquals(task, manager.getTask(3));
    }

    @Test
    void test08_getTasksWithInCorrectId() {
        manager.addEpic(epic);
        manager.addSubtask(subtask);
        manager.addTask(task);
        assertNull(manager.getTask(32));
        assertNull(manager.getEpic(312));
        assertNull(manager.getSubtask(44));
    }

    @Test
    void test09_getAllTasksTask() {
        List<Epic> listEpic = new ArrayList<>();
        listEpic.add(epic);
        List<Subtask> listSub = new ArrayList<>();
        listSub.add(subtask);
        List<Task> listTask = new ArrayList<>();
        listTask.add(task);
        manager.addEpic(epic);
        manager.addSubtask(subtask);
        manager.addTask(task);
        assertEquals(listEpic, manager.getAllEpic());
        assertEquals(listSub, manager.getAllSubtask());
        assertEquals(listTask, manager.getAllTask());
    }

    @Test
    void test10_getAllTasksWithOutTasks() {
        assertTrue(manager.getAllEpic().isEmpty());
        assertTrue(manager.getAllTask().isEmpty());
        assertTrue(manager.getAllSubtask().isEmpty());
    }

    @Test
    void test11_deleteTask() {
        manager.addTask(task);
        manager.deleteTask(1);
        assertTrue(manager.getAllTask().isEmpty());
    }

    @Test
    void test12_deleteTaskWithInCorrectId() {
        manager.addTask(task);
        manager.deleteTask(34);
        assertFalse(manager.getAllTask().isEmpty());
    }

    @Test
    void test13_deleteEpic() {
        manager.addEpic(epic);
        manager.deleteEpic(1);
        assertTrue(manager.getAllEpic().isEmpty());
    }

    @Test
    void test14_deleteEpicWithCorrectId() {
        manager.addEpic(epic);
        manager.deleteEpic(43);
        assertFalse(manager.getAllEpic().isEmpty());
    }

    @Test
    void test15_deleteSubtask() {
        manager.addEpic(epic);
        manager.addSubtask(subtask);
        manager.deleteSubtask(2);
        assertTrue(manager.getAllSubtask().isEmpty());
    }

    @Test
    void test16_deleteSubtaskWithCorrectId() {
        manager.addEpic(epic);
        manager.addSubtask(subtask);
        manager.deleteSubtask(233);
        assertFalse(manager.getAllSubtask().isEmpty());
    }

    @Test
    void test17_deleteEpic() {
        manager.addEpic(epic);
        manager.deleteEpic(1);
        assertTrue(manager.getAllEpic().isEmpty());
    }

    @Test
    void test18_deleteEpicWithCorrectId() {
        manager.addEpic(epic);
        manager.deleteEpic(434);
        assertFalse(manager.getAllEpic().isEmpty());
    }

    @Test
    void test19_deleteAllTasks() {
        manager.addEpic(epic);
        manager.addSubtask(subtask);
        manager.addTask(task);
        manager.deleteAllTask();
        manager.deleteAllEpic();
        manager.deleteAllSubtask();
        assertTrue(manager.getAllTask().isEmpty());
        assertTrue(manager.getAllEpic().isEmpty());
        assertTrue(manager.getAllSubtask().isEmpty());
    }

    @Test
    void test20_deleteEpicWithSubtask() {
        manager.addEpic(epic);
        manager.addSubtask(subtask);
        manager.deleteEpic(1);
        assertTrue(manager.getAllSubtask().isEmpty());
    }

    @Test
    void test21_updateTask() {
        manager.addTask(task);
        manager.updateTask(1, new Task("New Test", "Test", TaskStatus.NEW));
        assertNotEquals(task, manager.getTask(1));
    }

    @Test
    void test22_updateEpic() {
        manager.addEpic(epic);
        manager.updateEpic(1, new Epic("New Test", "Test"));
        assertNotEquals(epic, manager.getEpic(1));
    }

    @Test
    void test23_updateSubtask() {
        manager.addEpic(epic);
        manager.addSubtask(subtask);
        manager.updateSubtask(2, new Subtask("New Test", "Test", TaskStatus.NEW, 1));
        assertNotEquals(subtask, manager.getSubtask(3));
    }

    @Test
    void test24_updateTaskWithInCorrectId() {
        manager.addTask(task);
        manager.updateTask(4, new Task("New Test", "Test", TaskStatus.NEW));
        assertEquals(task, manager.getTask(1));
    }

    @Test
    void test25_updateEpicWithInCorrectId() {
        manager.addEpic(epic);
        final NullPointerException exception = assertThrows(NullPointerException.class,
                () -> {
                    manager.updateEpic(322, new Epic("New Test", "Test"));
                });
    }

    @Test
    void test26_updateSubtaskWithInCorrectId() {
        manager.addEpic(epic);
        manager.addSubtask(subtask);
        manager.updateSubtask(32, new Subtask("New Test", "Test", TaskStatus.NEW, 1));
        assertEquals(subtask, manager.getSubtask(2));
    }

    @Test
    void test27_getHistory() {
        manager.addEpic(epic);
        manager.addSubtask(subtask);
        manager.addTask(task);
        manager.getTask(3);
        manager.getEpic(1);
        manager.getSubtask(2);
        assertEquals(3, manager.history().size());
    }

    @Test
    void test28_getHistoryWithInCorrectedTasks() {
        manager.addEpic(epic);
        manager.addSubtask(subtask);
        manager.getEpic(1);
        manager.getSubtask(55);
        assertEquals(1, manager.history().size());
    }
}
