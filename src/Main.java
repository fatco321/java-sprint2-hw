import ru.yandex.practicum.tasktraker.controller.*;
import ru.yandex.practicum.tasktraker.tasks.*;
import ru.yandex.practicum.tasktraker.util.Managers;

public class Main {

    public static void main(String[] args) {
        TaskManager inMemoryTaskManager = Managers.getDefault();
        Task task1 = new Task("Задача 1", "Описание 1", TaskStatus.NEW);
        Task task2 = new Task("Задача 2", "Описание 2", TaskStatus.NEW);
        Epic epic1 = new Epic("Эпик 1", "Описание Эпика 1");
        Epic epic2 = new Epic("Эпик 2", "Описание Эпика 2");
        Subtask subtask1 = new Subtask("Сабтаск Эпика 1", "Описание Сабтаска 1", TaskStatus.NEW,
                3);
        Subtask subtask2 = new Subtask("Сабтаск - 1 Эпика 2", "Описание Сабтаска 1", TaskStatus.NEW,
                4);
        Subtask subtask3 = new Subtask("Сабтаск - 2 Эпика 2", "Описание Сабтаска 2", TaskStatus.NEW,
                4);
        inMemoryTaskManager.addTask(task1);
        inMemoryTaskManager.addTask(task2);
        inMemoryTaskManager.addEpic(epic1);
        inMemoryTaskManager.addEpic(epic2);
        inMemoryTaskManager.addSubtask(subtask1);
        inMemoryTaskManager.addSubtask(subtask2);
        inMemoryTaskManager.addSubtask(subtask3);
        System.out.println("Список задач:");
       /* System.out.println(inMemoryTaskManager.getTask(1));
        System.out.println(inMemoryTaskManager.getTask(2));*/
        System.out.println(inMemoryTaskManager.getAllTask());
        System.out.println("Список Эпиков:");
        System.out.println("ЭПИК 1: " + inMemoryTaskManager.getEpic(3));
        System.out.println("ПОДЗАДАЧИ ЭПИКА 1: " + inMemoryTaskManager.getSubtask(5));
        System.out.println("ЭПИК 2: " + inMemoryTaskManager.getEpic(4));
        System.out.println("ПОДЗАДАЧИ ЭПИКА 1: " + inMemoryTaskManager.getSubtask(6) + " " + inMemoryTaskManager.getSubtask(7));
        Task taskNew1 = new Task("Задача 1", "Описание 1", TaskStatus.IN_PROGRESS);
        Task taskNew2 = new Task("Задача 2", "Описание 2", TaskStatus.DONE);
        inMemoryTaskManager.updateTask(1, taskNew1);
        inMemoryTaskManager.updateTask(2, taskNew2);
        System.out.println("Список обновленных задач:");
        System.out.println(inMemoryTaskManager.getAllTask());
        Subtask subtaskNew1 = new Subtask("Обновленный Сабтаск Эпика 1", "Описание Сабтаска 1",
                TaskStatus.DONE, 3);
        Subtask subtaskNew2 = new Subtask("Обновленный Сабтаск - 1 Эпика 2", "Описание Сабтаска 1",
                TaskStatus.DONE, 4);
        Subtask subtaskNew3 = new Subtask("Обновленный Сабтаск - 2 Эпика 2", "Описание Сабтаска 1",
                TaskStatus.IN_PROGRESS, 4);
        inMemoryTaskManager.updateSubtask(5, subtaskNew1);
        inMemoryTaskManager.updateSubtask(6, subtaskNew2);
        inMemoryTaskManager.updateSubtask(7, subtaskNew3);
        System.out.println("Список Эпиков:");
        System.out.println("ЭПИК 1: " + inMemoryTaskManager.getEpic(3));
        System.out.println("ПОДЗАДАЧИ ЭПИКА 1: " + inMemoryTaskManager.getSubtask(5));
        System.out.println("ЭПИК 2: " + inMemoryTaskManager.getEpic(4));
        System.out.println("ПОДЗАДАЧИ ЭПИКА 1: " + inMemoryTaskManager.getSubtask(6) + " " +
                inMemoryTaskManager.getSubtask(7));
        inMemoryTaskManager.deleteTask(1);
        inMemoryTaskManager.deleteEpic(3);
        System.out.println("Список всех Задач после удаления:");
        System.out.println(inMemoryTaskManager.getAllTask());
        System.out.println("Список всех Эпиков после удаления:");
        System.out.println(inMemoryTaskManager.getAllEpic());
        System.out.println("Список всех Сабтасков после удаления:");
        System.out.println(inMemoryTaskManager.getAllSubtask());
        inMemoryTaskManager.getTask(1);
        System.out.println(InMemoryTaskManager.getInMemoryHistoryManager().getHistory());

    }
}
