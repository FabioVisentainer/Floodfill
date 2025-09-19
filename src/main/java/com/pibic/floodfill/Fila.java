package com.pibic.floodfill;

import java.util.LinkedList;

// FIFO
public class Fila<T> {
    private final LinkedList<T> lista = new LinkedList<>();

    public void enqueue(T item) {
        lista.addLast(item);
    }

    public T dequeue() {
        return lista.removeFirst();
    }

    public boolean isEmpty() {
        return lista.isEmpty();
    }
}