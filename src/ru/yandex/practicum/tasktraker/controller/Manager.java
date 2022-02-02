package ru.yandex.practicum.tasktraker.controller;

import ru.yandex.practicum.tasktraker.tasks.*;

import java.util.ArrayList;
import java.util.HashMap;

public class Manager {
    private HashMap<Integer, Task> taskHashMap = new HashMap<>();
    private HashMap<Integer, Epic> epicHashMap = new HashMap<>();
    private int id = 0;

    void setId() {
        id++;
    }

    public void addTask(String name, String description) {
        Task task = new Task(name, description);
        setId();
        taskHashMap.put(id, task);
    }

    public Task getTask(int id) {
        return taskHashMap.get(id);
    }

    public ArrayList getAllTask() {
        ArrayList<Task> taskList = new ArrayList<>();
        for (int key : taskHashMap.keySet()) {
            taskList.add(taskHashMap.get(key));
        }
        return taskList;
    }

    public void updateTask(int id, String name, String description) {
        Task task = taskHashMap.get(id);
        if (task != null) {
            task.setTaskName(name);
            task.setTaskDescription(description);
            taskHashMap.put(id, task);
        }
    }

    public void changeTaskStatus(int id) {
        Task task = taskHashMap.get(id);
        if (task != null)
            task.setStatus();
    }

    public void deleteTask(int id) {
        taskHashMap.remove(id);
    }

    public void deleteAllTask() {
        taskHashMap.clear();
    }

    public void addEpic(String name, String description) {
        Epic epic = new Epic(name, description);
        setId();
        epicHashMap.put(id, epic);
    }

    public Epic getEpic(int id) {
        return epicHashMap.get(id);
    }

    public ArrayList getAllEpic() {
        ArrayList<Epic> epicList = new ArrayList<>();
        for (int key : epicHashMap.keySet()) {
            epicList.add(epicHashMap.get(key));
        }
        return epicList;
    }

    public void updateEpic(int id, String name, String description) {
        Epic epic = epicHashMap.get(id);
        if (epic != null) {
            epic.setTaskName(name);
            epic.setTaskDescription(description);
            epicHashMap.put(id, epic);
        }
    }

    public void deleteEpic(int id) {
        epicHashMap.remove(id);
    }

    public void deleteAllEpic() {
        epicHashMap.clear();
    }

    public void addSubtask(int id, String name, String description) {
        Epic epic = epicHashMap.get(id);
        if (epic != null) {
            Subtask subtask = new Subtask(name, description);
            subtask.setEpic(epic);
            epic.setSublist(subtask);
        }
    }

    public void updateSubtask(int id, int index, String name, String description) {
        Epic epic = epicHashMap.get(id);
        if (epic != null) {
            if (index < epic.getSublist().size()) {
                Subtask subtask = new Subtask(name, description);
                subtask.setEpic(epic);
                epic.getSublist().remove(index);
                epic.getSublist().add(index, subtask);
                epic.setStatus();
            }
        }
    }

    public ArrayList getSubtask(int id) {
        Epic epic = epicHashMap.get(id);
        return epic.getSublist();
    }

    public ArrayList getAllSubtask() {
        ArrayList<Subtask> allSubList = new ArrayList<>();
        for (Epic epic : epicHashMap.values()) {
            for (Subtask subtask : epic.getSublist()) {
                allSubList.add(subtask);
            }
        }
        return allSubList;
    }

    public void changeSubStatus(int id, int index) {
        Epic epic = epicHashMap.get(id);
        if (epic != null) {
            ArrayList<Subtask> subList = epic.getSublist();
            if (subList != null) {
                if (index < subList.size()) {
                    Subtask subtask = subList.get(index);
                    subtask.setStatus();
                    epic.setStatus();
                }
            }
        }
    }

    public void deleteSubtask(int id, int index) {
        Epic epic = epicHashMap.get(id);
        if (epic != null) {
            if (index < epic.getSublist().size())
                epic.getSublist().remove(index);
            epic.setStatus();
        }
    }

    public void deleteAllSubtask(int id) {
        Epic epic = epicHashMap.get(id);
        if (epic != null) {
            epic.getSublist().clear();
            epic.setStatus();
        }
    }

}