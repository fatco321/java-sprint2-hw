package ru.yandex.practicum.tasktraker.tasks;

import java.util.Objects;

public class Subtask extends Task {
    private int epicId;

    public Subtask(String taskName, String taskDescription) {
        super(taskName, taskDescription);
    }

    public int getEpicId() {
        return epicId;
    }

    public void setEpicId(int epicId) {
        this.epicId = epicId;
    }

}
