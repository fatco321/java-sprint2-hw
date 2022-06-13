package ru.yandex.practicum.tasktraker.controller.server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpServer;
import ru.yandex.practicum.tasktraker.controller.TaskManager;
import ru.yandex.practicum.tasktraker.controller.server.adapter.DurationAdapter;
import ru.yandex.practicum.tasktraker.controller.server.adapter.LocalDateTimeAdapter;
import ru.yandex.practicum.tasktraker.controller.server.handler.EpicHandler;
import ru.yandex.practicum.tasktraker.controller.server.handler.HistoryHandler;
import ru.yandex.practicum.tasktraker.controller.server.handler.SubtaskHandler;
import ru.yandex.practicum.tasktraker.controller.server.handler.TaskHandler;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.time.Duration;
import java.time.LocalDateTime;

public class HttpTaskServer {
    private TaskManager taskManager;
    private Gson gson;
    private HttpServer httpServer;
    private int PORT = 8080;

    public HttpTaskServer(TaskManager manager) throws IOException {
        this.taskManager = manager;
        this.gson = new GsonBuilder()
                .registerTypeAdapter(Duration.class, new DurationAdapter())
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .setPrettyPrinting()
                .create();
        httpServer = HttpServer.create(new InetSocketAddress(PORT), 0);
        httpServer.createContext("/tasks/task", new TaskHandler(taskManager, gson));
        httpServer.createContext("/tasks/history", new HistoryHandler(taskManager, gson));
        httpServer.createContext("/tasks/epic", new EpicHandler(taskManager, gson));
        httpServer.createContext("/tasks/subtask", new SubtaskHandler(taskManager, gson));
    }

    public void start() {
        httpServer.start();
    }

    public void stop() {
        httpServer.stop(1);
    }
}
