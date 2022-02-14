package ru.yandex.practicum.tasktraker.controller;

import ru.yandex.practicum.tasktraker.tasks.*;

import java.util.ArrayList;
import java.util.List;

public interface TaskManager {
    void setId();

    void addTask(Task task);

    Task getTask(int taskId);

    ArrayList<Task> getAllTask();

    void updateTask(int taskId, Task taskNew);

    void deleteTask(int taskId);

    void deleteAllTask();

    void addEpic(Epic epic);

    Epic getEpic(int epicId);

    ArrayList<Epic> getAllEpic();

    void updateEpic(int epicId, Epic epicNew);

    void deleteEpic(int epicId);

    void deleteAllEpic();

    void addSubtask(Subtask subtask);

    Subtask getSubtask(int sabtaskId);

    void updateSubtask(int subtaskId, Subtask subtasNew);

    ArrayList<Subtask> getSubtasksByEpicId(int epicId);

    ArrayList<Subtask> getAllSubtask();

    void deleteSubtask(int subtaskId);

    void deleteAllSubtask();

    List<Task> history();
}
