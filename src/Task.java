public class Task {
    protected String taskName;
    protected String taskDescription;
    protected String taskStatus = "NEW";

    Task setTask(String taskName, String taskDescription) {
        Task task = new Task();
        task.taskName = taskName;
        task.taskDescription = taskDescription;
        return task;
    }

    String setStatus(String status) {
        if (status.equals("NEW")) {
            return "IN_PROGRESS";
        } else {
            return "DONE";
        }
    }
}
