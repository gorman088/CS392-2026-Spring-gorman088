import MyLibrary.BST.BST;
import MyLibrary.HashMap.SeparateChainingHashMap;
import MyLibrary.HashMap.QuadraticProbingHashMap;
import MyLibrary.HeapPQueue.HeapPriorityQueue;
import MyLibrary.FnGtree.FnGtree;
import MyLibrary.Sorts.Sorts;
import MyLibrary.FnList.FnList;
import MyLibrary.LnList.LnList;
import MyLibrary.FnA1sz.FnA1sz;

public class TestDS {
    public static void main(String[] args) {
        System.out.println("BST");
        BST<Integer, String> bst = new BST<Integer, String>();
        bst.put(5, "five");
        bst.put(2, "two");
        bst.put(7, "seven");
        bst.put(1, "one");
        bst.put(3, "three");

        bst.keysInOrder().System$out$print1();
        System.out.println();

        System.out.println(bst.successor(3));

        bst.remove(2);
        bst.keysInOrder().System$out$print1();
        System.out.println();

        System.out.println("\nHashMaps");
        SeparateChainingHashMap<String, Integer> map1 =
            new SeparateChainingHashMap<String, Integer>();

        map1.put("a", 1);
        map1.put("b", 2);
        map1.put("a", 10);

        System.out.println(map1.get("a"));
        System.out.println(map1.remove("b"));

        QuadraticProbingHashMap<String, Integer> map2 =
            new QuadraticProbingHashMap<String, Integer>();

        map2.put("x", 24);
        map2.put("y", 25);

        System.out.println(map2.get("x"));
        System.out.println(map2.remove("x"));
        System.out.println(map2.get("x"));

        System.out.println("\nHeap Priority Queue");
        HeapPriorityQueue<Integer> pq =
            new HeapPriorityQueue<Integer>(Integer::compareTo);

        pq.enqueue(4);
        pq.enqueue(10);
        pq.enqueue(2);
        pq.enqueue(7);

        while (!pq.isEmpty()) {
            System.out.print(pq.dequeue() + " ");
        }

        System.out.println();

        System.out.println("\nSorts with FnA1sz");
        Integer[] raw = {9, 4, 6, 1, 7};
        FnA1sz<Integer> arr = new FnA1sz<Integer>(raw);

        FnA1sz<Integer> sortedArr =
            Sorts.quickSort(arr, Integer::compareTo);

        sortedArr.System$out$print();
        System.out.println();

        System.out.println("\nSorts with FnList");
        FnList<Integer> fn =
            new FnList<Integer>(
                3,
                new FnList<Integer>(
                    1,
                    new FnList<Integer>(
                        4,
                        new FnList<Integer>(
                            2,
                            new FnList<Integer>()
                        )
                    )
                )
            );

        Sorts.mergeSort(fn, Integer::compareTo).System$out$print();
        System.out.println();

        System.out.println("\n Sorts with LnList");
        LnList<Integer> ln = new LnList<Integer>(fn);

        Sorts.mergeSort1(ln, Integer::compareTo);

        ln.System$out$print1();
        System.out.println();
    }
}