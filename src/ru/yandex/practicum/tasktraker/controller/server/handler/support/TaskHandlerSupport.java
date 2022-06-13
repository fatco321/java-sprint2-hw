package ru.yandex.practicum.tasktraker.controller.server.handler.support;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import ru.yandex.practicum.tasktraker.controller.exception.HttpException;
import ru.yandex.practicum.tasktraker.tasks.Epic;
import ru.yandex.practicum.tasktraker.tasks.Subtask;
import ru.yandex.practicum.tasktraker.tasks.Task;

import java.net.URI;

public class TaskHandlerSupport {
    public static Task parseJsonToTask(String body, Gson gson) throws HttpException {
        JsonElement jsonElement = JsonParser.parseString(body);
        if (!jsonElement.isJsonObject()) {
            throw new HttpException("error");
        }
        String taskType = jsonElement.getAsJsonObject().get("type").getAsString();
        return switch (taskType) {
            case "TASK" -> gson.fromJson(body, Task.class);
            case "EPIC" -> gson.fromJson(body, Epic.class);
            case "SUBTASK" -> gson.fromJson(body, Subtask.class);
            default -> throw new HttpException("Ошибка");
        };
    }

    public static Integer getIdFromQuery(URI uri) {
        if (uri.getQuery() != null) {
            String[] split = uri.getQuery().split("&");
            for (String s1 : split) {
                String name = s1.split("=")[0];
                String value = s1.split("=")[1];
                if (name.equals("id")) {
                    return Integer.parseInt(value);
                }
            }
        }
        return -1;
    }
}
