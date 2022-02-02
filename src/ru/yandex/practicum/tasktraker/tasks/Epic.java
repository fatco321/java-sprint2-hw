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

    public void setSublist(Subtask subtask) {
        sublist.add(subtask);
    }

    @Override
    public void setStatus() {
        int i = 0;
        int j = 0;
        for (Subtask subtask : sublist) {
            if (subtask.getTaskStatus().equals("IN_PROGRESS")) {
                setTaskStatus("IN_PROGRESS");
            }
            if (subtask.getTaskStatus().equals("DONE")) {
                i++;
            }
            if (subtask.getTaskStatus().equals("NEW")) {
                j++;
            }
        }
        if (i == sublist.size() && !sublist.isEmpty()) {
            setTaskStatus("DONE");
        } else if (i > 0 && i < sublist.size()) {
            setTaskStatus("IN_PROGRESS");
        } else if (sublist.isEmpty() || j == sublist.size()) {
            setTaskStatus("NEW");
        }
    }
}
