//
// HX: 50 bonus points
//
// This is just Quiz02_02.
//
// I know that many of you spent some time on
// this question. In case, you can solve this one,
// you receive 50 bonus points. If you cannot solve
// it, you do not lose any points.
//
// Here we revisit a question on quiz01 (Quiz01_03).
// Instead of sorting 10 elements without recursion,
// you are asked to insertion-sort up to 1 million
// elements without recursion. Note that loops are
// a special form of recursion and thus are not allowed
// here.
//
// Attention:
// You are suppose to do insertion-sort. If you do
// bubble-sort, you can receive up to 60%, that is
// 30 points of 50.
//
public class Final_06 {

	// small interface for a unit of work
    private interface Work {
        void run();
    }

    // these repeat helpers use no loops and no recursion
    // each function calls a smaller function twice
    private static void repeat1(Work w) { w.run(); }
    private static void repeat2(Work w) { repeat1(w); repeat1(w); }
    private static void repeat4(Work w) { repeat2(w); repeat2(w); }
    private static void repeat8(Work w) { repeat4(w); repeat4(w); }
    private static void repeat16(Work w) { repeat8(w); repeat8(w); }
    private static void repeat32(Work w) { repeat16(w); repeat16(w); }
    private static void repeat64(Work w) { repeat32(w); repeat32(w); }
    private static void repeat128(Work w) { repeat64(w); repeat64(w); }
    private static void repeat256(Work w) { repeat128(w); repeat128(w); }
    private static void repeat512(Work w) { repeat256(w); repeat256(w); }
    private static void repeat1024(Work w) { repeat512(w); repeat512(w); }
    private static void repeat2048(Work w) { repeat1024(w); repeat1024(w); }
    private static void repeat4096(Work w) { repeat2048(w); repeat2048(w); }
    private static void repeat8192(Work w) { repeat4096(w); repeat4096(w); }
    private static void repeat16384(Work w) { repeat8192(w); repeat8192(w); }
    private static void repeat32768(Work w) { repeat16384(w); repeat16384(w); }
    private static void repeat65536(Work w) { repeat32768(w); repeat32768(w); }
    private static void repeat131072(Work w) { repeat65536(w); repeat65536(w); }
    private static void repeat262144(Work w) { repeat131072(w); repeat131072(w); }
    private static void repeat524288(Work w) { repeat262144(w); repeat262144(w); }

	// run work exactly n times for n up to 1 mil
    // this uses only if statements and no loops and no recursion
    private static void repeatUpTo1000000(int n, Work w) {
        if (n >= 524288) {
            repeat524288(w);
            n = n - 524288;
        }

        if (n >= 262144) {
            repeat262144(w);
            n = n - 262144;
        }

        if (n >= 131072) {
            repeat131072(w);
            n = n - 131072;
        }

        if (n >= 65536) {
            repeat65536(w);
            n = n - 65536;
        }

        if (n >= 32768) {
            repeat32768(w);
            n = n - 32768;
        }

        if (n >= 16384) {
            repeat16384(w);
            n = n - 16384;
        }

        if (n >= 8192) {
            repeat8192(w);
            n = n - 8192;
        }

        if (n >= 4096) {
            repeat4096(w);
            n = n - 4096;
        }

        if (n >= 2048) {
            repeat2048(w);
            n = n - 2048;
        }

        if (n >= 1024) {
            repeat1024(w);
            n = n - 1024;
        }

        if (n >= 512) {
            repeat512(w);
            n = n - 512;
        }

        if (n >= 256) {
            repeat256(w);
            n = n - 256;
        }

        if (n >= 128) {
            repeat128(w);
            n = n - 128;
        }

        if (n >= 64) {
            repeat64(w);
            n = n - 64;
        }

        if (n >= 32) {
            repeat32(w);
            n = n - 32;
        }

        if (n >= 16) {
            repeat16(w);
            n = n - 16;
        }

        if (n >= 8) {
            repeat8(w);
            n = n - 8;
        }

        if (n >= 4) {
            repeat4(w);
            n = n - 4;
        }

        if (n >= 2) {
            repeat2(w);
            n = n - 2;
        }

        if (n >= 1) {
            repeat1(w);
        }
    }

    public static
	<T extends Comparable<T>>
	void sort1000WithNoRecursion(T[] A) {
	// HX-2026-05-04:
	// A is an array of size at most 1000K.
	// Please implement a sorting algorithm
	// WITHOUT recursion that can effectively
	// sort A.

	    // i is stored in an array so it can be changed inside Work
        final int[] i = {1};

        // outer insertion sort pass:
        // i = 1, 2, ..., A.length - 1
        repeatUpTo1000000(A.length - 1, new Work() {
            public void run() {
                final T key = A[i[0]];
                final int[] j = {i[0] - 1};
                final boolean[] active = {true};

                // inner insertion step
                // shift larger elements to right
                repeatUpTo1000000(i[0], new Work() {
                    public void run() {
                        if (active[0] &&
                            j[0] >= 0 &&
                            A[j[0]].compareTo(key) > 0) {
                            A[j[0] + 1] = A[j[0]];
                            j[0] = j[0] - 1;
                        } else {
                            active[0] = false;
                        }
                    }
                });

                // place key into correct position
                A[j[0] + 1] = key;

                // move to the next element
                i[0] = i[0] + 1;
            }
        });
    }

	// print helper
    private static <T> void printArray(T[] A) {
        final int[] i = {0};

        repeatUpTo1000000(A.length, new Work() {
            public void run() {
                System.out.print(A[i[0]]);

                if (i[0] + 1 < A.length) {
                    System.out.print(" ");
                }

                i[0] = i[0] + 1;
            }
        });

        System.out.println();
    }

    public static void main (String[] args) {
	// HX-2025-11-19:
	// Please write minimal testing code for sort1000WithNoRecursion
	    Integer[] A = {5, 2, 9, 1, 3};

        sort1000WithNoRecursion(A);
        printArray(A);
        // Expect 1 2 3 5 9

        String[] B = {"m", "a", "z", "b", "y"};

        sort1000WithNoRecursion(B);
        printArray(B);
        // Expect a b m y z
    }
} // end of [public class Final_06{...}]
