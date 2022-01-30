public class Main {

    public static void main(String[] args) {
        // write your code here
        Manager manager = new Manager();

        manager.addTask("Задача 1", "Описание");
        manager.addTask("Задача 2", "Описание");
        manager.addEpic("Эпик 1", "Описание");
        manager.addSubs("Подзадача эпика 1.1", "Описание");
        manager.addSubs("Подзадача эпика 1.2", "Описание");
        manager.putSubInEpic(3);
        manager.addEpic("Эпик 2", "Описание");
        manager.addSubs("Подзадача эпика 2.1", "Описание");
        manager.putSubInEpic(4);
        System.out.println();
        System.out.println("Добавление задач:");
        System.out.println();
        manager.getAllTask();
        System.out.println();
        manager.getAllEpic();
        System.out.println();
        System.out.println("Изменение статусов:");
        manager.changeTaskStatus(1);
        manager.changeTaskStatus(2);
        manager.changeTaskStatus(2);
        manager.changeSubStatus(3, 0);
        manager.changeSubStatus(3, 0);
        manager.changeSubStatus(4, 0);
        manager.changeSubStatus(4, 0);
        System.out.println();
        manager.getAllTask();
        System.out.println();
        manager.getAllEpic();
        System.out.println();
        System.out.println("Удаление задач:");
        manager.deleteTask(1);
        manager.deleteEpic(3);
        System.out.println();
        manager.getAllTask();
        System.out.println();
        manager.getAllEpic();
    }
}
