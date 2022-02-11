package ru.yandex.practicum.tasktraker.util;

import ru.yandex.practicum.tasktraker.controller.InMemoryTaskManager;

public class Managers {

    public static InMemoryTaskManager getDefault() {
        return new InMemoryTaskManager();
    }
}
