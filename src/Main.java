import ru.yandex.practicum.tasktraker.controller.Manager;

public class Main {

    public static void main(String[] args) {
        // write your code here
        Manager manager = new Manager();
        manager.addTask("Задача 1", "Описание"); //1
        manager.addTask("Задача 2", "Описание"); //2
        manager.addEpic("Эпик 1", "Описание"); //3
        manager.addSubtask(3,"Подзадача эпика 1.1","Описание");
        manager.addSubtask(3,"Подзадача эпика 1.2", "Описание");
        manager.addEpic("Эпик 2","Описание"); //4
        manager.addSubtask(4,"Подзадача эпика 2.1", "Описание");
        System.out.println(manager.getTask(1));
        System.out.println(manager.getTask(2));
        System.out.println();
        System.out.println(manager.getEpic(3));
        System.out.println(manager.getSubtask(3));
        System.out.println();
        System.out.println(manager.getEpic(4));
        System.out.println(manager.getSubtask(4));
        System.out.println();
        manager.changeTaskStatus(1);
        manager.changeTaskStatus(2);
        manager.changeTaskStatus(2);
        System.out.println(manager.getTask(1));
        System.out.println(manager.getTask(2));
        System.out.println();
        manager.changeSubStatus(3, 0);
        manager.changeSubStatus(3, 0);
        manager.changeSubStatus(3, 1);
        manager.changeSubStatus(3, 1);
        System.out.println(manager.getEpic(3));
        System.out.println(manager.getSubtask(3));
        System.out.println();
        manager.changeSubStatus(4, 0);
        System.out.println(manager.getEpic(4));
        System.out.println(manager.getSubtask(4));
        System.out.println();
        manager.deleteTask(1);
        manager.deleteEpic(3);
        System.out.println(manager.getAllTask());
        System.out.println(manager.getAllEpic());
    }
}
