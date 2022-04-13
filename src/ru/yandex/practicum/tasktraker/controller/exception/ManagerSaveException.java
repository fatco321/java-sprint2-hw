package ru.yandex.practicum.tasktraker.controller.exception;

public class ManagerSaveException extends RuntimeException {
    public ManagerSaveException(String message) {
        super(message);
    }
}
