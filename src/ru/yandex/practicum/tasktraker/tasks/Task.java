package ru.yandex.practicum.tasktraker.tasks;

public class Task {
    private String taskName;
    private String taskDescription;
    private TaskStatus status = TaskStatus.NEW;
    private int id;

    Task(String taskName, String taskDescription) {
        this.taskName = taskName;
        this.taskDescription = taskDescription;
    }

    public Task(String taskName, String taskDescription, TaskStatus status) {
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.status = status;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

     public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return id + "," + TaskType.TASK + "," +
                taskName + "," + status + "," + taskDescription;
    }

}