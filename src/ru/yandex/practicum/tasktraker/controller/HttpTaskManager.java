package ru.yandex.practicum.tasktraker.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import ru.yandex.practicum.tasktraker.controller.server.KVClient;
import ru.yandex.practicum.tasktraker.controller.server.adapter.DurationAdapter;
import ru.yandex.practicum.tasktraker.controller.server.adapter.LocalDateTimeAdapter;
import ru.yandex.practicum.tasktraker.tasks.Epic;
import ru.yandex.practicum.tasktraker.tasks.Subtask;
import ru.yandex.practicum.tasktraker.tasks.Task;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

public class HttpTaskManager extends FileBackedTasksManager {
    private final KVClient client;
    private final Gson gson;

    public HttpTaskManager(String uri) {
        this.client = new KVClient(uri);
        this.gson = new GsonBuilder()
                .registerTypeAdapter(Duration.class, new DurationAdapter())
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .setPrettyPrinting()
                .create();
    }

    @Override
    protected void save() {
        String taskToGson = gson.toJson(getTaskHashMap());
        String epicToGson = gson.toJson(getEpicHashMap());
        String subtaskToGson = gson.toJson(getSubtaskHashMap());
        String historyToGson = gson.toJson(history());
        client.save("task", taskToGson);
        client.save("epic", epicToGson);
        client.save("subtask", subtaskToGson);
        client.save("history", historyToGson);
    }

    public void load(String apiToken) {
        client.setApiToken(apiToken);
        setTaskHashMap(gson.fromJson(client.load("task"), new TypeToken<HashMap<Integer, Task>>() {
        }.getType()));
        setEpicHashMap(gson.fromJson(client.load("epic"), new TypeToken<HashMap<Integer, Epic>>() {
        }.getType()));
        setSubtaskHashMap(gson.fromJson(client.load("subtask"), new TypeToken<HashMap<Integer, Subtask>>() {
        }.getType()));
        ArrayList<Task> loadHistoryList = new ArrayList<>(gson.fromJson(client.load("history"),
                new TypeToken<ArrayList<Task>>() {
                }.getType()));
        for (Task taskInHistory : loadHistoryList) {
            getHistoryManager().add(taskInHistory);
        }
    }
}
