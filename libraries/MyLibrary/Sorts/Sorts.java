package MyLibrary.Sorts;

import java.util.Comparator;

import MyLibrary.FnA1sz.FnA1sz;
import MyLibrary.FnList.FnList;
import MyLibrary.LnList.LnList;

public class Sorts {

    /* FnA1sz sorting: returns a new sorted FnA1sz */

    public static <T> FnA1sz<T> insertionSort(FnA1sz<T> arr, Comparator<T> cmp) {
        T[] xs = copyArray(arr);

        for (int i = 1; i < xs.length; i++) {
            T key = xs[i];
            int j = i - 1;

            while (j >= 0 && cmp.compare(xs[j], key) > 0) {
                xs[j + 1] = xs[j];
                j--;
            }

            xs[j + 1] = key;
        }

        return new FnA1sz<T>(xs);
    }

    public static <T> FnA1sz<T> selectionSort(FnA1sz<T> arr, Comparator<T> cmp) {
        T[] xs = copyArray(arr);

        for (int i = 0; i < xs.length; i++) {
            int min = i;

            for (int j = i + 1; j < xs.length; j++) {
                if (cmp.compare(xs[j], xs[min]) < 0) {
                    min = j;
                }
            }

            swap(xs, i, min);
        }

        return new FnA1sz<T>(xs);
    }

    public static <T> FnA1sz<T> quickSort(FnA1sz<T> arr, Comparator<T> cmp) {
        T[] xs = copyArray(arr);
        quickSortArray(xs, 0, xs.length - 1, cmp);
        return new FnA1sz<T>(xs);
    }

    public static <T> FnA1sz<T> mergeSort(FnA1sz<T> arr, Comparator<T> cmp) {
        T[] xs = copyArray(arr);

        T[] aux = (T[]) new Object[xs.length];

        mergeSortArray(xs, aux, 0, xs.length - 1, cmp);

        return new FnA1sz<T>(xs);
    }

    public static <T> FnA1sz<T> heapSort(FnA1sz<T> arr, Comparator<T> cmp) {
        T[] xs = copyArray(arr);
        int n = xs.length;

        for (int i = n / 2 - 1; i >= 0; i--) {
            sink(xs, i, n, cmp);
        }

        for (int end = n - 1; end > 0; end--) {
            swap(xs, 0, end);
            sink(xs, 0, end, cmp);
        }

        return new FnA1sz<T>(xs);
    }

    private static <T> T[] copyArray(FnA1sz<T> arr) {
        T[] xs = (T[]) new Object[arr.length()];

        for (int i = 0; i < arr.length(); i++) {
            xs[i] = arr.getAt(i);
        }

        return xs;
    }

    private static <T> void swap(T[] xs, int i, int j) {
        T temp = xs[i];
        xs[i] = xs[j];
        xs[j] = temp;
    }

    private static <T> void quickSortArray(T[] xs, int lo, int hi, Comparator<T> cmp) {
        if (lo >= hi) {
            return;
        }

        int p = partition(xs, lo, hi, cmp);
        quickSortArray(xs, lo, p - 1, cmp);
        quickSortArray(xs, p + 1, hi, cmp);
    }

    private static <T> int partition(T[] xs, int lo, int hi, Comparator<T> cmp) {
        T pivot = xs[hi];
        int i = lo;

        for (int j = lo; j < hi; j++) {
            if (cmp.compare(xs[j], pivot) <= 0) {
                swap(xs, i, j);
                i++;
            }
        }

        swap(xs, i, hi);
        return i;
    }

    private static <T> void mergeSortArray(T[] xs, T[] aux, int lo, int hi, Comparator<T> cmp) {
        if (lo >= hi) {
            return;
        }

        int mid = lo + (hi - lo) / 2;

        mergeSortArray(xs, aux, lo, mid, cmp);
        mergeSortArray(xs, aux, mid + 1, hi, cmp);
        merge(xs, aux, lo, mid, hi, cmp);
    }

    private static <T> void merge(T[] xs, T[] aux, int lo, int mid, int hi, Comparator<T> cmp) {
        for (int i = lo; i <= hi; i++) {
            aux[i] = xs[i];
        }

        int i = lo;
        int j = mid + 1;
        int k = lo;

        while (i <= mid && j <= hi) {
            if (cmp.compare(aux[i], aux[j]) <= 0) {
                xs[k] = aux[i];
                i++;
            } else {
                xs[k] = aux[j];
                j++;
            }

            k++;
        }

        while (i <= mid) {
            xs[k] = aux[i];
            i++;
            k++;
        }
    }

    private static <T> void sink(T[] xs, int i, int n, Comparator<T> cmp) {
        while (true) {
            int left = 2 * i + 1;
            int right = 2 * i + 2;
            int largest = i;

            if (left < n && cmp.compare(xs[left], xs[largest]) > 0) {
                largest = left;
            }

            if (right < n && cmp.compare(xs[right], xs[largest]) > 0) {
                largest = right;
            }

            if (largest == i) {
                break;
            }

            swap(xs, i, largest);
            i = largest;
        }
    }

    /* FnList sorting: returns a new sorted FnList */

    public static <T> FnList<T> insertionSort(FnList<T> list, Comparator<T> cmp) {
        FnList<T> sorted = new FnList<T>();
        FnList<T> xs = list;

        while (!xs.nilq()) {
            sorted = insertSorted(sorted, xs.hd(), cmp);
            xs = xs.tl();
        }

        return sorted;
    }

    private static <T> FnList<T> insertSorted(FnList<T> list, T item, Comparator<T> cmp) {
        if (list.nilq() || cmp.compare(item, list.hd()) <= 0) {
            return new FnList<T>(item, list);
        }

        return new FnList<T>(list.hd(), insertSorted(list.tl(), item, cmp));
    }

    public static <T> FnList<T> mergeSort(FnList<T> list, Comparator<T> cmp) {
        if (list.length() <= 1) {
            return list;
        }

        int mid = list.length() / 2;

        FnList<T> left = new FnList<T>();
        FnList<T> right = new FnList<T>();

        int index = 0;
        FnList<T> xs = list;

        while (!xs.nilq()) {
            if (index < mid) {
                left = new FnList<T>(xs.hd(), left);
            } else {
                right = new FnList<T>(xs.hd(), right);
            }

            index++;
            xs = xs.tl();
        }

        left = left.reverse();
        right = right.reverse();

        return mergeFnList(mergeSort(left, cmp), mergeSort(right, cmp), cmp);
    }

    private static <T> FnList<T> mergeFnList(FnList<T> a, FnList<T> b, Comparator<T> cmp) {
        if (a.nilq()) {
            return b;
        }

        if (b.nilq()) {
            return a;
        }

        if (cmp.compare(a.hd(), b.hd()) <= 0) {
            return new FnList<T>(a.hd(), mergeFnList(a.tl(), b, cmp));
        } else {
            return new FnList<T>(b.hd(), mergeFnList(a, b.tl(), cmp));
        }
    }

    /* LnList sorting */
 
    public static <T> LnList<T> insertionSort(LnList<T> list, Comparator<T> cmp) {
        FnList<T> sorted = insertionSort(lnListToFnList(list), cmp);
        return new LnList<T>(sorted);
    }

    public static <T> LnList<T> mergeSort(LnList<T> list, Comparator<T> cmp) {
        FnList<T> sorted = mergeSort(lnListToFnList(list), cmp);
        return new LnList<T>(sorted);
    }

    /*
     * Mutating version for LnList
     */
    public static <T> void insertionSort1(LnList<T> list, Comparator<T> cmp) {
        LnList<T> sorted = insertionSort(list, cmp);
        list.free();
        list.append1(sorted);
    }

    /*
     * Mutating version for LnList
     */
    public static <T> void mergeSort1(LnList<T> list, Comparator<T> cmp) {
        LnList<T> sorted = mergeSort(list, cmp);
        list.free();
        list.append1(sorted);
    }

    private static <T> FnList<T> lnListToFnList(LnList<T> list) {
        FnList<T> result = new FnList<T>();
        LnList<T> xs = list;

        while (!xs.nilq1()) {
            result = new FnList<T>(xs.hd1(), result);
            xs = xs.tl1();
        }

        return result.reverse();
    }
}
