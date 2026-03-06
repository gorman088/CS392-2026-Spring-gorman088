//
// HX-2026-03-04: 30 points
// This one may seem easy but can be time-consuming
// if you use a brute-force approach.
// Hint: Try to think about implementing bubble-sort
// without recursion
//
public class Quiz01_03 {
    // Compare a[i] and a[j] and swap if they are out of order
    private static <T extends Comparable<T>> void cs(T[] a, int i, int j) {
        if (a[i].compareTo(a[j]) > 0) {
            T temp = a[i];
            a[i] = a[j];
            a[j] = temp;
        }
    }

    // One full bubble-sort pass from left to right
    private static <T extends Comparable<T>> void pass(T[] a) {
        cs(a, 0, 1);
        cs(a, 1, 2);
        cs(a, 2, 3);
        cs(a, 3, 4);
        cs(a, 4, 5);
        cs(a, 5, 6);
        cs(a, 6, 7);
        cs(a, 7, 8);
        cs(a, 8, 9);
        cs(a, 9, 10);
        cs(a, 10, 11);
        cs(a, 11, 12);
        cs(a, 12, 13);
        cs(a, 13, 14);
        cs(a, 14, 15);
        cs(a, 15, 16);
        cs(a, 16, 17);
        cs(a, 17, 18);
        cs(a, 18, 19);
    }	
	
	
	
	public static
	<T extends Comparable<T>>
	T[] sort20WithNoRecursion
	(T x00, T x01, T x02, T x03, T x04, T x05, T x06, T x07, T x08, T x09,
	 T x10, T x11, T x12, T x13, T x14, T x15, T x16, T x17, T x18, T x19) {
	// HX-2026-03-03:
	// Given 30 arguments,
	// please return an array of size 20 containing the
	// 20 arguments sorted according to the order implemented by
	// compareTo on T.
	// HX: No recursion is allowed for this one
	// HX: No loops (either while-loop or for-loop) is allowed.
	// HX: Yes, you can use functions (but not recursive functions)
	// HX: Please do not try to write a HUGE if-then-else mumble jumble!
	
	// Put the 20 arguments into an array
        T[] a = (T[]) new Comparable[] {
            x00, x01, x02, x03, x04, x05, x06, x07, x08, x09,
            x10, x11, x12, x13, x14, x15, x16, x17, x18, x19
        };

        // Do 19 bubble sort passes
        pass(a);
        pass(a);
        pass(a);
        pass(a);
        pass(a);
        pass(a);
        pass(a);
        pass(a);
        pass(a);
        pass(a);
        pass(a);
        pass(a);
        pass(a);
        pass(a);
        pass(a);
        pass(a);
        pass(a);
        pass(a);
        pass(a);

        return a;
    }

	// print helper
	private static <T> void print20(T[] a) {
        System.out.println(
            a[0] + " " + a[1] + " " + a[2] + " " + a[3] + " " + a[4] + " " +
            a[5] + " " + a[6] + " " + a[7] + " " + a[8] + " " + a[9] + " " +
            a[10] + " " + a[11] + " " + a[12] + " " + a[13] + " " + a[14] + " " +
            a[15] + " " + a[16] + " " + a[17] + " " + a[18] + " " + a[19]
        );
    }

    public static void main (String[] args) {
	// HX-2025-10-12:
	// Please write minimal testing code for sort20WithNoRecursion.
		Comparable[] A = sort20WithNoRecursion(
            5, 1, 9, 2, 8, 3, 7, 4, 6, 0,
            15, 11, 19, 12, 18, 13, 17, 14, 16, 10
        );
        print20(A);

        Comparable[] B = sort20WithNoRecursion(
            "m", "a", "z", "b", "y", "c", "x", "d", "w", "e",
            "v", "f", "u", "g", "t", "h", "s", "i", "r", "j"
        );
        print20(B);
    }
}
