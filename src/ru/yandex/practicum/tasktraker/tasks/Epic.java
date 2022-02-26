package ru.yandex.practicum.tasktraker.tasks;

import static ru.yandex.practicum.tasktraker.tasks.TaskStatus.*;

import java.util.ArrayList;

public class Epic extends Task {
    private ArrayList<Subtask> sublist = new ArrayList<>();

    public Epic(String taskName, String taskDescription) {
        super(taskName, taskDescription);
    }

    public ArrayList<Subtask> getSublist() {
        return sublist;
    }

    public void setSublist(ArrayList<Subtask> sublist) {
        this.sublist = sublist;
    }

    public void addSubtaskInList(Subtask subtask) {
        sublist.add(subtask);
    }

    public void setStatus() {
        int countDoneStatus = 0;
        int countNewStatus = 0;
        for (Subtask subtask : sublist) {
            if (subtask.getStatus().equals(IN_PROGRESS)) {
                setStatus(IN_PROGRESS);
            }
            if (subtask.getStatus().equals(DONE)) {
                countDoneStatus++;
            }
            if (subtask.getStatus().equals(NEW)) {
                countNewStatus++;
            }
        }
        if (countDoneStatus == sublist.size() && !sublist.isEmpty()) {
            setStatus(DONE);
        } else if (countDoneStatus > 0 && countDoneStatus < sublist.size()) {
            setStatus(IN_PROGRESS);
        } else if (sublist.isEmpty() || countNewStatus == sublist.size()) {
            setStatus(NEW);
        }
    }

    @Override
    public String toString() {
        return "Epic{" +
                "taskName='" + super.getTaskName() + '\'' +
                ", taskDescription='" + super.getTaskDescription() + '\'' +
                ", status=" + super.getStatus() +
                ", id=" + super.getId() + '\'' +
                ", sublist=" + sublist +
                '}';
    }
}