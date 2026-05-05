import java.util.function.Consumer;
import java.util.function.BiConsumer;

public class MyPQueueArray<T extends Comparable<? super T>>
    extends MyPQueueBase<T> {

    private T[] heap;
    private int size;

    // make a priority queue with fixed capacity
    public MyPQueueArray(int capacity) {
        heap = (T[]) new Comparable[capacity];
        size = 0;
    }

    // default constructor
    public MyPQueueArray() {
        this(100);
    }

    // number of items currently in the queue
    public int size() {
        return size;
    }

    // queue is full when array is full
    public boolean isFull() {
        return size >= heap.length;
    }

    // top item is stored at index 0
    public T top$raw() {
        return heap[0];
    }

    // insert item and bubble it 
    public void enque$raw(T itm) {
        int i = size;
        heap[i] = itm;
        size++;

        while (i > 0) {
            int parent = (i - 1) / 2;

            // maxheap means larger values have higher priority
            if (heap[i].compareTo(heap[parent]) <= 0) {
                break;
            }

            swap(i, parent);
            i = parent;
        }
    }

    // remove top item bubble replacement down
    public T deque$raw() {
        T ans = heap[0];
        size--;

        // if there was only one item
        if (size == 0) {
            heap[0] = null;
            return ans;
        }

        // move last item to root
        heap[0] = heap[size];
        heap[size] = null;

        int i = 0;

        while (true) {
            int left = 2 * i + 1;
            int right = 2 * i + 2;

            // no children
            if (left >= size) {
                break;
            }

            // find larger child
            int biggerChild = left;
            if (right < size && heap[right].compareTo(heap[left]) > 0) {
                biggerChild = right;
            }

            // heap property is already good
            if (heap[i].compareTo(heap[biggerChild]) >= 0) {
                break;
            }

            swap(i, biggerChild);
            i = biggerChild;
        }

        return ans;
    }

    // helper method to swap two positions
    private void swap(int i, int j) {
        T temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }
}
