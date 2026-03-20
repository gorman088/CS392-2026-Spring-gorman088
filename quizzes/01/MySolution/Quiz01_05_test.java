//
// HX: For testing Quiz01_05
//

import java.util.function.ToIntBiFunction;

import Library00.FnList.FnList;

public class Quiz01_05_test extends Quiz01_05 {

    public <T>
    FnList<T> someSort(FnList<T> xs, ToIntBiFunction<T,T> cmp) {
        return Assign05_01.insertSort(xs, cmp);
    }
    public static void main (String args[]) {
	// Your testing code for Quiz01_05
        Quiz01_05_test q = new Quiz01_05_test();

        // build the list 0, 1, 2, ..., 999
        FnList<Integer> xs = new FnList<Integer>();
        for (int i = 999; i >= 0; i--) {
            xs = new FnList<Integer>(i, xs);
        }

        // compare by parity only evens before odds
        ToIntBiFunction<Integer,Integer> parityCmp =
            (x, y) -> (x % 2) - (y % 2);

        FnList<Integer> ys = q.someRevStableSort(xs, parityCmp);

        // print first 40 elements
        System.out.println("First 40 elements after reverse stable parity sort:");
        FnList<Integer> cur = ys;
        int count = 0;
        while (!cur.nilq() && count < 40) {
            System.out.print(cur.hd() + " ");
            cur = cur.tl();
            count++;
        }
        System.out.println();

        // print last 10 elements too
        int[] last = new int[10];
        int n = 0;
        cur = ys;
        while (!cur.nilq()) {
            last[n % 10] = cur.hd();
            n++;
            cur = cur.tl();
        }

        System.out.println("Last 10 elements:");
        int start = n % 10;
        for (int i = 0; i < 10; i++) {
            System.out.print(last[(start + i) % 10] + " ");
        }
        System.out.println();
    }
}

