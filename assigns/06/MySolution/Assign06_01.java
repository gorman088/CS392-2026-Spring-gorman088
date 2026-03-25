/*
 *  Array-based Quicksort
 */
import java.util.Random;
import java.util.function.Function;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.BiFunction;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.ToIntBiFunction;

public class Assign06_01 {
    public static <T> void arrayQuickSort(T[] A, ToIntBiFunction<T,T> cmp) {
	// Please implement standard array-based quickSort and make sure
	// that equal elements are properly handled. In particular, your
	// testing code should test your implementation on an array of 1M zeros!
		quickSort(A, 0, A.length - 1, cmp);
    }

	// Quicksort on the part A[lo..hi]
    private static <T> void quickSort(T[] A, int lo, int hi, ToIntBiFunction<T,T> cmp) {
        // base case 0 or 1 element
        if (lo >= hi) return;

        // choose first element as pivot
        T pivot = A[lo];

        // 3 way partition
        // A[lo..lt-1] < pivot
        // A[lt..i-1] = pivot
        // A[i..gt] unknown
        // A[gt+1..hi] > pivot
        int lt = lo;
        int i = lo;
        int gt = hi;

        // partition the array into three parts
        while (i <= gt) {
            int sgn = cmp.applyAsInt(A[i], pivot);

            if (sgn < 0) {
                // A[i] is smaller than pivot
                swap(A, lt, i);
                lt++;
                i++;
            } else if (sgn > 0) {
                // A[i] is bigger than pivot
                swap(A, i, gt);
                gt--;
            } else {
                // A[i] is equal to pivot
                i++;
            }
        }

        // recursively sort left and right parts
        quickSort(A, lo, lt - 1, cmp);
        quickSort(A, gt + 1, hi, cmp);
    }

    // swap A[i] and A[j]
    private static <T> void swap(T[] A, int i, int j) {
        T temp = A[i];
        A[i] = A[j];
        A[j] = temp;
    }

    public static void main(String[] args) {
        // small test
		Integer[] A1 = {5, 3, 8, 3, 1, 9, 3, 2};
		arrayQuickSort(A1, (x, y) -> x.compareTo(y));

		for (int i = 0; i < A1.length; i++) {
			System.out.print(A1[i] + " ");
		}
		System.out.println();

		// test on array of all zeros
		Integer[] A2 = new Integer[1000000];
		for (int i = 0; i < A2.length; i++) {
			A2[i] = 0;
		}

		arrayQuickSort(A2, (x, y) -> x.compareTo(y));

		System.out.println(A2[0] + " " + A2[A2.length - 1]);
    }

} // end of [public class Assign06_01{...}]


