package ru.yandex.practicum.tasktraker.controller;

import ru.yandex.practicum.tasktraker.tasks.*;

import java.util.ArrayList;
import java.util.HashMap;

public class Manager {
    private HashMap<Integer, Task> taskHashMap = new HashMap<>();
    private HashMap<Integer, Epic> epicHashMap = new HashMap<>();
    private HashMap<Integer, Subtask> subtaskHashMap = new HashMap<>();
    private int id = 0;

    private void setId() {
        id++;
    }

    public void addTask(Task task) {
        setId();
        taskHashMap.put(id, task);
    }

    public Task getTask(int taskId) {
        return taskHashMap.get(taskId);
    }

    public ArrayList<Task> getAllTask() {
        ArrayList<Task> taskList = new ArrayList<>();
        for (int key : taskHashMap.keySet()) {
            taskList.add(taskHashMap.get(key));
        }
        return taskList;
    }

    public void updateTask(int taskId, Task taskNew) {
        taskHashMap.put(taskId, taskNew);
    }

    private void changeTaskStatus(int taskId) {
        Task task = taskHashMap.get(taskId);
        if (task != null)
            task.setStatus();
    }

    public void deleteTask(int taskId) {
        taskHashMap.remove(taskId);
    }

    public void deleteAllTask() {
        taskHashMap.clear();
    }

    public void addEpic(Epic epic) {
        setId();
        epicHashMap.put(id, epic);
    }

    public Epic getEpic(int epicId) {
        return epicHashMap.get(epicId);
    }

    public ArrayList<Epic> getAllEpic() {
        ArrayList<Epic> epicList = new ArrayList<>();
        for (int key : epicHashMap.keySet()) {
            epicList.add(epicHashMap.get(key));
        }
        return epicList;
    }

    public void updateEpic(int epicId, Epic epicNew) {
        Epic epic = epicHashMap.get(epicId);
        if (epic != null) {
            epicNew.setSublist(epic.getSublist());
            epicNew.setStatus();
            epicHashMap.put(epicId, epicNew);
        }
    }

    public void deleteEpic(int epicId) {
        Epic epic = epicHashMap.get(epicId);
        ArrayList<Integer> keyList = new ArrayList<>();
        for (Subtask subtask : epic.getSublist()) {
            for (int key : subtaskHashMap.keySet()) {
                if (subtask.equals(subtaskHashMap.get(key))) {
                    keyList.add(key);
                }
            }
        }
        for (int key : keyList) {
            subtaskHashMap.remove(key);
        }
        epicHashMap.remove(epicId);
    }

    public void deleteAllEpic() {
        subtaskHashMap.clear();
        epicHashMap.clear();
    }

    public void addSubtask(int epicId, Subtask subtask) {
        Epic epic = epicHashMap.get(epicId);
        if (epic != null) {
            setId();
            subtask.setEpicId(epicId);
            epic.addSubtaskInList(subtask);
            subtaskHashMap.put(id, subtask);
        }
    }

    public Subtask getSubtask(int sabtaskId) {
        return subtaskHashMap.get(sabtaskId);
    }

    public void updateSubtask(int subtaskId, Subtask subtasNew) {
        Subtask subtask = subtaskHashMap.get(subtaskId);
        if (subtask != null) {
            Epic epic = epicHashMap.get(subtask.getEpicId());
            int index = epic.getSublist().indexOf(subtask);
            epic.getSublist().set(index, subtasNew);
            subtaskHashMap.put(subtaskId, subtasNew);
            epic.setStatus();
        }
    }

    public ArrayList<Subtask> getSubtasksByEpicId(int epicId) {
        Epic epic = epicHashMap.get(epicId);
        return epic.getSublist();
    }

    public ArrayList<Subtask> getAllSubtask() {
        ArrayList<Subtask> allSubList = new ArrayList<>();
        for (Epic epic : epicHashMap.values()) {
            for (Subtask subtask : epic.getSublist()) {
                allSubList.add(subtask);
            }
        }
        return allSubList;
    }

    private void changeSubtaskStatus(int subtaskId) {
        Subtask subtask = subtaskHashMap.get(subtaskId);
        Epic epic = epicHashMap.get(subtask.getEpicId());
        if (subtask != null) {
            if (epic != null) {
                subtask.setStatus();
                int index = epic.getSublist().indexOf(subtask);
                epic.getSublist().set(index, subtask);
                epic.setStatus();
            }
        }
    }

    public void deleteSubtask(int subtaskId) {
        Subtask subtask = subtaskHashMap.get(subtaskId);
        if (subtask != null) {
            Epic epic = epicHashMap.get(subtask.getEpicId());
            if (epic != null) {
                epic.getSublist().remove(subtask);
                subtaskHashMap.remove(subtaskId);
                epic.setStatus();
            }
        }
    }

    public void deleteAllSubtask() {
        for (Epic epic : epicHashMap.values()) {
            epic.getSublist().clear();
            epic.setStatus();
        }
        subtaskHashMap.clear();
    }

}