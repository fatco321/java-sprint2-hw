import ru.yandex.practicum.tasktraker.controller.*;
import ru.yandex.practicum.tasktraker.tasks.*;
import ru.yandex.practicum.tasktraker.util.Managers;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Month;

public class Main {

    public static void main(String[] args) {
        TaskManager taskManager = Managers.getDefault();
        Task task = new Task("Задача 1", "Описание 1", TaskStatus.IN_PROGRESS);
        Task task1 = new Task("Задача 2", "Описание 2", TaskStatus.NEW);
        Epic epic = new Epic("Эпик 1", "Описание 1");
        Epic epic1 = new Epic("Эпик 2", "Описание 2");
        Task task2 = null;
        Subtask subtask = new Subtask("Подзадача 1", "Описание 1", TaskStatus.DONE, 1);
        Subtask subtask1 = new Subtask("Подзадача 2", "Описание 2", TaskStatus.NEW, 1,
                 LocalDateTime.of(2022, Month.APRIL, 29, 0, 0),Duration.ofDays(7));
        Subtask subtask2 = new Subtask("Подзадача 3", "Описание 3", TaskStatus.NEW, 1);
        taskManager.addTask(task);
        taskManager.addTask(task1);
        taskManager.addEpic(epic);
        taskManager.addEpic(epic1);
        taskManager.addSubtask(subtask);
        taskManager.addSubtask(subtask1);
        taskManager.addSubtask(subtask2);
        taskManager.addTask(task2);
        taskManager.getTask(1);
        System.out.println(taskManager.history());
        taskManager.getTask(1);
        taskManager.getTask(2);
        taskManager.getTask(1);
        System.out.println(taskManager.history());
        taskManager.getEpic(3);
        taskManager.getSubtask(4);
        taskManager.getSubtask(5);
        taskManager.getSubtask(6);
        taskManager.getEpic(4);
        taskManager.deleteTask(2);
        System.out.println(taskManager.history());
        taskManager.deleteSubtask(5);
        System.out.println(taskManager.history());
        taskManager.deleteEpic(3);
        System.out.println(taskManager.history());
        taskManager.addEpic(epic);
        taskManager.addSubtask(subtask);
        taskManager.addSubtask(subtask1);
        taskManager.addSubtask(subtask2);
        System.out.println(taskManager.getEpic(1).getStartTime() + "\n" + taskManager.getEpic(1).getEndTime() +
               "\n" + taskManager.getEpic(1).getDuration().toDays());

    }
}
