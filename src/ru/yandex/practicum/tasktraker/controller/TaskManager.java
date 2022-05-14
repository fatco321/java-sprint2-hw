package ru.yandex.practicum.tasktraker.controller;

import ru.yandex.practicum.tasktraker.tasks.*;

import java.util.List;

public interface TaskManager {

    //увеличение ID при добавлении задачи
    void setId();

    //добавление Задачи
    void addTask(Task task);

    //получение Задачи
    Task getTask(int taskId);

    //получения списка всех Задач
    List<Task> getAllTask();

    //обновление(изменение) Задачи
    void updateTask(int taskId, Task taskNew);

    //удаление Задачи
    void deleteTask(int taskId);

    //удаление всех Задач
    void deleteAllTask();

    //добавление Эпической задачи
    void addEpic(Epic epic);

    //получение Эпической задачи
    Epic getEpic(int epicId);

    //получение списка всех Эпических задач
    List<Epic> getAllEpic();

    //обновление(изменение) Эпической задачи
    void updateEpic(int epicId, Epic epicNew);

    //удаление Эпической задачи
    void deleteEpic(int epicId);

    //удаление всех Эпических задач
    void deleteAllEpic();

    //добавление Подзадачи
    void addSubtask(Subtask subtask);

    //получение Подзадачи
    Subtask getSubtask(int sabtaskId);

    //обновление(изменение) Подзадачи
    void updateSubtask(int subtaskId, Subtask subtasNew);

    //получение списка всех Подзадач конкретной Эпической задачи
    List<Subtask> getSubtasksByEpicId(int epicId);

    //получение списка всех Подзадач
    List<Subtask> getAllSubtask();

    //удаление Подзадачи
    void deleteSubtask(int subtaskId);

    //удаление всех Подзадач
    void deleteAllSubtask();

    //получение списка истории
    List<Task> history();
}
