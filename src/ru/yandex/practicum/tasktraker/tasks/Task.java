package ru.yandex.practicum.tasktraker.tasks;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

public class Task {
    private String taskName;
    private String taskDescription;
    private TaskStatus status = TaskStatus.NEW;
    private  TaskType type;
    private int id;
    private Duration duration;
    private LocalDateTime startTime;

    Task(String taskName, String taskDescription) {
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.type = TaskType.TASK;
    }

    public Task(String taskName, String taskDescription, TaskStatus status) {
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.status = status;
        this.type = TaskType.TASK;
    }

    public Task(String taskName, String taskDescription, TaskStatus status, LocalDateTime startTime, Duration duration) {
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.status = status;
        this.startTime = startTime;
        this.duration = duration;
        this.type = TaskType.TASK;
    }

    public LocalDateTime getEndTime() {
        if (startTime == null) {
            return null;
        }
        return startTime.plus(duration);
    }

    public TaskType getType() {
        return type;
    }

    public void setType(TaskType type) {
        this.type = type;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
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
                taskName + "," + status + "," + taskDescription + "," + ";" + startTime + "," + duration + "," + getEndTime();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Task)) return false;
        Task task = (Task) o;
        return id == task.id && taskName.equals(task.taskName) && taskDescription.equals(task.taskDescription) && status == task.status && type == task.type && Objects.equals(duration, task.duration) && Objects.equals(startTime, task.startTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(taskName, taskDescription, status, type, id, duration, startTime);
    }
}