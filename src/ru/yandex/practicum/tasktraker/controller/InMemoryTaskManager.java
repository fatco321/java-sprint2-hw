package ru.yandex.practicum.tasktraker.controller;

import ru.yandex.practicum.tasktraker.tasks.*;
import ru.yandex.practicum.tasktraker.historic.*;
import ru.yandex.practicum.tasktraker.util.Managers;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Predicate;

public class InMemoryTaskManager implements TaskManager {
    private final HistoryManager inMemoryHistoryManager = Managers.getDefaultHistory();
    private  HashMap<Integer, Task> taskHashMap = new HashMap<>();
    private  HashMap<Integer, Epic> epicHashMap = new HashMap<>();
    private  HashMap<Integer, Subtask> subtaskHashMap = new HashMap<>();
    private int id = 0;
    private final Set<Task> taskTreeSet = new TreeSet<>((o1, o2) -> {
        if (o1.getStartTime() == null || o2.getStartTime() == null) {
            return 1;
        } else {
            return o2.getStartTime().compareTo(o1.getStartTime());
        }
    });

    @Override
    public void setId() {
        id++;
    }

    @Override
    public void addTask(Task task) {
        if (task == null)
            return;
        if (noIntersection.test(task)) {
            setId();
            task.setId(id);
            taskHashMap.put(id, task);
            taskTreeSet.add(task);
        }
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
        taskTreeSet.add(subtask);
        taskTreeSet.add(epicHashMap.get(subtask.getEpicId()));
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

    public Set<Task> getPrioritizedTasks() {
        return taskTreeSet;
    }

    private final Predicate<Task> noIntersection = newTask -> {
        if (newTask.getStartTime() == null) {
            return true;
        }
        LocalDateTime newTaskStart = newTask.getStartTime();
        LocalDateTime newTaskFinish = newTask.getEndTime();
        for (Task task : taskTreeSet) {
            LocalDateTime taskStart = task.getStartTime();
            LocalDateTime taskFinish = task.getEndTime();
            if (newTaskStart.isBefore(taskStart) && newTaskFinish.isAfter(taskStart)) {
                return false;
            }
            if (newTaskStart.isBefore(taskFinish) && newTaskFinish.isAfter(taskFinish)) {
                return false;
            }
            if ((newTaskStart.isBefore(taskStart) && newTaskFinish.isBefore(taskStart)) &&
                    (newTaskStart.isBefore(taskFinish) && newTaskFinish.isBefore(taskFinish))) {
                break;
            }
        }
        return true;
    };

    public HashMap<Integer, Task> getTaskHashMap() {
        return taskHashMap;
    }

    public void setTaskHashMap(HashMap<Integer, Task> taskHashMap) {
        this.taskHashMap = taskHashMap;
    }

    public HashMap<Integer, Epic> getEpicHashMap() {
        return epicHashMap;
    }

    public void setEpicHashMap(HashMap<Integer, Epic> epicHashMap) {
        this.epicHashMap = epicHashMap;
    }

    public HashMap<Integer, Subtask> getSubtaskHashMap() {
        return subtaskHashMap;
    }

    public void setSubtaskHashMap(HashMap<Integer, Subtask> subtaskHashMap) {
        this.subtaskHashMap = subtaskHashMap;
    }

    public HistoryManager getHistoryManager(){
        return inMemoryHistoryManager;
    }
}