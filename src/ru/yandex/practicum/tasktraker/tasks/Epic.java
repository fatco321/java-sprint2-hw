package ru.yandex.practicum.tasktraker.tasks;

import static ru.yandex.practicum.tasktraker.tasks.TaskStatus.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;

public class Epic extends Task {
    private ArrayList<Subtask> sublist = new ArrayList<>();

    public Epic(String taskName, String taskDescription) {
        super(taskName, taskDescription);
        super.setType(TaskType.EPIC);
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
    public LocalDateTime getStartTime() {
        if (sublist.isEmpty()) {
            return null;
        }
        LocalDateTime startTime = LocalDateTime.MAX;
        for (Subtask subtask : sublist) {
            if (subtask.getStartTime() == null) {
                continue;
            }
            if (subtask.getStartTime() != null && startTime.isAfter(subtask.getStartTime())) {
                startTime = subtask.getStartTime();
            }
        }
        return startTime;
    }

    @Override
    public LocalDateTime getEndTime() {
        if (sublist.isEmpty()) {
            return null;
        }
        LocalDateTime endTime = LocalDateTime.MIN;
        for (Subtask subtask : sublist) {
            if (subtask.getEndTime() == null) {
                continue;
            }
            if (subtask.getEndTime() != null && endTime.isBefore(subtask.getEndTime())) {
                endTime = subtask.getEndTime();
            }
        }
        return endTime;
    }

    @Override
    public Duration getDuration() {
        if (sublist.isEmpty()) {
            return null;
        }
        Duration duration = Duration.ZERO;
        for (Subtask subtask : sublist) {
            if (subtask.getDuration() != null) {
                duration = duration.plus(subtask.getDuration());
            }
        }
        return duration;
    }

    @Override
    public String toString() {
        return super.getId() + "," + TaskType.EPIC + "," + super.getTaskName() + "," + super.getStatus() +
                "," + super.getTaskDescription() + "," + ";" + getStartTime() + "," +
                getDuration() + "," + getEndTime();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Epic)) return false;
        if (!super.equals(o)) return false;
        Epic epic = (Epic) o;
        return Objects.equals(sublist, epic.sublist);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), sublist);
    }
}