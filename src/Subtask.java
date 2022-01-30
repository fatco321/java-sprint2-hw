public class Subtask extends Task {

    Subtask setSubtask(String taskName, String taskDescription) {
        Subtask subtask = new Subtask();
        subtask.taskName = taskName;
        subtask.taskDescription = taskDescription;
        return subtask;
    }
}
