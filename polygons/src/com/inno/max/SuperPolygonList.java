package com.inno.max;

import java.awt.*;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

class SuperPolygonList<T> implements List<T> {
    final static class Node<T> {
        public Node(T value) {
            this.value = value;
            next = null;
        }

        public Node<T> getNext() {
            return next;
        }

        public void setNext(Node<T> next) {
            this.next = next;
        }

        public T getValue() {
            return value;
        }

        public void setValue(T value) {
            this.value = value;
        }

        Node<T> next;
        T value;
    }

    final static class SuperIterator<T> implements java.util.Iterator {
        Node<T> iterator;
        boolean headGone = false;

        public SuperIterator(final Node<T> head) {
            this.iterator = head;
        }

        @Override
        public boolean hasNext() {
            return iterator != null && iterator.getNext() != null;
        }

        @Override
        public Object next() {
            if (iterator == null) {
                return null;
            }

            if (!headGone) {
                headGone = true;
            } else {
                iterator = iterator.getNext();
            }

            return iterator.getValue();
        }
    }

    Node<T> head;
    Node<T> tail;
    int size = 0;

    @Override
    public boolean contains(Object value) {
        if (head == null) {
            return false;
        }

        Node<T> iterator = head;

        do {
            if (iterator.getValue().equals(value)) {
                return true;
            }

            iterator = iterator.getNext();
        } while (iterator != null);

        return false;
    }

    @Override
    public boolean add(T value) {
        if (tail == null) {
            // The list is empty now
            head = new Node<T>(value);
            tail = head;
            size++;

            return true;
        }

        final Node<T> newTail = new Node<T>(value);
        tail.setNext(newTail);
        tail = newTail;
        size++;

        return true;
    }

    @Override
    public boolean remove(final Object value) {
        if (head == null) {
            return false;
        }

        if (head == tail && head.getValue().equals(value)) {
            head.setNext(null);
            tail = null;
            head = null;

            size = 0;
            return true;
        }

        Node<T> iterator = head;
        Node<T> prev = null;

        do {
            if (iterator.getValue().equals(value)) {
                if (iterator == tail) {
                    tail = prev;
                } else {
                    prev.setNext(iterator.getNext());
                    iterator.setNext(null);
                }

                size--;
                return true;
            }

            prev = iterator;
            iterator = iterator.getNext();
        } while (iterator != null);

        return false;
    }

    @Override
    public Iterator<T> iterator() {
        return new SuperIterator<T>(head);
    }

    @Override
    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }

    @Override
    public T get(final int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }

        if (head == null) {
            return null;
        }

        Node<T> iterator = head;

        for (int i = 0; iterator != null; i++, iterator = iterator.getNext()) {
            if (i < index) {
                continue;
            }

            return iterator.getValue();
        }

        return null;
    }

    @Override
    public void add(int index, T value) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }

        if (head == null) {
            return;
        }

        Node<T> iterator = head;
        Node<T> prev = null;

        for (int i = 0; iterator != null; i++, prev = iterator,
                iterator = iterator.getNext()) {
            if (i < index) {
                continue;
            }

            Node<T> newNode = new Node<T>(value);

            newNode.setNext(iterator);
            prev.setNext(newNode);

            size++;
            return;
        }
    }

    @Override
    public T remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }

        if (head == null) {
            return null;
        }

        Node<T> iterator = head;
        Node<T> prev = null;

        for (int i = 0; iterator != null; i++, prev = iterator,
                iterator = iterator.getNext()) {
            if (i < index) {
                continue;
            }

            T removedNode = iterator.getValue();

            if (prev == null) {
                Node<T> oldItem = iterator;

                head = iterator.getNext();

                oldItem.setNext(null);
            } else {
                prev.setNext(iterator.getNext());
                iterator.setNext(null);
            }

            size--;
            return removedNode;
        }

        return null;
    }

    @Override
    public T set(int index, T value) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }

        if (head == null) {
            return null;
        }

        Node<T> iterator = head;

        for (int i = 0; iterator != null; i++, iterator = iterator.getNext()) {
            if (i < index) {
                continue;
            }

            T oldValue = iterator.getValue();

            iterator.setValue(value);

            return oldValue;
        }

        return null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int indexOf(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int lastIndexOf(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ListIterator<T> listIterator() {
        throw new UnsupportedOperationException();
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object[] toArray() {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }
}
