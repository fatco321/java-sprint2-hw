package ru.yandex.practicum.tasktraker.historic;

import ru.yandex.practicum.tasktraker.tasks.Task;

import java.util.List;

public interface HistoryManager {

    void add(Task task);
    void remove(int id);
    List<Task> getHistory();
}
