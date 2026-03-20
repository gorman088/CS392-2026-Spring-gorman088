//
// HX: 50 points
//
//import LnList.java;
public class Quiz01_04 {

	// inserts the one node list x into the sorted list ys in a stable way
    private static <T extends Comparable<T>>
    LnList<T> insert(LnList<T> ys, LnList<T> x) {

        // if ys is empty then return x
        if (ys.nilq1()) return x;

        // if x should go before the first node of ys then link x in front
        if (x.hd1().compareTo(ys.hd1()) < 0) {
            x.link(ys);
            return x;
        }

        // otherwise move through ys until find where x should be inserted
        LnList<T> cur = ys;
        while (cur.tl1().consq1() && cur.tl1().hd1().compareTo(x.hd1()) <= 0) {
            cur = cur.tl1();
        }

        // insert x after cur
        LnList<T> tail = cur.unlink();
        cur.link(x);
        x.link(tail);

        return ys;
    }


    public static
	<T extends Comparable<T>>
	LnList<T> LnListInsertSort(LnList<T> xs) {
	// HX-2025-10-12:
	// Please implement (stable) insertion sort on a
	// linked list (LnList).
	// Note that you are not allowed to modify the definition
	// of the LnList class. You can only use the public methods
	// provided by the LnList class; you cannot use any constructors
	// in LnList
	
	// empty list is already sorted
        if (xs.nilq1()) return xs;

        // start sorted list with first node
        LnList<T> sorted = xs;

        // rest of the list to be inserted one by one
        LnList<T> rest = xs.unlink();

        // insert each remaining node into sorted
        while (rest.consq1()) {
            LnList<T> one = rest;     // take first node from rest
            rest = rest.unlink();     // remove it from rest
            sorted = insert(sorted, one);
        }
        return sorted;
    }

    // small class used only for parity sorting test
    static class PInt implements Comparable<PInt> {
        int x;

        PInt(int x) {
            this.x = x;
        }

        public int compareTo(PInt other) {
            return (this.x % 2) - (other.x % 2);
        }

        public String toString() {
            return "" + x;
        }
    }

    public static void main (String[] args) {
	// HX-2026-03-04:
	// Here you can use constructors in LnList.
	// Please write minimal testing code for LnListInsertSort
	// 1. Please sort a nearly sorted list of 1M elements
	// 2. Please do parity-sorting to test that LnListInsertSort is stable
		
        // nearly sorted list of 1M elements
        LnList<Integer> big = new LnList<Integer>();

        // build 0, 1, 2, 999999 with one small swap
        for (int i = 999999; i >= 0; i--) {
            if (i == 500000) {
                big = new LnList<Integer>(500001, big);
            } else if (i == 500001) {
                big = new LnList<Integer>(500000, big);
            } else {
                big = new LnList<Integer>(i, big);
            }
        }

        // sort the nearly sorted list
        LnList<Integer> bigSorted = LnListInsertSort(big);

        // print first 10 elements to check result
        System.out.println("First 10 elements:");
        bigSorted.iforitm1((i, x) -> {
            if (i < 10) System.out.print(x + " ");
        });
        System.out.println();

        // parity sorting to test stability
        LnList<PInt> ps = new LnList<PInt>();

        // build list 0 1 2 3 4 5 6 7 8 9
        for (int i = 9; i >= 0; i--) {
            ps = new LnList<PInt>(new PInt(i), ps);
        }

        // print before sorting
        System.out.println("Before parity sort:");
        ps.foritm1(x -> System.out.print(x + " "));
        System.out.println();

        // sort by parity
        LnList<PInt> psSorted = LnListInsertSort(ps);

        // print after sorting
        System.out.println("After parity sort:");
        psSorted.foritm1(x -> System.out.print(x + " "));
        System.out.println();
    }
}
