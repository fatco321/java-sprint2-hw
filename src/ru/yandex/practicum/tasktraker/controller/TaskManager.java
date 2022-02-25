package ru.yandex.practicum.tasktraker.controller;

import ru.yandex.practicum.tasktraker.tasks.*;

import java.util.ArrayList;
import java.util.List;

public interface TaskManager {

    //увелечение ID при добавлении задачи
    void setId();

    //добавление Задачи
    void addTask(Task task);

    //получение Задачи
    Task getTask(int taskId);

    //получения листа всех Задач
    List<Task> getAllTask();

    //обновление(изменение) Задачи
    void updateTask(int taskId, Task taskNew);

    //удаление Задачи
    void deleteTask(int taskId);

    //удаление всех Задач
    void deleteAllTask();

    //доавбление Эпической задачи
    void addEpic(Epic epic);

    //получение Эпической задачи
    Epic getEpic(int epicId);

    //получение листа всех Эпических задач
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

    //получение листа всех Подзадач конкретной Эпической задачи
    List<Subtask> getSubtasksByEpicId(int epicId);

    //получение листа всех Подзадач
    List<Subtask> getAllSubtask();

    //удаление Подзадачи
    void deleteSubtask(int subtaskId);

    //удаление всех Подзадач
    void deleteAllSubtask();

    //получение листа истории
    List<Task> history();
}
