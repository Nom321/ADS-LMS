package by.it.group351052.nomerovskiy.lesson09;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class ListC<E> implements List<E> {

    //Создайте аналог списка БЕЗ использования других классов СТАНДАРТНОЙ БИБЛИОТЕКИ

    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////
    //////               Обязательные к реализации методы             ///////
    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////

    private E[] elements;
    private int size;

    public ListC() {
        elements = (E[]) new Object[10];
        size = 0;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("[");
        for (int i = 0; i < size(); i++) {
            result.append(get(i));
            if (i < size() - 1) {
                result.append(", ");
            }
        }
        result.append("]");
        return result.toString();
    }

    @Override
    public boolean add(E e) {
        if (size == elements.length) {
            resize();
        }
        elements[size] = e;
        size++;
        return true;
    }

    @Override
    public E remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        E removedElement = elements[index];
        for (int i = index; i < size - 1; i++) {
            elements[i] = elements[i + 1];
        }
        elements[size - 1] = null;
        size--;
        return removedElement;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void add(int index, E element) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Индекс вне диапазона: " + index);
        }
        ensureCapacity(size + 1);
        for (int i = size; i > index; i--) {
            elements[i] = elements[i - 1];
        }
        elements[index] = element;
        size++;
    }

    @Override
    public boolean remove(Object o) {
        for (int i = 0; i < size; i++) {
            if (o.equals(elements[i])) {
                for (int j = i; j < size - 1; j++) {
                    elements[j] = elements[j + 1];
                }
                elements[size - 1] = null;
                size--;
                return true;
            }
        }
        return false;
    }

    @Override
    public E set(int index, E element) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Индекс вне диапазона: " + index);
        }
        E oldElement = elements[index];
        elements[index] = element;
        return oldElement;
    }


    @Override
    public boolean isEmpty() {
        return size == 0;
    }


    @Override
    public void clear() {
        for (int i = 0; i < elements.length; i++) {
            elements[i] = null;
        }
        size = 0;
    }

    @Override
    public int indexOf(Object o) {
        for (int i = 0; i < size; i++) {
            if (elements[i].equals(o)) {
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
        return elements[index];
    }

    @Override
    public boolean contains(Object o) {
        for (int i = 0; i < size; i++) {
            if (elements[i].equals(o)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int lastIndexOf(Object o) {
        for (int i = size - 1; i >= 0; i--) {
            if (elements[i].equals(o)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object o : c) {
            if (!contains(o)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        boolean isModified = false;
        for (E e : c) {
            if (add(e)) {
                isModified = true;
            }
        }
        return isModified;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        if (index < 0 || index > size()) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size());
        }
        boolean isModified = false;
        for (E e : c) {
            add(index++, e);
            isModified = true;
        }
        return isModified;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean isModified = false;
        for (Object o : c) {
            while (remove(o)) {
                isModified = true;
            }
        }
        return isModified;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean modified = false;
        Iterator<E> it = this.iterator();
        while (it.hasNext()) {
            E element = it.next();
            if (!c.contains(element)) {
                it.remove();
                modified = true;
            }
        }
        return modified;
    }

    private void ensureCapacity(int minCapacity) {
        if (minCapacity > elements.length) {
            int newCapacity = elements.length * 2;
            @SuppressWarnings("unchecked")
            E[] newArray = (E[]) new Object[newCapacity];
            System.arraycopy(elements, 0, newArray, 0, size);
            elements = newArray;
        }
    }

    private void resize() {
        E[] newArray = (E[]) new Object[elements.length * 2];
        for (int i = 0; i < size; i++) {
            newArray[i] = elements[i];
        }
        elements = newArray;
    }

    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////
    //////               Опциональные к реализации методы             ///////
    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        if (fromIndex < 0 || toIndex > size || fromIndex > toIndex) {
            throw new IndexOutOfBoundsException("Индексы выходят за пределы коллекции");
        }
        List<E> sublist = new ListB<>();
        for (int i = fromIndex; i < toIndex; i++) {
            sublist.add(get(i));
        }
        return sublist;
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Индекс вне диапазона: " + index);
        }
        return new ListIterator<E>() {
            private int cursor = index;
            private int lastReturned = -1;
            @Override
            public boolean hasNext() {
                return cursor < size;
            }
            @Override
            public E next() {
                if (!hasNext()) {
                    return null;
                }
                lastReturned = cursor;
                return (E) elements[cursor++];
            }
            @Override
            public boolean hasPrevious() {
                return cursor > 0;
            }
            @Override
            public E previous() {
                if (!hasPrevious()) {
                    return null;
                }
                lastReturned = --cursor;
                return (E) elements[cursor];
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
                ListC.this.remove(lastReturned);
                cursor = lastReturned;
                lastReturned = -1;
            }
            @Override
            public void set(E e) {
                if (lastReturned < 0) {
                    throw new IllegalStateException();
                }
                ListC.this.set(lastReturned, e);
            }
            @Override
            public void add(E e) {
                ListC.this.add(cursor++, e);
                lastReturned = -1;
            }
        };
    }

    @Override
    public ListIterator<E> listIterator() {
        return new ListIterator<E>() {
            private int cursor = 0;
            private int lastReturned = -1;
            @Override
            public boolean hasNext() {
                return cursor < size;
            }
            @Override
            public E next() {
                if (!hasNext()) {
                    return null;
                }
                lastReturned = cursor;
                return (E) elements[cursor++];
            }
            @Override
            public boolean hasPrevious() {
                return cursor > 0;
            }
            @Override
            public E previous() {
                if (!hasPrevious()) {
                    return null;
                }
                lastReturned = --cursor;
                return (E) elements[cursor];
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
                ListC.this.remove(lastReturned);
                cursor = lastReturned;
                lastReturned = -1;
            }

            @Override
            public void set(E e) {
                if (lastReturned < 0) {
                    throw new IllegalStateException();
                }
                ListC.this.set(lastReturned, e);
            }
            @Override
            public void add(E e) {
                ListC.this.add(cursor++, e);
                lastReturned = -1;
            }
        };
    }

    @Override
    public <T> T[] toArray(T[] a) {
        if (a.length < size) {
            @SuppressWarnings("unchecked")
            T[] newArray = (T[]) java.lang.reflect.Array.newInstance(a.getClass().getComponentType(), size);
            for (int i = 0; i < size; i++) {
                newArray[i] = (T) elements[i];
            }
            return newArray;
        }
        for (int i = 0; i < size; i++) {
            a[i] = (T) elements[i];
        }
        if (a.length > size) {
            a[size] = null;
        }

        return a;
    }

    @Override
    public Object[] toArray() {
        Object[] result = new Object[size];
        for (int i = 0; i < size; i++) {
            result[i] = elements[i];
        }
        return result;
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
            private boolean canRemove = false;
            @Override
            public boolean hasNext() {
                return cursor < size;
            }
            @Override
            public E next() {
                canRemove = true;
                return (E) elements[cursor++];
            }
            @Override
            public void remove() {
                if (!canRemove) {
                    throw new IllegalStateException("next() has not been called, or remove() was already called");
                }
                ListC.this.remove(--cursor);
                canRemove = false;
            }
        };
    }
}
