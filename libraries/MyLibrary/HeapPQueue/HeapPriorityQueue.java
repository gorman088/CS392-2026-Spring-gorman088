package MyLibrary.HeapPQueue;

import java.util.Comparator;

/**
 * Array-based max-heap priority queue.
 *
 * If cmp.compare(a, b) > 0, then a has higher priority than b.
 */
public class HeapPriorityQueue<T> {
    private T[] heap;
    private int size;
    private final Comparator<T> cmp;

    public HeapPriorityQueue(Comparator<T> cmp) {
        this.cmp = cmp;
        this.heap = (T[]) new Object[8];
        this.size = 0;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public T top() {
        if (isEmpty()) {
            return null;
        }

        return heap[0];
    }

    public void enqueue(T item) {
        if (size == heap.length) {
            resize(heap.length * 2);
        }

        heap[size] = item;
        swim(size);
        size++;
    }

    public T dequeue() {
        if (isEmpty()) {
            return null;
        }

        T result = heap[0];

        size--;
        swap(0, size);
        heap[size] = null;

        sink(0);

        if (heap.length > 8 && size > 0 && size == heap.length / 4) {
            resize(heap.length / 2);
        }

        return result;
    }

    private void swim(int child) {
        while (child > 0) {
            int parent = (child - 1) / 2;

            if (cmp.compare(heap[child], heap[parent]) <= 0) {
                break;
            }

            swap(child, parent);
            child = parent;
        }
    }

    private void sink(int parent) {
        while (true) {
            int left = 2 * parent + 1;
            int right = 2 * parent + 2;
            int best = parent;

            if (left < size && cmp.compare(heap[left], heap[best]) > 0) {
                best = left;
            }

            if (right < size && cmp.compare(heap[right], heap[best]) > 0) {
                best = right;
            }

            if (best == parent) {
                break;
            }

            swap(parent, best);
            parent = best;
        }
    }

    private void swap(int i, int j) {
        T temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }

    private void resize(int newCapacity) {
        T[] temp = (T[]) new Object[newCapacity];

        for (int i = 0; i < size; i++) {
            temp[i] = heap[i];
        }

        heap = temp;
    }
}
