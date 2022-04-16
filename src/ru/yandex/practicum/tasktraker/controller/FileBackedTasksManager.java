package ru.yandex.practicum.tasktraker.controller;

import ru.yandex.practicum.tasktraker.controller.exception.ManagerSaveException;
import ru.yandex.practicum.tasktraker.tasks.*;

import java.io.*;
import java.nio.charset.Charset;
import java.util.LinkedList;
import java.util.List;

public class FileBackedTasksManager extends InMemoryTaskManager implements TaskManager {

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
        FileBackedTasksManager fb1 = FileBackedTasksManager.loadFromFile(new File("tasks.csv"));
    }

    public static FileBackedTasksManager loadFromFile(File file) {
        FileBackedTasksManager fb = new FileBackedTasksManager();
        List<String> bufferList = new LinkedList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file
                , Charset.forName("Windows-1251")))) {
            while (br.ready()) {
                bufferList.add(br.readLine());
            }
            for (String line : bufferList) {
                String[] split = line.split(";");
                if (!split[0].equals("ID")) {
                    if (split.length >= 5) {
                        if (split[1].equals("TASK")) {
                            fb.addTask((fromString(Integer.parseInt(split[0]))));
                        } else if (split[1].equals("EPIC")) {
                            fb.addEpic((Epic) fromString(Integer.parseInt(split[0])));
                        } else {
                            fb.addSubtask((Subtask) fromString(Integer.parseInt(split[0])));
                        }
                    } else if (split.length == 1) {
                        if (!split[0].equals("HISTORY")) {
                            fb.getTask(Integer.parseInt(split[0]));
                            fb.getSubtask(Integer.parseInt(split[0]));
                            fb.getEpic(Integer.parseInt(split[0]));
                        }
                    }
                }
            }
            return fb;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new FileBackedTasksManager();
    }

    private void save() {
        try (FileWriter fw = new FileWriter("tasks.csv",
                Charset.forName("Windows-1251"))) {
            fw.write("ID" + ";" + "TYPE" + ";" + "NAME" + ";" + "STATUS" + ";" +
                    "DESCRIPTION" + ";" + "EPIC");
            for (Task task : getAllTask()) {
                fw.write("\n");
                fw.write(task.toString().replace(",", ";"));
            }
            for (Task epic : getAllEpic()) {
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

    public static Task fromString(int id) {
        try (BufferedReader br = new BufferedReader(new FileReader("tasks.csv",
                Charset.forName("Windows-1251")))) {
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