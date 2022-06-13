package taskmanagers;

import com.google.gson.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.tasktraker.controller.HttpTaskManager;
import ru.yandex.practicum.tasktraker.controller.server.KVClient;
import ru.yandex.practicum.tasktraker.controller.server.KVServer;
import ru.yandex.practicum.tasktraker.controller.server.adapter.DurationAdapter;
import ru.yandex.practicum.tasktraker.controller.server.adapter.LocalDateTimeAdapter;
import ru.yandex.practicum.tasktraker.tasks.Epic;
import ru.yandex.practicum.tasktraker.tasks.Subtask;
import ru.yandex.practicum.tasktraker.tasks.Task;
import ru.yandex.practicum.tasktraker.tasks.TaskStatus;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class HttpTaskMangerTest {
    private HttpTaskManager httpTaskManager;

    @BeforeAll
    public static void beforeAllHTTPTaskManagerTests() {
        try {
            KVServer kvServer = new KVServer();
            kvServer.start();

            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(Duration.class, new DurationAdapter())
                    .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                    .setPrettyPrinting()
                    .create();

            KVClient kvClient = new KVClient("http://localhost:8078/");
            kvClient.setApiToken("DEBUG");
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @BeforeEach
    public void beforeEach() {
        httpTaskManager = new HttpTaskManager("http://localhost:8078/");
    }

    @Test
    public void test01_SaveAndLoadTasks() {
        Epic epic = new Epic("Epic_Test", "Test");
        Subtask subtask = new Subtask("Subtask_Test", "Test", TaskStatus.NEW, 1);
        Task task = new Task("Task_Test", "Test", TaskStatus.DONE);
        httpTaskManager.addEpic(epic);
        httpTaskManager.addSubtask(subtask);
        httpTaskManager.addTask(task);
        HttpTaskManager loadManager = new HttpTaskManager("http://localhost:8078/");
        loadManager.load("DEBUG");
        assertEquals(task, loadManager.getTask(3));
        assertEquals(epic, loadManager.getEpic(1));
        assertEquals(subtask, loadManager.getSubtask(2));
    }

    @Test
    public void test02_SaveAndLoadHistory() {
        Epic epic = new Epic("Epic_Test", "Test");
        Subtask subtask = new Subtask("Subtask_Test", "Test", TaskStatus.NEW, 1);
        Task task = new Task("Task_Test", "Test", TaskStatus.DONE);
        httpTaskManager.addEpic(epic);
        httpTaskManager.addSubtask(subtask);
        httpTaskManager.addTask(task);
        httpTaskManager.getTask(3);
        httpTaskManager.getSubtask(2);
        HttpTaskManager loadManager = new HttpTaskManager("http://localhost:8078/");
        loadManager.load("DEBUG");
        assertEquals(httpTaskManager.history().size(), loadManager.history().size());
    }
}
