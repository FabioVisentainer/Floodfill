package com.pibic.floodfill;

import java.util.LinkedList;

// LIFO
public class Pilha<T> {
    private final LinkedList<T> lista = new LinkedList<>();

    public void push(T item) {
        lista.addFirst(item);
    }

    public T pop() {
        return lista.removeFirst();
    }

    public boolean isEmpty() {
        return lista.isEmpty();
    }
}