package ru.yandex.practicum.tasktraker.controller;

import ru.yandex.practicum.tasktraker.tasks.*;
import ru.yandex.practicum.tasktraker.historic.*;
import ru.yandex.practicum.tasktraker.util.Managers;

import java.util.*;

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
        if (task == null)
            return;
        setId();
        task.setId(id);
        taskHashMap.put(id, task);
    }

    @Override
    public Task getTask(int taskId) {
        if (taskHashMap.containsKey(taskId))
            inMemoryHistoryManager.add(taskHashMap.get(taskId));
        return taskHashMap.get(taskId);
    }

    @Override
    public List<Task> getAllTask() {
        return new ArrayList<>(taskHashMap.values());
    }

    @Override
    public void updateTask(int taskId, Task taskNew) {
        taskNew.setId(taskId);
        taskHashMap.put(taskId, taskNew);
    }

    @Override
    public void deleteTask(int taskId) {
        if (taskHashMap.containsKey(taskId)) {
            taskHashMap.remove(taskId);
            inMemoryHistoryManager.remove(taskId);
        }
    }

    @Override
    public void deleteAllTask() {
        for (int taskId : taskHashMap.keySet()) {
            inMemoryHistoryManager.remove(taskId);
        }
        taskHashMap.clear();
    }

    @Override
    public void addEpic(Epic epic) {
        if (epic == null)
            return;
        setId();
        epic.setId(id);
        epicHashMap.put(id, epic);
    }

    @Override
    public Epic getEpic(int epicId) {
        if (epicHashMap.containsKey(epicId))
            inMemoryHistoryManager.add(epicHashMap.get(epicId));
        return epicHashMap.get(epicId);
    }

    @Override
    public List<Epic> getAllEpic() {
        return new ArrayList<>(epicHashMap.values());
    }

    @Override
    public void updateEpic(int epicId, Epic epicNew) {
        if (!epicHashMap.containsKey(epicId) && epicNew == null)
            return;
        Epic epic = epicHashMap.get(epicId);
        epicNew.setId(epicId);
        epicNew.setSublist(epic.getSublist());
        epicNew.setStatus();
        epicHashMap.put(epicId, epicNew);
    }

    @Override
    public void deleteEpic(int epicId) {
        if (!epicHashMap.containsKey(epicId))
            return;
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
            inMemoryHistoryManager.remove(key);
        }
        inMemoryHistoryManager.remove(epicId);
        epicHashMap.remove(epicId);
    }

    @Override
    public void deleteAllEpic() {
        for (int epicId : epicHashMap.keySet()) {
            for (int subId : subtaskHashMap.keySet()) {
                inMemoryHistoryManager.remove(subId);
            }
            inMemoryHistoryManager.remove(epicId);
        }
        subtaskHashMap.clear();
        epicHashMap.clear();
    }

    @Override
    public void addSubtask(Subtask subtask) {
        if (subtask == null)
            return;
        if (!epicHashMap.containsKey(subtask.getEpicId()))
            return;
        putSubtaskInEpic(subtask);
        setId();
        subtask.setId(id);
        subtaskHashMap.put(id, subtask);
    }

    private void putSubtaskInEpic(Subtask subtask) {
        Epic epic = epicHashMap.get(subtask.getEpicId());
        epic.addSubtaskInList(subtask);
        epic.setStatus();
    }

    @Override
    public Subtask getSubtask(int sabtaskId) {
        if (subtaskHashMap.containsKey(sabtaskId))
            inMemoryHistoryManager.add(subtaskHashMap.get(sabtaskId));
        return subtaskHashMap.get(sabtaskId);
    }

    @Override
    public void updateSubtask(int subtaskId, Subtask subtasNew) {
        if (subtaskHashMap.containsKey(subtaskId) && subtasNew == null)
            return;
        Subtask subtask = subtaskHashMap.get(subtaskId);
        if (subtask != null) {
            subtasNew.setId(subtaskId);
            Epic epic = epicHashMap.get(subtask.getEpicId());
            int index = epic.getSublist().indexOf(subtask);
            epic.getSublist().set(index, subtasNew);
            subtaskHashMap.put(subtaskId, subtasNew);
            epic.setStatus();
        }
    }

    @Override
    public List<Subtask> getSubtasksByEpicId(int epicId) {
        if (epicHashMap.containsKey(epicId)) {
            Epic epic = epicHashMap.get(epicId);
            return epic.getSublist();
        } else {
            return null;
        }
    }

    @Override
    public List<Subtask> getAllSubtask() {
        return new ArrayList<>(subtaskHashMap.values());
    }

    @Override
    public void deleteSubtask(int subtaskId) {
        if (subtaskHashMap.containsKey(subtaskId)) {
            Subtask subtask = subtaskHashMap.get(subtaskId);
            Epic epic = epicHashMap.get(subtask.getEpicId());
            epic.getSublist().remove(subtask);
            subtaskHashMap.remove(subtaskId);
            inMemoryHistoryManager.remove(subtaskId);
            epic.setStatus();
        }
    }

    @Override
    public void deleteAllSubtask() {
        for (Epic epic : epicHashMap.values()) {
            epic.getSublist().clear();
            epic.setStatus();
        }
        for (int subId : subtaskHashMap.keySet()) {
            inMemoryHistoryManager.remove(subId);
        }
        subtaskHashMap.clear();
    }

    @Override
    public List<Task> history() {
        return inMemoryHistoryManager.getHistory();
    }

    public HistoryManager getInMemoryHistoryManager() {
        return inMemoryHistoryManager;
    }

    public List<Task> getPrioritizedTasks() {
        Comparator<Task> comparator = (task1, task2) -> {
            if (task1.getStartTime() == null || task2.getStartTime() == null){
                return 1;
            } else {
                return task2.getStartTime().compareTo(task1.getStartTime());
            }
        };
        Set<Task> taskSet = new TreeSet<>(comparator);
        taskSet.addAll(getAllTask());
        taskSet.addAll(getAllEpic());
        taskSet.addAll(getAllSubtask());
        return new ArrayList<>(taskSet);
    }
}