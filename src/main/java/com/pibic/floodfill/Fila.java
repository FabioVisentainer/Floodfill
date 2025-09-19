package com.pibic.floodfill;

import java.util.LinkedList;

public class Fila<T> {
    private LinkedList<T> lista = new LinkedList<>();

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