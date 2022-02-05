import ru.yandex.practicum.tasktraker.controller.Manager;
import ru.yandex.practicum.tasktraker.tasks.*;

public class Main {

    public static void main(String[] args) {
        Manager manager = new Manager();
        Task task1 = new Task("Задача 1", "Описание 1", "NEW");
        Task task2 = new Task("Задача 2", "Описание 2", "NEW");
        Epic epic1 = new Epic("Эпик 1", "Описание Эпика 1");
        Epic epic2 = new Epic("Эпик 2", "Описание Эпика 2");
        Subtask subtask1 = new Subtask("Сабтаск Эпика 1", "Описание Сабтаска 1", "NEW",
                3);
        Subtask subtask2 = new Subtask("Сабтаск - 1 Эпика 2", "Описание Сабтаска 1", "NEW",
                4);
        Subtask subtask3 = new Subtask("Сабтаск - 2 Эпика 2", "Описание Сабтаска 2", "NEW",
                4);
        manager.addTask(task1);
        manager.addTask(task2);
        manager.addEpic(epic1);
        manager.addEpic(epic2);
        manager.addSubtask(subtask1);
        manager.addSubtask(subtask2);
        manager.addSubtask(subtask3);
        System.out.println("Список задач:");
        System.out.println(manager.getAllTask());
        System.out.println("Список Эпиков:");
        System.out.println("ЭПИК 1: " + manager.getEpic(3));
        System.out.println("ПОДЗАДАЧИ ЭПИКА 1: " + manager.getSubtask(5));
        System.out.println("ЭПИК 2: " + manager.getEpic(4));
        System.out.println("ПОДЗАДАЧИ ЭПИКА 1: " + manager.getSubtask(6) + " " + manager.getSubtask(7));
        Task taskNew1 = new Task("Задача 1", "Описание 1", "IN_PROGRESS");
        Task taskNew2 = new Task("Задача 2", "Описание 2", "DONE");
        manager.updateTask(1, taskNew1);
        manager.updateTask(2, taskNew2);
        System.out.println("Список обновленных задач:");
        System.out.println(manager.getAllTask());
        Subtask subtaskNew1 = new Subtask("Обновленный Сабтаск Эпика 1", "Описание Сабтаска 1",
                "DONE", 3);
        Subtask subtaskNew2 = new Subtask("Обновленный Сабтаск - 1 Эпика 2", "Описание Сабтаска 1",
                "DONE", 4);
        Subtask subtaskNew3 = new Subtask("Обновленный Сабтаск - 2 Эпика 2", "Описание Сабтаска 1",
                "IN_PROGRESS", 4);
        manager.updateSubtask(5, subtaskNew1);
        manager.updateSubtask(6, subtaskNew2);
        manager.updateSubtask(7, subtaskNew3);
        System.out.println("Список Эпиков:");
        System.out.println("ЭПИК 1: " + manager.getEpic(3));
        System.out.println("ПОДЗАДАЧИ ЭПИКА 1: " + manager.getSubtask(5));
        System.out.println("ЭПИК 2: " + manager.getEpic(4));
        System.out.println("ПОДЗАДАЧИ ЭПИКА 1: " + manager.getSubtask(6) + " " + manager.getSubtask(7));
        manager.deleteTask(1);
        manager.deleteEpic(3);
        System.out.println("Список всех Задач после удаления:");
        System.out.println(manager.getAllTask());
        System.out.println("Список всех Эпиков после удаления:");
        System.out.println(manager.getAllEpic());
        System.out.println("Список всех Сабтасков после удаления:");
        System.out.println(manager.getAllSubtask());
    }
}
