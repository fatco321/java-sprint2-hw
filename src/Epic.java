import java.util.ArrayList;

public class Epic extends Task {

    Epic setEpic(String taskName, String taskDescription) {
        Epic epic = new Epic();
        epic.taskName = taskName;
        epic.taskDescription = taskDescription;
        return epic;
    }

    String setStatus(ArrayList<Subtask> subtasks, Epic epic) {
        int i = 0;
        for (Subtask subtask : subtasks) {
            if (subtask.taskStatus.equals("IN_PROGRESS")) {
                return "IN_PROGRESS";
            }
            if (subtask.taskStatus.equals("DONE")) {
                i++;
            }
        }
        if (i == subtasks.size() && !subtasks.isEmpty()) {
            return "DONE";
        } else if (i > 0 && i < subtasks.size()) {
            return "IN_PROGRESS";
        } else if (subtasks.isEmpty()) {
            return epic.setStatus(epic.taskStatus);
        } else {
            return "NEW";
        }
    }
}

