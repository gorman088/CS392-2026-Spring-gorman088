

public class FnList<T> {
    private Node root;
    private class Node {
        T head;
        FnList<T> tail;
        Node(T x0, FnList<T> xs) {
            head = x0; tail = xs;
        }
    }

    // construct an empty list
    public FnList() {
        root = null;
    }

    // construct a non-empty list
    public FnList(T x0, FnList<T> xs) {
        root = new Node(x0, xs);
    }

    // is it empty?
    public boolean nilq() {
        return (root == null);
    }

    // is it not empty?
    public boolean consq() {
        return (root != null);
    }

    // return head of current list
    public T hd() {
        // hdraw
        return root.head;
    }

    // return rest of element
    public FnList<T> tl() {
        // tlraw
        return root.tail;
    }

    // length is O(n)
    public int length() {
        int res = 0;
        FnList<T> xs = this;
        while (true) {
            if (xs.nilq()) break;
            res += 1; xs = xs.tl();
        }
        return res;
    }
}
