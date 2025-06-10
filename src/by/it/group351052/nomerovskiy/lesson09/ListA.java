package by.it.group351052.nomerovskiy.lesson09;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class ListA<E> implements List<E> {

    //Создайте аналог списка БЕЗ использования других классов СТАНДАРТНОЙ БИБЛИОТЕКИ

    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////
    //////               Обязательные к реализации методы             ///////
    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////

    private Object[] elements;
    private int size;
    private static final int INITIAL_CAPACITY = 10;

    public ListA() {
        elements = new Object[INITIAL_CAPACITY];
        size = 0;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < size; i++) {
            sb.append(elements[i]);
            if (i < size - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    @Override
    public boolean add(E e) {
        if (size == elements.length) {
            resize();
        }
        elements[size++] = e;
        return true;
    }

    @Override
    public E remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Индекс: " + index + ", Размер: " + size);
        }
        E removedElement = (E) elements[index];
        for (int i = index; i < size - 1; i++) {
            elements[i] = elements[i + 1];
        }
        elements[--size] = null;
        return removedElement;
    }

    @Override
    public int size() {
        return size;
    }

    private void resize() {
        int newCapacity = elements.length * 2;
        Object[] newArray = new Object[newCapacity];
        System.arraycopy(elements, 0, newArray, 0, elements.length);
        elements = newArray;
    }

    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////
    //////               Опциональные к реализации методы             ///////
    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////

    @Override
    public void add(int index, E element) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Индекс: " + index + ", Размер: " + size);
        }
        if (size == elements.length) {
            resize();
        }
        System.arraycopy(elements, index, elements, index + 1, size - index);
        elements[index] = element;
        size++;
    }

    @Override
    public boolean remove(Object o) {
        for (int i = 0; i < size; i++) {
            if (o.equals(elements[i])) {
                remove(i);
                return true;
            }
        }
        return false;
    }

    @Override
    public E set(int index, E element) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Индекс: " + index + ", Размер: " + size);
        }
        E oldElement = (E) elements[index];
        elements[index] = element;
        return oldElement;
    }


    @Override
    public boolean isEmpty() {
        return size == 0;
    }


    @Override
    public void clear() {
        for (int i = 0; i < size; i++) {
            elements[i] = null;
        }
        size = 0;
    }

    @Override
    public int indexOf(Object o) {
        for (int i = 0; i < size; i++) {
            if (o.equals(elements[i])) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public E get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Индекс вне диапазона: " + index);
        }
        return (E) elements[index];
    }

    @Override
    public boolean contains(Object o) {
        for (int i = 0; i < size; i++) {
            if (o.equals(elements[i])) {
                return true;
            }
        }
        return false; 
    }

    @Override
    public int lastIndexOf(Object o) {
        for (int i = size - 1; i >= 0; i--) {
            if (o.equals(elements[i])) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object item : c) {
            if (!contains(item)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        return addAll(size, c);
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Индекс вне диапазона: " + index);
        }
    
        boolean modified = false;
        for (E element : c) {
            add(index++, element);
            modified = true;
        }
        return modified; 
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean modified = false;
        Iterator<E> iterator = iterator();
        while (iterator.hasNext()) {
            if (c.contains(iterator.next())) {
                iterator.remove();
                modified = true;
            }
        }
        return modified; 
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean modified = false;
        Iterator<E> iterator = iterator();
        
        while (iterator.hasNext()) {
            if (!c.contains(iterator.next())) {
                iterator.remove();
                modified = true;
            }
        }
        return modified;
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        if (fromIndex < 0 || toIndex > size() || fromIndex > toIndex) {
            throw new IndexOutOfBoundsException("Индексы находятся вне диапазона: " + fromIndex + " - " + toIndex);
        }
        List<E> sublist = new ArrayList<>();
        for (int i = fromIndex; i < toIndex; i++) {
            sublist.add(get(i));
        }
        return sublist;
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        if (index < 0 || index > size()) {
            throw new IndexOutOfBoundsException("Индекс вне диапазона: " + index);
        }
        return new ListIterator<E>() {
            private int cursor = index;
            private int lastReturned = -1;
            @Override
            public boolean hasNext() {
                return cursor < size();
            }
            @Override
            public E next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                lastReturned = cursor++;
                return get(lastReturned);
            }
            @Override
            public boolean hasPrevious() {
                return cursor > 0;
            }
            @Override
            public E previous() {
                if (!hasPrevious()) {
                    throw new NoSuchElementException();
                }
                lastReturned = --cursor;
                return get(lastReturned);
            }
            @Override
            public int nextIndex() {
                return cursor;
            }
            @Override
            public int previousIndex() {
                return cursor - 1;
            }
            @Override
            public void remove() {
                if (lastReturned < 0) {
                    throw new IllegalStateException();
                }
                ListA.this.remove(lastReturned);
                cursor = lastReturned;
                lastReturned = -1;
            }
            @Override
            public void set(E e) {
                if (lastReturned < 0) {
                    throw new IllegalStateException();
                }
                ListA.this.set(lastReturned, e);
            }
            @Override
            public void add(E e) {
                ListA.this.add(cursor++, e);
                lastReturned = -1;
            }
        };
    }

    @Override
    public ListIterator<E> listIterator() {
        return listIterator(0);
    }

    @Override
    public <T> T[] toArray(T[] a) {
        if (a.length < size()) {
            return (T[]) Arrays.copyOf(toArray(), size(), a.getClass());
        }
        System.arraycopy(toArray(), 0, a, 0, size());
        if (a.length > size()) {
            a[size()] = null;
        }
        return a;
    }

    @Override
    public Object[] toArray() {
        return toArray(new Object[0]);
    }

    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////
    ////////        Эти методы имплементировать необязательно    ////////////
    ////////        но они будут нужны для корректной отладки    ////////////
    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////
    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private int cursor = 0;
            @Override
            public boolean hasNext() {
                return cursor < size();
            }
            @Override
            public E next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return get(cursor++);
            }
            @Override
            public void remove() {
                if (cursor <= 0) {
                    throw new IllegalStateException("Нет элементов для удаления");
                }
                ListA.this.remove(--cursor);
            }
        };
    }

}
