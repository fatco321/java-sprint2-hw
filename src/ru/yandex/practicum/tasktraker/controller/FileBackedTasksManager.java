package ru.yandex.practicum.tasktraker.controller;

import ru.yandex.practicum.tasktraker.controller.exception.ManagerSaveException;
import ru.yandex.practicum.tasktraker.tasks.*;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class FileBackedTasksManager extends InMemoryTaskManager implements TaskManager {
    private static final List<String> taskListInCsv = readCsvToArray();
    private boolean loadFile = false;



    public static void main(String[] args) {
        FileBackedTasksManager fb = new FileBackedTasksManager();
        Task task = new Task("Задача 1", "Описание 1", TaskStatus.IN_PROGRESS);
        Task task1 = new Task("Задача 2", "Описание 2", TaskStatus.NEW);
        Epic epic = new Epic("Эпик 1", "Описание 1");
        Epic epic1 = new Epic("Эпик 2", "Описание 2");
        Subtask subtask = new Subtask("Подзадача 1", "Описание 1", TaskStatus.DONE, 3);
        fb.addTask(task);
        fb.addTask(task1);
        fb.addEpic(epic);
        fb.addSubtask(subtask);
        fb.getTask(1);
        fb.getEpic(3);
        FileBackedTasksManager fileBackedTasksManager = FileBackedTasksManager.loadFromFile(new File("tasks.csv"));
        fileBackedTasksManager.addEpic(epic1);
        fileBackedTasksManager.getEpic(5);
    }

    private void save() {
        try (PrintWriter pw = new PrintWriter("tasks.csv", "Windows-1251")) {
            pw.write("ID;TYPE;NAME;STATUS;DESCRIPTION;EPIC" + "\n");
            for (Task task : getAllTask()) {
                pw.write(task.toString().replace(",", ";") + "\n");
            }
            for (Task epic : getAllEpic()) {
                String[] split = epic.toString().split(",");
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < 5; i++) {
                    sb.append(split[i]);
                    sb.append(";");
                }
                pw.write(sb + "\n");
            }
            for (Task subtask : getAllSubtask()) {
                pw.write(subtask.toString().replace(",", ";") + "\n");
            }
            pw.write("HISTORY" + "\n");
            for (Task inHistoryTask : history()) {
                String[] split = inHistoryTask.toString().split(",");
                pw.write(split[0] + "\n");
            }
        } catch (IOException e) {
            throw new ManagerSaveException("ACHTUNG ALARM —— ERROR");
        }
    }

    public static FileBackedTasksManager loadFromFile(File file) {
        FileBackedTasksManager fileBack = new FileBackedTasksManager();
        List<String> taskInFile = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file,
                Charset.forName("Windows-1251")))) {
            while (br.ready()) {
                taskInFile.add(br.readLine());
            }
            taskInFile.remove(0);
            taskInFile.remove("HISTORY");
            for (String line : taskInFile) {
                String[] split = line.split(";");
                if (split.length >= 5) {
                    if (split[1].equals("TASK")) {
                        fileBack.addTask(fromString(Integer.parseInt(split[0])));
                    } else if (split[1].equals("EPIC")) {
                        fileBack.addEpic((Epic) fromString(Integer.parseInt(split[0])));
                    } else {
                        fileBack.addSubtask((Subtask) fromString(Integer.parseInt(split[0])));
                    }
                }
            }
            for (String line : taskInFile) {
                String[] split = line.split(";");
                if (split.length == 1) {
                    fileBack.getTask(Integer.parseInt(split[0]));
                    fileBack.getEpic(Integer.parseInt(split[0]));
                    fileBack.getSubtask(Integer.parseInt(split[0]));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileBack;
    }

    private static List<String> readCsvToArray() {
        List<String> bufferList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("tasks.csv",
                Charset.forName("Windows-1251")))) {
            while (br.ready()) {
                bufferList.add(br.readLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bufferList;
    }

    public static Task fromString(int id) {
        for (String line : taskListInCsv) {
            String[] split = line.split(";");
            if (!split[0].equals("ID")) {
                if (!split[0].equals("HISTORY")) {
                    int targetId = Integer.parseInt(split[0]);
                    if (targetId == id) {
                        switch (TaskType.valueOf(split[1])) {
                            case TASK:
                                Task task = new Task(split[2], split[4], TaskStatus.valueOf(split[3]));
                                task.setId(id);
                                return task;
                            case SUBTASK:
                                int epicId = Integer.parseInt(split[5].substring(9));
                                Subtask subtask = new Subtask(split[2], split[4], TaskStatus.valueOf(split[3]), epicId);
                                subtask.setId(id);
                                return subtask;
                            case EPIC:
                                Epic epic = new Epic(split[2], split[4]);
                                epic.setId(id);
                                return epic;
                        }
                    }
                }
            }
        }
        return null;
    }

    @Override
    public void addTask(Task task) {
        super.addTask(task);
        save();
    }

    @Override
    public void addEpic(Epic epic) {
        super.addEpic(epic);
        save();
    }

    @Override
    public void addSubtask(Subtask subtask) {
        super.addSubtask(subtask);
        save();
    }

    @Override
    public Task getTask(int taskId) {
        super.getTask(taskId);
        save();
        return super.getTask(taskId);
    }

    @Override
    public Epic getEpic(int epicId) {
        super.getEpic(epicId);
        save();
        return super.getEpic(epicId);
    }

    @Override
    public Subtask getSubtask(int sabtaskId) {
        super.getSubtask(sabtaskId);
        save();
        return super.getSubtask(sabtaskId);
    }

    @Override
    public void updateTask(int taskId, Task taskNew) {
        super.updateTask(taskId, taskNew);
        save();
    }

    @Override
    public void deleteTask(int taskId) {
        super.deleteTask(taskId);
        save();
    }

    @Override
    public void deleteAllTask() {
        super.deleteAllTask();
        save();
    }

    @Override
    public void updateEpic(int epicId, Epic epicNew) {
        super.updateEpic(epicId, epicNew);
        save();
    }

    @Override
    public void deleteEpic(int epicId) {
        super.deleteEpic(epicId);
        save();
    }

    @Override
    public void deleteAllEpic() {
        super.deleteAllEpic();
        save();
    }

    @Override
    public void updateSubtask(int subtaskId, Subtask subtasNew) {
        super.updateSubtask(subtaskId, subtasNew);
        save();
    }

    @Override
    public void deleteSubtask(int subtaskId) {
        super.deleteSubtask(subtaskId);
        save();
    }

    @Override
    public void deleteAllSubtask() {
        super.deleteAllSubtask();
        save();
    }
}
