import ru.yandex.practicum.tasktraker.controller.*;
import ru.yandex.practicum.tasktraker.historic.HistoryManager;
import ru.yandex.practicum.tasktraker.tasks.*;
import ru.yandex.practicum.tasktraker.util.Managers;

public class Main {

    public static void main(String[] args) {
        TaskManager taskManager = Managers.getDefault();
        HistoryManager historyManager = taskManager.history();
        Task task = new Task("Задача 1", "Описание 1", TaskStatus.NEW);
        Epic epic = new Epic("Эпик 1", "Описание 1");
        Subtask subtask = new Subtask("Подзадача 1", "Описание 1", TaskStatus.NEW, 2);
        taskManager.addTask(task);
        taskManager.addEpic(epic);
        taskManager.addSubtask(subtask);
        taskManager.getTask(1);
        System.out.println(historyManager.getHistory());
        taskManager.getEpic(2);
        System.out.println(historyManager.getHistory());
        taskManager.getSubtask(3);
        System.out.println(historyManager.getHistory());

    }
}
