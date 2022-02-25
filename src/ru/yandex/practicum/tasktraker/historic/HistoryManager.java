package ru.yandex.practicum.tasktraker.historic;

import ru.yandex.practicum.tasktraker.tasks.Task;

import java.util.List;

public interface HistoryManager {
    //добавление задачи в историю
    void add(Task task);

    //удаление задачи из истории
    void remove(int id);

    //получение листа историй
    List<Task> getHistory();
}
