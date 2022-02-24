package ru.yandex.practicum.tasktraker.historic;

import org.w3c.dom.Node;
import ru.yandex.practicum.tasktraker.tasks.Task;

import java.util.*;

public class InMemoryHistoryManager<E extends Task> implements HistoryManager {
    Map<Integer, Node<E>> map = new HashMap<>();
    public Node<E> first;
    public Node<E> last;
    private int size = 0;

    @Override
    public void add(Task task) {
        linkLast((E) task);
    }

    @Override
    public List<Task> getHistory() {
        return test();
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

    private void linkLast(E e) {
        final Node<E> l = last;
        final Node<E> newNode = new Node<>(l, e, null);
        last = newNode;
        if (l == null)
            first = newNode;
        else
            l.next = newNode;
        map.put(e.getId(), newNode);
        size++;
    }



    public List<Task> getTasks() {
        List<Task> history = new ArrayList<>();
        for (int key : map.keySet()) {
            history.add(map.get(key).item);
        }
        return history;
    }

    @Override
    public void remove(int id) {
        removeNode(map.get(id));
        map.remove(id);
    }

    private void removeNode(Node<E> node){
        /*node.item = node.next.item;
        node.next = node.prev.prev;*/
      /*  if(node.next == null) {
            final E element = node.item;
            final Node<E> prev = node.prev;
            node.item = null;
            node.prev = null; // help GC
            last = prev;
            if (prev == null)
                first = null;
            else
                prev.next = null;
            size--;
        }*/
        if(node.next == null || node.prev == null) {
            final E element = node.item;
            final Node<E> prev = node.prev;
            node.item = null;
            node.prev = null; // help GC
            last = prev;
            if (prev == null)
                first = node.next;
            else
                prev.next = node.next;
            size--;
        } else {
            node.item = node.next.item;
            node.next = node.prev.prev;
        }


    }

    public List<Task> test(){
        List<Task> history = new ArrayList<>();
        Node<E> taskNode = first;
        while (taskNode != null){
            history.add(taskNode.item);
            taskNode = taskNode.next;
        }
        return history;
    }
}


/* public List<Task> test(){
        List<Task> history = new ArrayList<>();
        Node<E> taskNode = first;
        while (taskNode != null){
            history.add(taskNode.item);
            taskNode = taskNode.next;
        }
        return history;
    }*/