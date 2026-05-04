package MyLibrary.BST;

import MyLibrary.LnList.LnList;

/* Doubly linked binary search tree */
public class BST<K extends Comparable<K>, V> {
    public class Node {
        public K key;
        public V value;
        public Node parent;
        public Node left;
        public Node right;
        public int size;

        Node(K key, V value, Node parent) {
            this.key = key;
            this.value = value;
            this.parent = parent;
            this.size = 1;
        }
    }

    private Node root;

    public int size() {
        return size(root);
    }

    private int size(Node node) {
        return node == null ? 0 : node.size;
    }

    public boolean isEmpty() {
        return root == null;
    }

    public V get(K key) {
        Node node = searchNode(key);
        return node == null ? null : node.value;
    }

    public boolean containsKey(K key) {
        return searchNode(key) != null;
    }

    private Node searchNode(K key) {
        Node current = root;

        while (current != null) {
            int cmp = key.compareTo(current.key);

            if (cmp < 0) {
                current = current.left;
            } else if (cmp > 0) {
                current = current.right;
            } else {
                return current;
            }
        }

        return null;
    }

    public void put(K key, V value) {
        if (root == null) {
            root = new Node(key, value, null);
            return;
        }

        Node current = root;
        Node parent = null;
        int cmp = 0;

        while (current != null) {
            parent = current;
            cmp = key.compareTo(current.key);

            if (cmp < 0) {
                current = current.left;
            } else if (cmp > 0) {
                current = current.right;
            } else {
                current.value = value;
                return;
            }
        }

        Node node = new Node(key, value, parent);

        if (cmp < 0) {
            parent.left = node;
        } else {
            parent.right = node;
        }

        updateSizesUpward(parent);
    }

    public boolean remove(K key) {
        Node node = searchNode(key);

        if (node == null) {
            return false;
        }

        deleteNode(node);
        return true;
    }

    private void deleteNode(Node node) {
        if (node.left == null) {
            transplant(node, node.right);
        } else if (node.right == null) {
            transplant(node, node.left);
        } else {
            Node succ = minNode(node.right);

            if (succ.parent != node) {
                Node oldParent = succ.parent;
                transplant(succ, succ.right);

                succ.right = node.right;
                succ.right.parent = succ;

                updateSizesUpward(oldParent);
            }

            transplant(node, succ);

            succ.left = node.left;
            succ.left.parent = succ;

            recomputeSize(succ);
            updateSizesUpward(succ.parent);
        }
    }

    private void transplant(Node oldNode, Node newNode) {
        Node parent = oldNode.parent;

        if (parent == null) {
            root = newNode;
        } else if (oldNode == parent.left) {
            parent.left = newNode;
        } else {
            parent.right = newNode;
        }

        if (newNode != null) {
            newNode.parent = parent;
        }

        updateSizesUpward(parent);
    }

    public K minKey() {
        Node node = minNode(root);
        return node == null ? null : node.key;
    }

    public K maxKey() {
        Node node = maxNode(root);
        return node == null ? null : node.key;
    }

    private Node minNode(Node node) {
        if (node == null) {
            return null;
        }

        while (node.left != null) {
            node = node.left;
        }

        return node;
    }

    private Node maxNode(Node node) {
        if (node == null) {
            return null;
        }

        while (node.right != null) {
            node = node.right;
        }

        return node;
    }

    public K successor(K key) {
        Node node = searchNode(key);

        if (node == null) {
            return null;
        }

        Node succ;

        if (node.right != null) {
            succ = minNode(node.right);
        } else {
            Node parent = node.parent;

            while (parent != null && node == parent.right) {
                node = parent;
                parent = parent.parent;
            }

            succ = parent;
        }

        return succ == null ? null : succ.key;
    }

    public K predecessor(K key) {
        Node node = searchNode(key);

        if (node == null) {
            return null;
        }

        Node pred;

        if (node.left != null) {
            pred = maxNode(node.left);
        } else {
            Node parent = node.parent;

            while (parent != null && node == parent.left) {
                node = parent;
                parent = parent.parent;
            }

            pred = parent;
        }

        return pred == null ? null : pred.key;
    }

    private void updateSizesUpward(Node node) {
        while (node != null) {
            recomputeSize(node);
            node = node.parent;
        }
    }

    private void recomputeSize(Node node) {
        if (node != null) {
            node.size = 1 + size(node.left) + size(node.right);
        }
    }

    public LnList<K> keysInOrder() {
        return inorder(root, new LnList<K>());
    }

    /*
     * Builds the list in sorted order 
     *
     * Process right subtree first, then current node, then left subtree
     */
    private LnList<K> inorder(Node node, LnList<K> acc) {
        if (node == null) {
            return acc;
        }

        acc = inorder(node.right, acc);
        acc = new LnList<K>(node.key, acc);
        acc = inorder(node.left, acc);

        return acc;
    }
}
