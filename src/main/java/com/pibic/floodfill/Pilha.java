package com.pibic.floodfill;

import java.util.LinkedList;

public class Pilha<T> {
    private LinkedList<T> lista = new LinkedList<>();

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