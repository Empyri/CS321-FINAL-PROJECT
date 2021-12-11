
public class BTree{
    // max children per B-tree node = M-1
    // (must be even and greater than 2)
    private static int M; //the Order of the B-tree
    private BTreeNode root;       // This is the stand-in value for the root of the B-tree
    private int height;      // Counter for the height of the Btree
    private int size;           // the current number of key-value pairs in the B-tree

    /**
     * A private BTree class that serves as the nodes of the B-Tree.
     * contains an array of TreeObjects with the size of the order.
     *
     */
    private static final class BTreeNode {
        private int numChildren;                             // number of children
        private TreeObject[] children = new TreeObject[M];   // the array of children

        /**
         * creates a BTreeNode.
         * @param childrenNum The number of children that the BTreeNode has.
         */
        private BTreeNode(int childrenNum) {
            numChildren = childrenNum;
        }
    }

    /**
     * A TreeObject class that contains the objects inside the BTreeNodes.
     * A helper to keep track of everything.
     */
    // The internal nodes: only care about key and next
    // external nodes: only care about key and value
    private static class TreeObject {
        private Long key;
        private String dnaVal;
        private int frequency;
        private BTreeNode next;

        /**
         * Creates a TreeObject.
         * @param key the Key of the TreeObject.
         * @param dnaVal the Value of the DNA string for the TreeObject.
         * @param next the iterative value for the TreeObject to help guide along the Btree.
         */
                public TreeObject(long key, String dnaVal , BTreeNode next) {
            this.key  = key;
            this.frequency=1;
            this.dnaVal  = dnaVal;
            this.next = next;
        }

        public int getFrequency(){
            return this.frequency;
        }

    }

    /**
     * Initializes an empty B-tree.
     * M is preset to 32 which is optimal for the 32 bit longs with a 4096 size array.
     */
    public BTree() {
      this(32);
    }

    /**
     * Creates a B-Tree with a specified order
     * @Limitation The Order must be even and greater than or equal to 4.
     * @param mVal The order specified.
     */
    public BTree(int mVal){
        if (mVal == 0){
            M = 32;
        }
         else if (mVal < 4 && mVal%2!=0
        ){
            throw new IllegalArgumentException("The order must be Even or greater than 4");
        }
        M = mVal;
        root = new BTreeNode(0);
    }

    /**
     * Returns true if this symbol table is empty.
     * @return {@code true} if this symbol table is empty; {@code false} otherwise
     */
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Returns the number of key-value pairs in this symbol table.
     * @return the number of key-value pairs in this symbol table
     */
    public int size() {
        return size;
    }

    /**
     * Returns the height of this B-tree (for debugging).
     *
     * @return the height of this B-tree
     */
    public int height() {
        return height;
    }


//    /**
//     * Returns the value associated with the given key.
//     *
//     * @param  key the key
//     * @return the value associated with the given key if the key is in the symbol table
//     *         and {@code null} if the key is not in the symbol table
//     * @throws IllegalArgumentException if {@code key} is {@code null}
//     */
//    public String get(Long key) {
//        if (key == null) throw new IllegalArgumentException("argument to get() is null");
//        return search(root, key, height);
//    }
//
//
//    private String search(BTreeNode x, Long key, int ht) {
//        TreeObject[] children = x.children;
//
//        // external node
//        if (ht == 0) {
//            for (int j = 0; j < x.numChildren; j++) {
//                if (compareTo(key, children[j].key)==0) return (String) children[j].dnaVal;
//            }
//        }
//
//        // internal node
//        else {
//            for (int j = 0; j < x.numChildren; j++) {
//                if (j+1 == x.numChildren || compareTo(key, children[j+1].key)<1)
//                    return search(children[j].next, key, ht-1);
//            }
//        }
//        return null;
//    }


    /**
     * Inserts the key-dnaVal pair into the symbol table, overwriting the old value
     * with the new value if the key is already in the symbol table.
     * If the value is {@code null}, this effectively deletes the key from the symbol table.
     *
     * @param  key the key
     * @param  dnaVal the value
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public void put(Long key, String dnaVal) {
        if (key == null) throw new IllegalArgumentException("argument key to put() is null");
        BTreeNode u = insert(root, key, dnaVal, height);
        size++;
        if (u == null) return;

        // need to split root
        BTreeNode t = new BTreeNode(2);
        t.children[0] = new TreeObject(root.children[0].key, null, root);
        t.children[1] = new TreeObject(u.children[0].key, null, u);
        root = t;
        height++;
    }

    private BTreeNode insert(BTreeNode h, Long key, String dnaVal, int ht) {
        int j;
        TreeObject t = new TreeObject(key, dnaVal, null);

        // external node
        if (ht == 0) {
            for (j = 0; j < h.numChildren; j++) {
                if(compareTo(key,h.children[j].key)==0){h.children[j].frequency++;
                return null; }
                if (compareTo(key, h.children[j].key)<0) break;
            }
        }

        // internal node
        else {
            for (j = 0; j < h.numChildren; j++) {
                if ((j+1 == h.numChildren) || compareTo(key, h.children[j+1].key)<0) {
                    BTreeNode u = insert(h.children[j++].next, key, dnaVal, ht-1);
                    if (u == null) return null;
                    t.key = u.children[0].key;
                    t.dnaVal = null;
                    t.next = u;
                    break;
                }
            }
        }

        for (int i = h.numChildren; i > j; i--)
            h.children[i] = h.children[i-1];
        h.children[j] = t;
        h.numChildren++;
        if (h.numChildren < M) return null;
        else         return split(h);
    }

    // split node in half
    private BTreeNode split(BTreeNode h) {
        BTreeNode t = new BTreeNode(M/2);
        h.numChildren = M/2;
        for (int j = 0; j < M/2; j++)
            t.children[j] = h.children[M/2+j];
        return t;
    }

    /**
     * Returns a string representation of this B-tree (for debugging).
     *
     * @return a string representation of this B-tree.
     */
    public String toString() {
        return toString(root, height) + "\n";
    }

    private String toString(BTreeNode h, int ht) {
        StringBuilder s = new StringBuilder();
        TreeObject[] children = h.children;

        if (ht == 0) {
            for (int j = 0; j < h.numChildren; j++) {
                s.append( children[j].key + " " + children[j].dnaVal + " " + children[j].frequency + "\n");
            }
        }
        else {
            for (int j = 0; j < h.numChildren; j++) {
                s.append(toString(children[j].next, ht-1));
            }
        }
        return s.toString();
    }

    public int compareTo(Comparable k1, Comparable k2)
    {
        return k1.compareTo(k2);
    }
}