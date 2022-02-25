package ru.yandex.practicum.tasktraker.historic;

import ru.yandex.practicum.tasktraker.tasks.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryHistoryManager<E extends Task> implements HistoryManager {
    private final Map<Integer, Node<E>> nodesMap = new HashMap<>();
    private Node<E> first;
    private Node<E> last;

    @Override
    public void add(Task task) {
        if (nodesMap.containsKey(task.getId())) {
            remove(task.getId());
        }
        nodesMap.put(task.getId(), linkLast((E) task));
    }

    @Override
    public List<Task> getHistory() {
        return getTasks();
    }

    @Override
    public void remove(int id) {
        if (nodesMap.containsKey(id)) {
            removeNode(nodesMap.get(id));
            nodesMap.remove(id);
        }
    }

    private static class Node<E> {
        E item;
        Node<E> next;
        Node<E> prev;

        Node(Node<E> prev, E element, Node<E> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }

    private Node<E> linkLast(E e) {
        final Node<E> newLast = last;
        final Node<E> newNode = new Node<>(newLast, e, null);
        last = newNode;
        if (newLast == null)
            first = newNode;
        else
            newLast.next = newNode;
        return newNode;
    }

    private void removeNode(Node<E> node) {
        final Node<E> next = node.next;
        final Node<E> prev = node.prev;
        if (prev == null) {
            first = next;
        } else {
            prev.next = next;
            node.prev = null;
        }
        if (next == null) {
            last = prev;
        } else {
            next.prev = prev;
            node.next = null;
        }
        node.item = null;
    }

    private List<Task> getTasks() {
       final List<Task> history = new ArrayList<>();
        Node<E> taskNode = first;
        while (taskNode != null) {
            history.add(taskNode.item);
            taskNode = taskNode.next;
        }
        return history;
    }
}