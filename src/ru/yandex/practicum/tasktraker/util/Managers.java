package ru.yandex.practicum.tasktraker.util;

import ru.yandex.practicum.tasktraker.controller.HttpTaskManager;
import ru.yandex.practicum.tasktraker.controller.InMemoryTaskManager;
import ru.yandex.practicum.tasktraker.controller.TaskManager;
import ru.yandex.practicum.tasktraker.historic.HistoryManager;
import ru.yandex.practicum.tasktraker.historic.InMemoryHistoryManager;

public class Managers {

    public static TaskManager getDefault() {
        return new HttpTaskManager("http://localhost:8078/");
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}
