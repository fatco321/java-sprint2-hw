package ru.yandex.practicum.tasktraker.tasks;

public class Subtask extends Task {
    private Epic epic;

    public Subtask(String taskName, String taskDescription) {
        super(taskName, taskDescription);
    }

    public Epic getEpic() {
        return epic;
    }

    public void setEpic(Epic epic) {
        this.epic = epic;
    }
}
