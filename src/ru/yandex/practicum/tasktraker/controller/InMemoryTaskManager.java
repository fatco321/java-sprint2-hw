package ru.yandex.practicum.tasktraker.controller;

import ru.yandex.practicum.tasktraker.tasks.*;
import ru.yandex.practicum.tasktraker.historic.*;
import ru.yandex.practicum.tasktraker.util.Managers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryTaskManager implements TaskManager {
    private final HistoryManager inMemoryHistoryManager = Managers.getDefaultHistory();
    private final HashMap<Integer, Task> taskHashMap = new HashMap<>();
    private final HashMap<Integer, Epic> epicHashMap = new HashMap<>();
    private final HashMap<Integer, Subtask> subtaskHashMap = new HashMap<>();
    private int id = 0;

    @Override
    public void setId() {
        id++;
    }

    @Override
    public void addTask(Task task) {
        setId();
        task.setId(id);
        taskHashMap.put(id, task);
    }

    @Override
    public Task getTask(int taskId) {
        inMemoryHistoryManager.add(taskHashMap.get(taskId));
        return taskHashMap.get(taskId);
    }

    @Override
    public ArrayList<Task> getAllTask() {
        return new ArrayList<>(taskHashMap.values());
    }

    @Override
    public void updateTask(int taskId, Task taskNew) {
        taskHashMap.put(taskId, taskNew);
    }

    @Override
    public void deleteTask(int taskId) {
        taskHashMap.remove(taskId);
    }

    @Override
    public void deleteAllTask() {
        taskHashMap.clear();
    }

    @Override
    public void addEpic(Epic epic) {
        setId();
        epic.setId(id);
        epicHashMap.put(id, epic);
    }

    @Override
    public Epic getEpic(int epicId) {
        inMemoryHistoryManager.add(epicHashMap.get(epicId));
        return epicHashMap.get(epicId);
    }

    @Override
    public ArrayList<Epic> getAllEpic() {
        return new ArrayList<>(epicHashMap.values());
    }

    @Override
    public void updateEpic(int epicId, Epic epicNew) {
        Epic epic = epicHashMap.get(epicId);
        if (epic != null) {
            epicNew.setSublist(epic.getSublist());
            epicNew.setStatus();
            epicHashMap.put(epicId, epicNew);
        }
    }

    @Override
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

    @Override
    public void deleteAllEpic() {
        subtaskHashMap.clear();
        epicHashMap.clear();
    }

    @Override
    public void addSubtask(Subtask subtask) {
        putSubtaskInEpic(subtask);
        setId();
        subtask.setId(id);
        subtaskHashMap.put(id, subtask);

    }

    private void putSubtaskInEpic(Subtask subtask) {
        if (subtask != null) {
            Epic epic = epicHashMap.get(subtask.getEpicId());
            if (epic != null) {
                epic.addSubtaskInList(subtask);
                epic.setStatus();
            }
        }
    }

    @Override
    public Subtask getSubtask(int sabtaskId) {
        inMemoryHistoryManager.add(subtaskHashMap.get(sabtaskId));
        return subtaskHashMap.get(sabtaskId);
    }

    @Override
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

    @Override
    public ArrayList<Subtask> getSubtasksByEpicId(int epicId) {
        Epic epic = epicHashMap.get(epicId);
        return epic.getSublist();
    }

    @Override
    public ArrayList<Subtask> getAllSubtask() {
        return new ArrayList<>(subtaskHashMap.values());
    }

    @Override
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

    @Override
    public void deleteAllSubtask() {
        for (Epic epic : epicHashMap.values()) {
            epic.getSublist().clear();
            epic.setStatus();
        }
        subtaskHashMap.clear();
    }

    @Override
    public List<Task> history() {
        return inMemoryHistoryManager.getHistory();
    }


    public  void remove(int id){
        inMemoryHistoryManager.remove(id);
    }

}