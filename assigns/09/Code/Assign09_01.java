//
// HX-2026-04-09: 50 points
// A partial implementation of
// randomized doubly linked binary search tree
// 20 points for insert and 30 points for remove
//
public class Assign09_01 {
    Node root = null;
    public class Node {
		int key; // key stored in the node
		int size; // size of the tree rooted as the node
		Node parent; // parent of the node
		Node lchild; // left-child of the node
		Node rchild; // right-child of the node

		// make a new node
		Node(int k, Node p) {
			key = k;
			size = 1;
			parent = p;
			lchild = null;
			rchild = null;
		}
    }

    public boolean insert(int key) {
	// HX-2026-04-09: 20 points
	// If key is in the tree stored at [root],
	// [insert] does nothing and just returns false.
	// If key is not in the tree stored at [root],
	// the key is inserted as a leaf node and the new
	// tree is still a binary search tree and [insert]
	// returns true (to indicate insertion is done).

		// if tree is empty then new node becomes root
		if (root == null) {
			root = new Node(key, null);
			return true;
		}

		Node cur = root;
		Node par = null;

		// search for where the new key should go
		while (cur != null) {
			par = cur;
			// dont insert duplicates
			if (key == cur.key) {
				return false;
			}

			//go left if key is smaller
			else if (key < cur.key) {
				cur = cur.lchild;
			}

			// got right if key is larger
			else {
				cur = cur.rchild;
			}
		}

		// create new leaf node
		Node n = new Node(key, par); 

		// connect it to its parent
		if (key < par.key) {
			par.lchild = n;
		} else {
			par.rchild = n;
		}

		// update size fields going back up tp root 
		while (par != null) {
            par.size = 1;
            if (par.lchild != null) par.size += par.lchild.size;
            if (par.rchild != null) par.size += par.rchild.size;
            par = par.parent;
        }

        return true;
    }
    public boolean remove(int key) {
	// HX-2026-04-09: 20 points
	// If key is in the tree stored at [root],
	// [remove] removes the key and the new tree
	// obtained is still a binary search tree and
	// [remove] returns true to indicate the removal
	// is done.
	// If key is not in the tree stored at [root],
	// [remove] does nothing and returns false to
	// indicate that no removal of the key k is done.

		Node cur = root;

		// search for the node with the given key
		while (cur != null && cur.key != key) {
			if (key < cur.key) {
				cur = cur.lchild;
			} else {
				cur = cur.rchild;
			}
		}

		// key was not found
		if (cur == null) {
			return false;
		}

		// if node has two children then replace its key with
		// smallest key in the right subtree
		if (cur.lchild != null && cur.rchild != null) {
			Node suc = cur.rchild;
			while (suc.lchild != null) {
				suc = suc.lchild;
			}
			cur.key = suc.key;
			cur = suc;
		}

		// now cur has at most one child
		Node child;
		if (cur.lchild != null) {
			child = cur.lchild;
		} else {
			child = cur.rchild;
		}

		// connect child to cur's parent
		if (child != null) {
			child.parent = cur.parent;
		}

		// remove cur from tree
		if (cur.parent ==null) {
			root = child;
		} else if (cur.parent.lchild == cur) {
			cur.parent.lchild = child;
		} else {
			cur.parent.rchild = child;
		}

		// update size fields going back up to the root
        Node par = cur.parent;
        while (par != null) {
            par.size = 1;
            if (par.lchild != null) par.size += par.lchild.size;
            if (par.rchild != null) par.size += par.rchild.size;
            par = par.parent;
        }
        return true;
	}
    
    public static void main (String[] args) {
		// Please add minimal testing code for insert()
		// Please add minimal testing code for remove()
		Assign09_01 t = new Assign09_01();
		// minimal testing for insert()
        System.out.println(t.insert(50)); // true
        System.out.println(t.insert(30)); // true
        System.out.println(t.insert(70)); // true
        System.out.println(t.insert(30)); // false

        // minimal testing for remove()
        System.out.println(t.remove(30));  // true
        System.out.println(t.remove(100)); // false
    }
}


