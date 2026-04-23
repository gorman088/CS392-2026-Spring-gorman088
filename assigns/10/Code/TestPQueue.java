public class TestPQueue {
    public static void main(String[] args) {
        MyPQueueArray<Integer> pq = new MyPQueueArray<Integer>(5);

        System.out.println("Empty? " + pq.isEmpty()); // true
        System.out.println("Top empty: " + pq.top$opt()); // null
        System.out.println("Dequeue empty: " + pq.deque$opt()); // null

        pq.enque$raw(5);
        pq.enque$raw(2);
        pq.enque$raw(9);
        pq.enque$raw(1);
        pq.enque$raw(7);

        System.out.println("Size: " + pq.size()); // 5
        System.out.println("Full? " + pq.isFull()); // true
        System.out.println("Try enqueue when full: " + pq.enque$opt(100)); // false

        System.out.println("Top: " + pq.top$raw()); // 9

        System.out.println(pq.deque$raw()); // 9
        System.out.println(pq.deque$raw()); // 7
        System.out.println(pq.deque$raw()); // 5
        System.out.println(pq.deque$raw()); // 2
        System.out.println(pq.deque$raw()); // 1

        System.out.println("Empty? " + pq.isEmpty()); // true
    }
}