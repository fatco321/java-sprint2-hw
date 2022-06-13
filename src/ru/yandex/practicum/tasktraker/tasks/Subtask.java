package ru.yandex.practicum.tasktraker.tasks;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

public class Subtask extends Task {
    private int epicId;

    public Subtask(String taskName, String taskDescription, TaskStatus status, int epicId) {
        super(taskName, taskDescription, status);
        super.setType(TaskType.SUBTASK);
        this.epicId = epicId;
    }

    public Subtask(String taskName, String taskDescription, TaskStatus status, int epicId,
                   LocalDateTime startTime, Duration duration) {
        super(taskName, taskDescription, status, startTime, duration);
        super.setType(TaskType.SUBTASK);
        this.epicId = epicId;
    }

    public int getEpicId() {
        return epicId;
    }

    public void setEpicId(int epicId) {
        this.epicId = epicId;
    }

    @Override
    public String toString() {
        return super.getId() + "," + TaskType.SUBTASK + "," + super.getTaskName() + "," +
                super.getStatus() + "," + super.getTaskDescription() + "," + "Epic_ID: " + epicId + "," +
                super.getStartTime() + "," + super.getDuration() + "," + super.getEndTime();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subtask subtask = (Subtask) o;
        return epicId == subtask.epicId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(epicId);
    }
}
