//
// HX-2026-04-28: 50 points
// A partial implementation of
// randomized doubly linked binary search tree
// 30 points for reroot and 20 points for insert
//
public class Quiz02_06 {
    Node root = null;
    public class Node {
	int key; // key stored in the node
	int size; // size of the tree rooted as the node
	Node parent; // parent of the node
	Node lchild; // left-child of the node
	Node rchild; // right-child of the node
    }

	private int size(Node n) {
        if (n == null) {
            return 0;
        }
        return n.size;
    }

	    private Node makeNode(int key, Node parent) {
        Node n = new Node();
        n.key = key;
        n.size = 1;
        n.parent = parent;
        n.lchild = null;
        n.rchild = null;
        return n;
    }

    private void updateSize(Node n) {
        if (n != null) {
            n.size = 1 + size(n.lchild) + size(n.rchild);
        }
    }

    private void rotateRight(Node y) {
        Node x = y.lchild;
        Node beta = x.rchild;
        Node p = y.parent;

        x.parent = p;

        if (p == null) {
            root = x;
        } else if (p.lchild == y) {
            p.lchild = x;
        } else {
            p.rchild = x;
        }

        x.rchild = y;
        y.parent = x;

        y.lchild = beta;
        if (beta != null) {
            beta.parent = y;
        }

        updateSize(y);
        updateSize(x);
    }

    private void rotateLeft(Node x) {
        Node y = x.rchild;
        Node beta = y.lchild;
        Node p = x.parent;

        y.parent = p;

        if (p == null) {
            root = y;
        } else if (p.lchild == x) {
            p.lchild = y;
        } else {
            p.rchild = y;
        }

        y.lchild = x;
        x.parent = y;

        x.rchild = beta;
        if (beta != null) {
            beta.parent = x;
        }

        updateSize(x);
        updateSize(y);
    }

    private Node randomNode() {
        java.util.Random rand = new java.util.Random();
        int r = rand.nextInt(root.size);

        Node cur = root;

        while (cur != null) {
            int leftSize = size(cur.lchild);

            if (r < leftSize) {
                cur = cur.lchild;
            } else if (r == leftSize) {
                return cur;
            } else {
                r = r - leftSize - 1;
                cur = cur.rchild;
            }
        }

        return root;
    }


    public void reroot() {
	// HX-2025-11-20: 30 points
	// [reroot] picks a node RANDOMLY and
	// uses rotations to turn this picked node
	// into the root of a new binary search tree
	// (containing the same set of keys)
	    if (root == null) {
            return;
        }

        Node chosen = randomNode();

        while (chosen.parent != null) {
            Node p = chosen.parent;

            if (p.lchild == chosen) {
                rotateRight(p);
            } else {
                rotateLeft(p);
            }
        }
    }
    public boolean insert(int key) {
	// HX-2025-11-20: 20 points
	// If key is in the tree stored at [root],
	// [insert] does no nothing and just returns false
	// If key is not in the tree stored at [root],
	// the key is inserted as a leaf node and the new
	// tree is still a binary search tree and [insert]
	// returns true (to indicate insertion is done).
	    if (root == null) {
            root = makeNode(key, null);
            return true;
        }

        Node cur = root;
        Node parent = null;

        while (cur != null) {
            parent = cur;

            if (key < cur.key) {
                cur = cur.lchild;
            } else if (key > cur.key) {
                cur = cur.rchild;
            } else {
                return false;
            }
        }

        Node n = makeNode(key, parent);

        if (key < parent.key) {
            parent.lchild = n;
        } else {
            parent.rchild = n;
        }

        cur = parent;
        while (cur != null) {
            updateSize(cur);
            cur = cur.parent;
        }

        return true;
    }

	private void printInorder(Node n) {
        if (n == null) {
            return;
        }

        printInorder(n.lchild);
        System.out.print(n.key + " ");
        printInorder(n.rchild);
    }
	public static void main (String[] args) {
	// Please add minimal testing code for reroot()
	// Please add minimal testing code for insert()
	
	    Quiz02_06 tree = new Quiz02_06();

        tree.insert(5);
        tree.insert(2);
        tree.insert(8);
        tree.insert(1);
        tree.insert(3);
        tree.insert(7);
        tree.insert(9);

        System.out.println("Before reroot:");
        tree.printInorder(tree.root);
        System.out.println();
        System.out.println("Root = " + tree.root.key);

        tree.reroot();

        System.out.println("After reroot:");
        tree.printInorder(tree.root);
        System.out.println();
        System.out.println("Root = " + tree.root.key);

        System.out.println("Insert duplicate 5:");
        System.out.println(tree.insert(5)); // false

        System.out.println("Insert new key 6:");
        System.out.println(tree.insert(6)); // true

        tree.printInorder(tree.root);
        System.out.println();
    }
}
