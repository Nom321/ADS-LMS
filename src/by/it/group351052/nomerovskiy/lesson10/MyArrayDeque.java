package by.it.group351052.nomerovskiy.lesson10;

import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class MyArrayDeque<E> implements Deque<E> {
    private E[] array;
    private int size;
    private int head;
    private int tail;

    @SuppressWarnings("unchecked")
    public MyArrayDeque(int initialCapacity) {
        array = (E[]) new Object[initialCapacity];
        size = 0;
        head = 0;
        tail = 0;
    }

    public MyArrayDeque() {
        this(10);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < size; i++) {
            sb.append(array[(head + i) % array.length]);
            if (i < size - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Iterator<E> iterator() {
        return null;
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return null;
    }

    @Override
    public Iterator<E> descendingIterator() {
        return null;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean add(E element) {
        addLast(element);
        return true;
    }

    @Override
    public boolean offer(E e) {
        return false;
    }

    @Override
    public E remove() {
        return null;
    }

    @Override
    public void addFirst(E element) {
        if (size == array.length) {
            resize();
        }
        head = (head - 1 + array.length) % array.length;
        array[head] = element;
        size++;
    }

    @Override
    public void addLast(E element) {
        if (size == array.length) {
            resize();
        }
        array[tail] = element;
        tail = (tail + 1) % array.length;
        size++;
    }

    @Override
    public boolean offerFirst(E e) {
        return false;
    }

    @Override
    public boolean offerLast(E e) {
        return false;
    }

    @Override
    public E removeFirst() {
        return null;
    }

    @Override
    public E removeLast() {
        return null;
    }

    @Override
    public E element() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        return array[head];
    }

    @Override
    public E peek() {
        return null;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {
    }

    @Override
    public void push(E e) {
    }

    @Override
    public E pop() {
        return null;
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean contains(Object o) {
        return false;
    }

    @Override
    public E getFirst() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        return array[head];
    }

    @Override
    public E getLast() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        return array[(tail - 1 + array.length) % array.length];
    }

    @Override
    public E peekFirst() {
        return null;
    }

    @Override
    public E peekLast() {
        return null;
    }

    @Override
    public boolean removeFirstOccurrence(Object o) {
        return false;
    }

    @Override
    public boolean removeLastOccurrence(Object o) {
        return false;
    }

    @Override
    public E poll() {
        return pollFirst();
    }

    @Override
    public E pollFirst() {
        if (size == 0) {
            return null;
        }
        E element = array[head];
        array[head] = null;
        head = (head + 1) % array.length;
        size--;
        return element;
    }

    @Override
    public E pollLast() {
        if (size == 0) {
            return null;
        }
        tail = (tail - 1 + array.length) % array.length;
        E element = array[tail];
        array[tail] = null;
        size--;
        return element;
    }

    @SuppressWarnings("unchecked")
    private void resize() {
        E[] newArray = (E[]) new Object[array.length * 2];
        for (int i = 0; i < size; i++) {
            newArray[i] = array[(head + i) % array.length];
        }
        array = newArray;
        head = 0;
        tail = size;
    }
}
