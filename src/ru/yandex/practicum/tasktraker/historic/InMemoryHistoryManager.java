package ru.yandex.practicum.tasktraker.historic;

import ru.yandex.practicum.tasktraker.tasks.Task;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager<T> implements HistoryManager {
    private final List<Task> history = new ArrayList<>();
    private Node<T> head;
    private Node<T> tail;
    private int size = 0;

    @Override
    public void add(Task task) {
        addLast((T) task);
    }

    @Override
    public List<Task> getHistory() {
        return getTasks();
    }

    class Node<E> {
        public E data;
        public Node<E> next;
        public Node<E> prev;

        public Node(Node<E> prev, E data, Node<E> next) {
            this.data = data;
            this.next = next;
            this.prev = prev;
        }
    }

    public void addLast(T element) {
        final Node<T> oldTail = tail;
        final Node<T> newNode = new Node<>(tail, element, null);
        tail = newNode;
        if (oldTail == null)
            tail = newNode;
        else
            oldTail.prev = newNode;
        size++;
    }

    public List<Task> getTasks(){
        Node<T> node = tail;
        while (node != null){
            history.add((Task) node.data);
            node = node.next;
        }
        return history;
    }

}

