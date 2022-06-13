package ru.yandex.practicum.tasktraker.controller.server.handler;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import ru.yandex.practicum.tasktraker.controller.TaskManager;
import ru.yandex.practicum.tasktraker.controller.exception.HttpException;
import ru.yandex.practicum.tasktraker.controller.server.handler.support.TaskHandlerSupport;
import ru.yandex.practicum.tasktraker.tasks.Subtask;
import ru.yandex.practicum.tasktraker.tasks.Task;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class SubtaskHandler implements HttpHandler {
    private final TaskManager taskManager;
    private final Gson gson;

    public SubtaskHandler(TaskManager taskManager, Gson gson) {
        this.taskManager = taskManager;
        this.gson = gson;
    }

    @Override
    public void handle(HttpExchange exchange){
        String response = "";
        int code = 200;
        try {
            String method = exchange.getRequestMethod();
            switch (method) {
                case "GET" -> response = getResponse(exchange);
                case "POST" -> postSubtaskRequest(exchange);
                case "DELETE" -> deleteSubtaskRequest(exchange);
            }

        } catch (Exception e) {
            e.printStackTrace();
            code = 400;
            response = gson.toJson(e);
        } finally {
            getResponse(exchange, response, code);
        }
    }

    private void getResponse(HttpExchange exchange, String response, int code) {
        try (OutputStream outputStream = exchange.getResponseBody()) {
            exchange.sendResponseHeaders(code, 0);
            outputStream.write(response.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getResponse(HttpExchange exchange) {
        URI uri = exchange.getRequestURI();
        String query = exchange.getRequestURI().getQuery();
        int id;
        String response = "";
        if (query == null) {
            return gson.toJson(taskManager.getAllSubtask());
        }
        id = TaskHandlerSupport.getIdFromQuery(uri);
        if (id != 0) {
            Task task = taskManager.getSubtask(id);
            response = gson.toJson(task);
        }
        return response;
    }


    private void deleteSubtaskRequest(HttpExchange exchange) {
        URI uri = exchange.getRequestURI();
        int id = TaskHandlerSupport.getIdFromQuery(uri);
        if (id > 0) {
            taskManager.deleteSubtask(id);
        } else {
            taskManager.deleteAllSubtask();
        }
    }

    private void postSubtaskRequest(HttpExchange exchange) throws HttpException, IOException {
        List<String> refresh = exchange.getRequestHeaders().get("refresh");
        InputStream inputStream = exchange.getRequestBody();
        String body = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        Subtask subtask = (Subtask) TaskHandlerSupport.parseJsonToTask(body, gson);
        if (refresh == null) {
            throw new NullPointerException("Error Change Task");
        }
        if (refresh.contains("create")) {
            taskManager.addSubtask(subtask);
        } else if (refresh.contains("update")) {
            taskManager.updateSubtask(subtask.getId(), subtask);
        }
    }
}