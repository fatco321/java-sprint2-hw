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
    void test3_ShouldGetTasksWithIncorrectID() {
        assertNull(inMemoryTaskManager.getTask(66));
        assertNull(inMemoryTaskManager.getEpic(33));
        assertNull(inMemoryTaskManager.getSubtask(55));
    }

    @Test
    void test4_ShouldGetAllTask() {
        List<Task> testListTask = new ArrayList<>();
        inMemoryTaskManager.addTask(task);
        testListTask.add(task);
        task = new Task("Task_Test2","Test",TaskStatus.NEW);
        inMemoryTaskManager.addTask(task);
        testListTask.add(task);
        assertEquals(testListTask,inMemoryTaskManager.getAllTask());
    }

    @Test
    void test5_ShouldGetAllEpic(){
        List<Epic> testListEpic = new ArrayList<>();
        inMemoryTaskManager.addEpic(epic);
        testListEpic.add(epic);
        epic = new Epic("Epic_Test2","Test");
        inMemoryTaskManager.addEpic(epic);
        testListEpic.add(epic);
        assertEquals(testListEpic,inMemoryTaskManager.getAllEpic());
    }

    @Test
    void test6_ShouldGetAllSubtask(){
        List<Subtask> testListSubtask = new ArrayList<>();
        inMemoryTaskManager.addTask(task);
        inMemoryTaskManager.addEpic(epic);
        inMemoryTaskManager.addSubtask(subtask);
        testListSubtask.add(subtask);
        subtask = new Subtask("Subtask_Test2","Test",TaskStatus.NEW,2);
        inMemoryTaskManager.addSubtask(subtask);
        testListSubtask.add(subtask);
        assertEquals(testListSubtask,inMemoryTaskManager.getAllSubtask());
    }

    @Test
    void test7_ShouldGetAllTasksWithEmptyList(){
        assertTrue(inMemoryTaskManager.getAllTask().isEmpty());
        assertTrue(inMemoryTaskManager.getAllEpic().isEmpty());
        assertTrue(inMemoryTaskManager.getAllSubtask().isEmpty());
    }

    @Test
    void test8_ShouldDeleteTasks(){

    }
}
