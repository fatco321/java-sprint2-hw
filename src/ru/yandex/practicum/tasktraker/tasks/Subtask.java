package ru.yandex.practicum.tasktraker.tasks;

public class Subtask extends Task {
    private int epicId;

    public Subtask(String taskName, String taskDescription, String taskStatus, int epicId) {
        super(taskName, taskDescription, taskStatus);
        this.epicId = epicId;
    }

    public int getEpicId() {
        return epicId;
    }

    public void setEpicId(int epicId) {
        this.epicId = epicId;
    }

}
