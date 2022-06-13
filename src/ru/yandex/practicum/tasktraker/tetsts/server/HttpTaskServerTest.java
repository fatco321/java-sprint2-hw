package server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.tasktraker.controller.InMemoryTaskManager;
import ru.yandex.practicum.tasktraker.controller.server.HttpTaskServer;
import ru.yandex.practicum.tasktraker.controller.server.adapter.DurationAdapter;
import ru.yandex.practicum.tasktraker.controller.server.adapter.LocalDateTimeAdapter;
import ru.yandex.practicum.tasktraker.tasks.Epic;
import ru.yandex.practicum.tasktraker.tasks.Subtask;
import ru.yandex.practicum.tasktraker.tasks.Task;
import ru.yandex.practicum.tasktraker.tasks.TaskStatus;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class HttpTaskServerTest {
    private static Gson gson;
    private static Task task;
    private static Epic epic;
    private static Subtask subtask;
    private static HttpClient client;
    private static String url;

    @BeforeAll
    public static void beforeAll() throws IOException {
        gson = new GsonBuilder()
                .registerTypeAdapter(Duration.class, new DurationAdapter())
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .setPrettyPrinting()
                .create();
        url = "http://localhost:8080/";
        epic = new Epic("Epic_TEST", "Test");
        subtask = new Subtask("Subtask_TEST", "Test", TaskStatus.NEW, 1);
        task = new Task("Task_Test", "Test", TaskStatus.DONE);
        InMemoryTaskManager manager = new InMemoryTaskManager();
        manager.addEpic(epic);
        manager.addSubtask(subtask);
        manager.addTask(task);
        client = HttpClient.newHttpClient();
        HttpTaskServer server = new HttpTaskServer(manager);
        server.start();
    }

    @Test
    public void test01_getTaskFromServer() throws IOException, InterruptedException {
        URI uri = URI.create(url + "tasks/task/?id=3");
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(uri)
                .version(HttpClient.Version.HTTP_1_1)
                .build();
        HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
        HttpResponse<String> response = client.send(request, handler);
        Task serverTask = gson.fromJson(response.body(), Task.class);
        assertEquals(task, serverTask);
    }

    @Test
    public void test02_getEpicFromServer() throws IOException, InterruptedException {
        URI uri = URI.create(url + "tasks/epic/?id=1");
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(uri)
                .version(HttpClient.Version.HTTP_1_1)
                .build();
        HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
        HttpResponse<String> response = client.send(request, handler);
        Epic serverEpic = gson.fromJson(response.body(), Epic.class);
        assertEquals(epic, serverEpic);
    }

    @Test
    public void test03_getSubtaskFromServer() throws IOException, InterruptedException {
        URI uri = URI.create(url + "tasks/subtask/?id=2");
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(uri)
                .version(HttpClient.Version.HTTP_1_1)
                .build();
        HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
        HttpResponse<String> response = client.send(request, handler);
        Subtask serverSubtask = gson.fromJson(response.body(), Subtask.class);
        assertEquals(subtask, serverSubtask);
    }

    @Test
    public void test04_deleteTask() throws IOException, InterruptedException {
        URI uri = URI.create(url + "tasks/task");
        HttpRequest request = HttpRequest.newBuilder()
                .DELETE()
                .uri(uri)
                .version(HttpClient.Version.HTTP_1_1)
                .build();
        HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
        HttpResponse<String> response = client.send(request, handler);
        uri = URI.create(url + "tasks/task/?id=3");
        request = HttpRequest.newBuilder()
                .GET()
                .uri(uri)
                .version(HttpClient.Version.HTTP_1_1)
                .build();
        handler = HttpResponse.BodyHandlers.ofString();
        response = client.send(request, handler);
        Task serverTask = gson.fromJson(response.body(), Task.class);
        assertNull(serverTask);
    }

    @Test
    public void test05_deleteEpic() throws IOException, InterruptedException {
        URI uri = URI.create(url + "tasks/epic");
        HttpRequest request = HttpRequest.newBuilder()
                .DELETE()
                .uri(uri)
                .version(HttpClient.Version.HTTP_1_1)
                .build();
        HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
        HttpResponse<String> response = client.send(request, handler);
        uri = URI.create(url + "tasks/epic/?id=1");
        request = HttpRequest.newBuilder()
                .GET()
                .uri(uri)
                .version(HttpClient.Version.HTTP_1_1)
                .build();
        handler = HttpResponse.BodyHandlers.ofString();
        response = client.send(request, handler);
        Epic serverEpic = gson.fromJson(response.body(), Epic.class);
        assertNull(serverEpic);
    }

    @Test
    public void test06_deleteSubtask() throws IOException, InterruptedException {
        URI uri = URI.create(url + "tasks/subtask");
        HttpRequest request = HttpRequest.newBuilder()
                .DELETE()
                .uri(uri)
                .version(HttpClient.Version.HTTP_1_1)
                .build();
        HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
        HttpResponse<String> response = client.send(request, handler);
        uri = URI.create(url + "tasks/subtask/?id=2");
        request = HttpRequest.newBuilder()
                .GET()
                .uri(uri)
                .version(HttpClient.Version.HTTP_1_1)
                .build();
        handler = HttpResponse.BodyHandlers.ofString();
        response = client.send(request, handler);
        Subtask serverSubtask = gson.fromJson(response.body(), Subtask.class);
        assertNull(serverSubtask);
    }

    @Test
    public void test07_addNewTask() throws IOException, InterruptedException {
        Task newTask = new Task("New Task", "Test", TaskStatus.NEW);
        newTask.setId(4);
        URI uri = URI.create(url + "tasks/task");
        final HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(gson.toJson(newTask));
        HttpRequest request = HttpRequest
                .newBuilder()
                .uri(uri)
                .header("refresh", "create")
                .POST(body)
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        uri = URI.create(url + "tasks/task/?id=4");
        request = HttpRequest.newBuilder()
                .GET()
                .uri(uri)
                .version(HttpClient.Version.HTTP_1_1)
                .build();
        HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
        response = client.send(request, handler);
        Task serverTask = gson.fromJson(response.body(), Task.class);
        assertEquals(newTask, serverTask);
    }

    @Test
    public void test08_addNewEpic() throws IOException, InterruptedException {
        Epic newEpic = new Epic("New Epic", "Test");
        newEpic.setId(4);
        URI uri = URI.create(url + "tasks/epic");
        final HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(gson.toJson(newEpic));
        HttpRequest request = HttpRequest
                .newBuilder()
                .uri(uri)
                .header("refresh", "create")
                .POST(body)
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        uri = URI.create(url + "tasks/epic/?id=4");
        request = HttpRequest.newBuilder()
                .GET()
                .uri(uri)
                .version(HttpClient.Version.HTTP_1_1)
                .build();
        HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
        response = client.send(request, handler);
        Task serverEpic = gson.fromJson(response.body(), Epic.class);
        assertEquals(newEpic, serverEpic);
    }

    @Test
    public void test09_addNewSubtask() throws IOException, InterruptedException {
        Subtask newSubtask = new Subtask("New Subtask", "Test", TaskStatus.NEW, 1);
        newSubtask.setId(4);
        URI uri = URI.create(url + "tasks/subtask");
        final HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(gson.toJson(newSubtask));
        HttpRequest request = HttpRequest
                .newBuilder()
                .uri(uri)
                .header("refresh", "create")
                .POST(body)
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        uri = URI.create(url + "tasks/subtask/?id=4");
        request = HttpRequest.newBuilder()
                .GET()
                .uri(uri)
                .version(HttpClient.Version.HTTP_1_1)
                .build();
        HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
        response = client.send(request, handler);
        Task serverSubtask = gson.fromJson(response.body(), Subtask.class);
        assertEquals(newSubtask, serverSubtask);
    }
}
