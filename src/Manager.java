import java.util.ArrayList;
import java.util.HashMap;

public class Manager {
    private HashMap<Integer, Task> taskHashMap = new HashMap<>();
    private HashMap<Integer, Epic> epicHashMap = new HashMap<>();
    private HashMap<Epic, ArrayList<Subtask>> subtaskHashMap = new HashMap<>();
    private ArrayList<Subtask> subtaskList = new ArrayList<>();
    private Task task = new Task();
    private Epic epic = new Epic();
    private Subtask subtask = new Subtask();
    private int id = 0;

    void setId() {
        id++;
    }

    void addTask(String taskName, String taskDescription) {
        setId();
        taskHashMap.put(id, task.setTask(taskName, taskDescription));
    }

    String getTask(int id) {
        task = taskHashMap.get(id);
        return task != null ? "Задача: " + task.taskName + ". Описание: " + task.taskDescription
                + ". Статус: " + task.taskStatus : "Такой задачи нет";
    }

    void changeTaskStatus(int id) {
        task = taskHashMap.get(id);
        if (task != null) {
            task.taskStatus = task.setStatus(task.taskStatus);
        }
    }

    void updateTask(int id, String taskName, String taskDescription) {
        task = taskHashMap.get(id);
        if (task != null) {
            taskHashMap.put(id, task.setTask(taskName, taskDescription));
        }
    }

    void getAllTask() {
        for (int key : taskHashMap.keySet()) {
            System.out.println(getTask(key));
        }
    }

    void deleteTask(int id) {
        taskHashMap.remove(id);
    }

    void deleteAllTask() {
        taskHashMap.clear();
    }

    void addEpic(String taskName, String taskDescription) {
        setId();
        epicHashMap.put(id, epic.setEpic(taskName, taskDescription));
    }

    void getEpic(int id) {
        epic = epicHashMap.get(id);
        if (epic != null) {
            System.out.println("Эпик: " + epic.taskName + " Описание: " + epic.taskDescription
                    + " Статус: " + epic.taskStatus);
            ArrayList<Subtask> listSub = subtaskHashMap.get(epic);
            if (listSub != null) {
                for (Subtask subtask : listSub) {
                    if (subtask != null) {
                        System.out.println("Подзача: " + subtask.taskName + " Описание: " + subtask.taskDescription
                                + " Статус: " + subtask.taskStatus);
                    }
                }
            }
        }
    }

    void getAllEpic() {
        for (int key : epicHashMap.keySet()) {
            getEpic(key);
        }
    }

    void updateEpic(int id, String taskName, String taskDescription) {
        epic = epicHashMap.get(id);
        if (epic != null) {
            ArrayList<Subtask> listSub = subtaskHashMap.get(epic);
            if (listSub != null) {
                for (Subtask subtask : listSub) {
                    subtaskList.add(subtask);
                }
            }
            epic = epic.setEpic(taskName, taskDescription);
            subtaskHashMap.put(epic, subtaskList);
            epicHashMap.put(id, epic);
            subtaskList = new ArrayList<>();
        }
    }

    private void changeEpicStatus(int id) {
        epic = epicHashMap.get(id);
        if (epic != null) {
            ArrayList<Subtask> subList = subtaskHashMap.get(epic);
            if (subList != null) {
                epic.taskStatus = epic.setStatus(subList, epic);
            }
        }
    }

    void deleteEpic(int id) {
        epicHashMap.remove(id);
    }

    void deleteAllEpic() {
        epicHashMap.clear();
    }

    void addSubs(String taskName, String taskDescription) {
        subtaskList.add(subtask.setSubtask(taskName, taskDescription));
    }

    void putSubInEpic(int id) {
        epic = epicHashMap.get(id);
        subtaskHashMap.put(epic, subtaskList);
        subtaskList = new ArrayList<>();
    }

    void changeSubStatus(int id, int index) {
        epic = epicHashMap.get(id);
        if (epic != null) {
            ArrayList<Subtask> subList = subtaskHashMap.get(epic);
            if (subList != null) {
                if (index < subList.size()) {
                    subtask = subList.get(index);
                    subtask.taskStatus = subtask.setStatus(subtask.taskStatus);
                }
            }
        }
        changeEpicStatus(id);
    }

    void deleteSubtask(int id, int index) {
        epic = epicHashMap.get(id);
        if (epic != null) {
            ArrayList<Subtask> listSub = subtaskHashMap.get(epic);
            if (listSub != null) {
                if (index == 0 || index < listSub.size()) {
                    listSub.remove(index);
                    subtaskHashMap.put(epic, listSub);
                }
            }
        }
    }

    void deleteAllSubtask(int id) {
        epic = epicHashMap.get(id);
        if (epic != null) {
            ArrayList<Subtask> listSub = subtaskHashMap.get(epic);
            if (listSub != null) {
                listSub.clear();
                subtaskHashMap.put(epic, listSub);
            }
        }
    }

    void updateSubtask(int id, int index, String taskName, String taskDescription) {
        epic = epicHashMap.get(id);
        if (epic != null) {
            ArrayList<Subtask> listSub = subtaskHashMap.get(epic);
            if (listSub != null) {
                if (index == 0 || index < listSub.size()) {
                    subtask = listSub.get(index);
                    subtask = subtask.setSubtask(taskName, taskDescription);
                    listSub.add(index, subtask);
                    subtaskHashMap.put(epic, listSub);
                }
            }
        }
    }
}
