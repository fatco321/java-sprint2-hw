package ru.yandex.practicum.tasktraker.tasks;

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

    @Override
    public void setStatus() {
        int countDoneStatus = 0;
        int countNewStatus = 0;
        for (Subtask subtask : sublist) {
            if (subtask.getTaskStatus().equals("IN_PROGRESS")) {
                setTaskStatus("IN_PROGRESS");
            }
            if (subtask.getTaskStatus().equals("DONE")) {
                countDoneStatus++;
            }
            if (subtask.getTaskStatus().equals("NEW")) {
                countNewStatus++;
            }
        }
        if (countDoneStatus == sublist.size() && !sublist.isEmpty()) {
            setTaskStatus("DONE");
        } else if (countDoneStatus > 0 && countDoneStatus < sublist.size()) {
            setTaskStatus("IN_PROGRESS");
        } else if (sublist.isEmpty() || countNewStatus == sublist.size()) {
            setTaskStatus("NEW");
        }
    }
}
