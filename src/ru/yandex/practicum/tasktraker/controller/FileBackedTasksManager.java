package ru.yandex.practicum.tasktraker.controller;

import ru.yandex.practicum.tasktraker.controller.exception.ManagerSaveException;
import ru.yandex.practicum.tasktraker.tasks.*;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FileBackedTasksManager extends InMemoryTaskManager implements TaskManager {
    private static List<String> taskListInCsv = writeCsvToArray();
    private static File csvFile = new File("tasks.csv");

    public static void main(String[] args) {
        FileBackedTasksManager fileBacked = new FileBackedTasksManager();
        Task task = new Task("Задача 1", "Описание 1", TaskStatus.IN_PROGRESS,
                LocalDateTime.of(2022, 2, 3, 11, 22), Duration.ofDays(2));
        Task task1 = new Task("Задача 2", "Описание 2", TaskStatus.NEW);
        Epic epic = new Epic("Эпик 1", "Описание 1");
        Epic epic1 = new Epic("Эпик 2", "Описание 2");
        Subtask subtask = new Subtask("Подзадача 1", "Описание 1", TaskStatus.DONE, 3,
                LocalDateTime.now(), Duration.ofDays(4));
        fileBacked.addTask(task);
        fileBacked.addTask(task1);
        fileBacked.addEpic(epic);
        fileBacked.addSubtask(subtask);
        fileBacked.getTask(1);
        fileBacked.getEpic(3);
        FileBackedTasksManager fileBackedTasksManager = FileBackedTasksManager.loadFromFile(new File
                ("tasks.csv"));
        fileBackedTasksManager.addEpic(epic1);
        fileBackedTasksManager.getEpic(5);
        Subtask subtask1 = new Subtask("Подзадача 2", "Описание 2", TaskStatus.DONE, 3,
                LocalDateTime.of(2012, 5, 6, 5, 2), Duration.ofDays(4));
        fileBackedTasksManager.addSubtask(subtask1);
    }

    protected void save() {
        try (PrintWriter pw = new PrintWriter(csvFile, "Windows-1251")) {
            pw.write("ID;TYPE;NAME;STATUS;DESCRIPTION;EPIC;StartTime;Duration;EndTime" + "\n");
            for (Task task : getAllTask()) {
                pw.write(task.toString().replace(",", ";") + "\n");
            }
            for (Epic epic : getAllEpic()) {
                pw.write(epic.toString().replace(",", ";") + "\n");
            }
            for (Subtask subtask : getAllSubtask()) {
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
        FileBackedTasksManager.taskListInCsv = FileBackedTasksManager.writeCsvToArray();
        FileBackedTasksManager.setCsvFile(file);
        FileBackedTasksManager fileBack = new FileBackedTasksManager();
        taskListInCsv.remove(0);
        taskListInCsv.remove("HISTORY");
        for (String line : taskListInCsv) {
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
        for (String line : taskListInCsv) {
            String[] split = line.split(";");
            if (split.length == 1) {
                fileBack.getTask(Integer.parseInt(split[0]));
                fileBack.getEpic(Integer.parseInt(split[0]));
                fileBack.getSubtask(Integer.parseInt(split[0]));
            }
        }
        return fileBack;
    }

    private static List<String> writeCsvToArray() {
        if (Files.exists(Path.of(String.valueOf(csvFile)))) {
            List<String> bufferList = new ArrayList<>();
            try (BufferedReader br = new BufferedReader(new FileReader(csvFile,
                    Charset.forName("Windows-1251")))) {
                while (br.ready()) {
                    bufferList.add(br.readLine());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bufferList;
        }
        return null;
    }

    public static Task fromString(int id) {
        for (String line : taskListInCsv) {
            String[] split = line.split(";");
            if (!split[0].equals("ID")) {
                if (!split[0].equals("HISTORY")) {
                    int targetId = Integer.parseInt(split[0]);
                    if (targetId == id) {
                        switch (TaskType.valueOf(split[1])) {
                            case TASK -> {
                                Task task = new Task(split[2], split[4], TaskStatus.valueOf(split[3]));
                                task.setId(id);
                                if (!split[6].equals("null")) {
                                    task.setStartTime(LocalDateTime.parse(split[6]));
                                    task.setDuration(Duration.parse(split[7]));
                                }
                                return task;
                            }
                            case SUBTASK -> {
                                int epicId = Integer.parseInt(split[5].substring(9));
                                Subtask subtask = new Subtask(split[2], split[4], TaskStatus.valueOf(split[3]), epicId);
                                subtask.setId(id);
                                if (!split[6].equals("null")) {
                                    subtask.setStartTime(LocalDateTime.parse(split[6]));
                                    subtask.setDuration(Duration.parse(split[7]));
                                }
                                return subtask;
                            }
                            case EPIC -> {
                                Epic epic = new Epic(split[2], split[4]);
                                epic.setId(id);
                                return epic;
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    public static void setCsvFile(File csvFile) {
        FileBackedTasksManager.csvFile = csvFile;
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
    public Subtask getSubtask(int subtaskId) {
        super.getSubtask(subtaskId);
        save();
        return super.getSubtask(subtaskId);
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
    public void updateSubtask(int subtaskId, Subtask subtaskNew) {
        super.updateSubtask(subtaskId, subtaskNew);
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
