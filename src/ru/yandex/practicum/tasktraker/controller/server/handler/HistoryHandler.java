package ru.yandex.practicum.tasktraker.controller.server.handler;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import ru.yandex.practicum.tasktraker.controller.TaskManager;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class HistoryHandler implements HttpHandler {
    private final TaskManager taskManager;
    private final Gson gson;

    public HistoryHandler(TaskManager taskManager, Gson gson) {
        this.taskManager = taskManager;
        this.gson = gson;
    }

    @Override
    public void handle(HttpExchange exchange) {
        String response = "";
        int code = 200;
        try {
            String method = exchange.getRequestMethod();
            if ("GET".equals(method)) {
                response = getRequest();
            }
        } catch (Exception e) {
            e.printStackTrace();
            code = 400;
            response = gson.toJson(e);
        } finally {
            getResponse(exchange, response, code);
        }
    }

    private String getRequest() {
        return gson.toJson(taskManager.history());
    }

    private void getResponse(HttpExchange exchange, String response, int code) {
        try (OutputStream outputStream = exchange.getResponseBody()) {
            exchange.sendResponseHeaders(code, 0);
            outputStream.write(response.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
