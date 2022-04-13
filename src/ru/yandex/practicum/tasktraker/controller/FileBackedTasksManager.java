package ru.yandex.practicum.tasktraker.controller;

import ru.yandex.practicum.tasktraker.controller.exception.ManagerSaveException;
import ru.yandex.practicum.tasktraker.tasks.*;

import java.io.*;
import java.nio.charset.Charset;

public class FileBackedTasksManager extends InMemoryTaskManager implements TaskManager {
    private String pathToFile = "C:\\Users\\fatco\\dev" +
            "\\java-sprint5-hw\\Tasks\\tasks.csv";
    private static boolean append = false;

    public static void main(String[] args) {
        FileBackedTasksManager fb = new FileBackedTasksManager();
        Task task = new Task("Задача 1", "Описание 1", TaskStatus.IN_PROGRESS);
        Task task1 = new Task("Задача 2", "Описание 2", TaskStatus.NEW);
        Epic epic = new Epic("Эпик 1", "Описание 1");
        Epic epic1 = new Epic("Эпик 2", "Описание 2");
        Subtask subtask = new Subtask("Подзадача 1", "Описание 1", TaskStatus.DONE, 2);
        fb.addTask(task);
        fb.addEpic(epic);
        fb.addSubtask(subtask);
        fb.getTask(1);
        fb.getSubtask(3);
        FileBackedTasksManager fileBackedTasksManager = FileBackedTasksManager.load("tasks.csv");
        fileBackedTasksManager.addTask(task1);
    }

    public FileBackedTasksManager() {
        createDirectory();
    }

    private FileBackedTasksManager(String file) {
        this.pathToFile = "C:\\Users\\fatco\\dev" +
                "\\java-sprint5-hw\\Tasks\\" + file;
    }

    private void createDirectory() {
        File file = new File("C:\\Users\\fatco\\dev" +
                "\\java-sprint5-hw\\Tasks\\");
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    public static FileBackedTasksManager load(String file) {
        append = true;
        return new FileBackedTasksManager(file);
    }

    private int readID() {
        int id = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(pathToFile, Charset.forName("Windows-1251")))) {
            while (br.ready()) {
                String[] split = br.readLine().split(";");
                if (!split[0].equals("ID")) {
                    if (!split[0].equals("HISTORY"))
                        id = Integer.parseInt(split[0]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return id;
    }

    private void save() {
        try (FileWriter fw = new FileWriter(pathToFile, Charset.forName("Windows-1251"), append)) {
            if (append) {
                fw.write("\n");
            }
            fw.write("ID" + ";" + "TYPE" + ";" + "NAME" + ";" + "STATUS" + ";" +
                    "DESCRIPTION" + ";" + "EPIC");
            for (Task task : getAllTask()) {
                if (append) {
                    if (task.getId() <= readID()) {
                        task.setId(readID() + 1);
                    }
                }
                fw.write("\n");
                fw.write(task.toString().replace(",", ";"));
            }
            for (Task epic : getAllEpic()) {
                if (append) {
                    if (epic.getId() <= readID()) {
                        epic.setId(readID() + 1);
                    }
                }
                String[] split = epic.toString().split(",");
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < 5; i++) {
                    sb.append(split[i]);
                    sb.append(";");
                }
                fw.write("\n");
                fw.write(sb.toString());
            }
            for (Task subtask : getAllSubtask()) {
                if (append) {
                    if (subtask.getId() <= readID()) {
                        subtask.setId(readID() + 1);
                    }
                }
                fw.write("\n");
                fw.write(subtask.toString().replace(",", ";"));
            }
            fw.write("\n");
            fw.write("HISTORY");
            for (Task historyTask : history()) {
                fw.write("\n");
                String[] split = historyTask.toString().split(",");
                fw.write(split[0]);
            }
        } catch (IOException e) {
            throw new ManagerSaveException("ACHTUNG ALARM —— ERROR");
        }
    }

    public Task fromString(int id) {
        try (BufferedReader br = new BufferedReader(new FileReader(pathToFile, Charset.forName("Windows-1251")))) {
            while (br.ready()) {
                String[] split = br.readLine().split(";");
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
                                    Task subtask = new Subtask(split[2], split[4], TaskStatus.valueOf(split[3]), epicId);
                                    subtask.setId(id);
                                    return subtask;
                                case EPIC:
                                    Task epic = new Epic(split[2], split[4]);
                                    epic.setId(id);
                                    return epic;
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
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
    public void deleteTask(int taskId) {
        super.deleteTask(taskId);
        save();
    }

    @Override
    public void deleteEpic(int epicId) {
        super.deleteEpic(epicId);
        save();
    }

    @Override
    public void deleteSubtask(int subtaskId) {
        super.deleteSubtask(subtaskId);
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
}