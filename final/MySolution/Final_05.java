/*
// HX: 50 points for Final_05
// HX: This one tests your priority queue implementation
*/


import MyLibrary.LnList.*;
import MyLibrary.FnList.*;


import java.util.function.ToIntBiFunction;

public class Final_05 {

    public static<T> LnList<T>
	LnList_n$way$merge(LnList<T> xss[], ToIntBiFunction<T,T> cmp) {
	// HX: Given an array of (linear) lists (LnList), each of which is
	// ordered according to cmp, please implement a function to merge them
	// into one ordered (linear) list. Please note that you cannot create
	// new list nodes; you can only use existing nodes to form the returned
	// linear list. You are asked to use MyPQueueArray.java implemented in
	// Assigment#9 for finding the minimum of a collection of nodes.
	        
		// priority queue stores one current head from each list
        MyPQueueArray<PQItem<T>> pq =
            new MyPQueueArray<PQItem<T>>(xss.length + 1);

        // put the first item of each nonempty list into the priority queue
        for (int i = 0; i < xss.length; i += 1) {
            if (xss[i] != null && !xss[i].nilq1()) {
                pq.enque$raw(new PQItem<T>(xss[i].hd1(), i, cmp));
            }
        }

        // result list and pointer to last node
        LnList<T> result = new LnList<T>();
        LnList<T> tail = null;

        // keep taking the smallest current item
        while (!pq.isEmpty()) {
            PQItem<T> item = pq.deque$raw();
            int index = item.index;

            // take the head node from xss[index]
            LnList<T> oneNode = xss[index];

            // detach the head node from the rest of list
            LnList<T> rest = oneNode.unlink1();
            xss[index] = rest;

            // add this existing node to  result list
            if (result.nilq1()) {
                result = oneNode;
                tail = oneNode;
            } else {
                tail.link1(oneNode);
                tail = oneNode;
            }

            // if source list still has more nodes enqueue its new head
            if (!rest.nilq1()) {
                pq.enque$raw(new PQItem<T>(rest.hd1(), index, cmp));
            }
        }

        return result;
    }

    public static<T>
	FnList<T>
	LnList_mergeSort$100way(LnList<T> xs, ToIntBiFunction<T,T> cmp) {
	// HX: Please use LnList_n$way$merge to implement 100-way mergesort
	// on a linear list. That is, split each list evenly into 100 sublists;
	// recursely sort the 100 sublist and then use LnList_n$way$merge to merge
	// them into one sorted list.
	// Please make sure that your implementation of LnList_mergeSort$100way
	// does stable sorting!
	        
		// sort the linear list using the helper that returns LnList
        LnList<T> sorted = mergeSort100wayLn(xs, cmp);

        // convert the sorted LnList into FnList
        return lnListToFnList(sorted);
    }

	private static <T>
    LnList<T>
    mergeSort100wayLn(LnList<T> xs, ToIntBiFunction<T,T> cmp) {
        int n = xs.length1();

        // a list of length 0 or 1 is already sorted
        if (n <= 1) {
            return xs;
        }

        // split xs into 100 sublists
        LnList<T>[] parts = split100(xs, n);

        // recursively sort each nonempty sublist
        for (int i = 0; i < parts.length; i += 1) {
            if (!parts[i].nilq1()) {
                parts[i] = mergeSort100wayLn(parts[i], cmp);
            }
        }

        // merge the 100 sorted sublists
        return LnList_n$way$merge(parts, cmp);
    }

	private static <T>
    LnList<T>[]
    split100(LnList<T> xs, int n) {
    
        LnList<T>[] parts = (LnList<T>[])(new LnList[100]);

        int base = n / 100;
        int extra = n % 100;

        LnList<T> rest = xs;

        for (int i = 0; i < 100; i += 1) {
            // split evenly as possible
            int len = base;

            if (i < extra) {
                len += 1;
            }

            // empty part
            if (len == 0) {
                parts[i] = new LnList<T>();
            }
            // nonempty part
            else {
                parts[i] = rest;

                // move to the last node of this part
                LnList<T> cur = parts[i];

                for (int j = 1; j < len; j += 1) {
                    cur = cur.tl1();
                }

                // detach this part from the rest of the list
                rest = cur.unlink1();
            }
        }

        return parts;
    }

	private static <T>
    FnList<T>
    lnListToFnList(LnList<T> xs) {
        FnList<T> result = new FnList<T>();

        // build reversed FnList
        while (!xs.nilq1()) {
            result = new FnList<T>(xs.hd1(), result);
            xs = xs.tl1();
        }

        // reverse so order is correct
        return result.reverse();
    }

    /*
     * PQItem wraps one current list head
     *
     * MyPQueueArray is a maxheap but n way merge needs the minimum so compareTo is reversed
     * the smaller value gets higher priority
     */
    private static class PQItem<T> implements Comparable<PQItem<T>> {
        T value;
        int index;
        ToIntBiFunction<T,T> cmp;

        PQItem(T value, int index, ToIntBiFunction<T,T> cmp) {
            this.value = value;
            this.index = index;
            this.cmp = cmp;
        }

        public int compareTo(PQItem<T> other) {
            int c = cmp.applyAsInt(this.value, other.value);

            // smaller value should come out first
            if (c < 0) {
                return 1;
            }

            if (c > 0) {
                return -1;
            }

            // tiebreak by list index for stable merging
            if (this.index < other.index) {
                return 1;
            }

            if (this.index > other.index) {
                return -1;
            }

            return 0;
        }
    }

	private static FnList<Integer>
    buildFnListRange(int n) {
        FnList<Integer> result = new FnList<Integer>();

        for (int i = n - 1; i >= 0; i -= 1) {
            result = new FnList<Integer>(i, result);
        }

        return result;
    }

    private static void printFirstN(FnList<Integer> xs, int n) {
        int i = 0;

        while (i < n && !xs.nilq()) {
            System.out.print(xs.hd());

            if (i + 1 < n) {
                System.out.print(" ");
            }

            xs = xs.tl();
            i += 1;
        }

        System.out.println();
    }

    public static void main(String[] args) {
	// Please write some testing code that applies
	// mergeSort to parity-sort the list [0,1,2,...,999998,999999]
	// of 1000000 elements.
	       
		// build [0, 1, 2, ..., 999999]
        FnList<Integer> fxs = buildFnListRange(1000000);

        // convert to LnList
        LnList<Integer> xs = new LnList<Integer>(fxs);

        // parity comparator
        // even numbers come before odd numbers
        // numbers with the same parity compare equal so stable sorting matters
        ToIntBiFunction<Integer,Integer> parityCmp =
            (x, y) -> {
                int px = x % 2;
                int py = y % 2;

                if (px < py) {
                    return -1;
                }

                if (px > py) {
                    return 1;
                }

                return 0;
            };

        // sort by parity
        FnList<Integer> sorted =
            LnList_mergeSort$100way(xs, parityCmp);

        printFirstN(sorted, 40);
    }
}
