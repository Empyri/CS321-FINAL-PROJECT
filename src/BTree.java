/**
 * Primary class for implementing a B-tree.
 * This class will take a long and a string input.
 * The long is the key, and the string is the value paired with the key.
 * It cannot delete any entries, and manages a link between key-values.
 * @Limitations, The Order must be even AND >=4.
 * @Author Alayne Rice
 * @Author Michael Alberda
 * @Author Jordan Whyte
 * @Semester Fall 2021
 */
import java.io.Serializable;
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
    private static final class BTreeNode  implements Serializable{
        private int numChildren;                             // number of children
        private TreeObject[] children = new TreeObject[M];   // the array of children

        /**
         * creates a BTreeNode. Requires an input of how many children are in the BTreeNode.
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
    private static class TreeObject  implements Serializable{
        private Long key;
        private String dnaVal;
        private int frequency;
        private BTreeNode next;
        // The internal nodes: only care about key and next
        // external nodes: only care about key and value

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
     * Returns the value associated with the given key.
     *
     * @param  key the key
     * @return the value associated with the given key if the key is in the symbol table
     *         and {@code null} if the key is not in the symbol table
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public String get(Long key) {
        if (key == null) throw new IllegalArgumentException("argument to get() is null");
        return search(root, key, height);
    }


    private String search(BTreeNode x, Long key, int ht) {
        TreeObject[] children = x.children;

        // external node
        if (ht == 0) {
            for (int j = 0; j < x.numChildren; j++) {
                if (compareTo(key, children[j].key)==0) return (String) children[j].dnaVal;
            }
        }

        // internal node
        else {
            for (int j = 0; j < x.numChildren; j++) {
                if (j+1 == x.numChildren || compareTo(key, children[j+1].key)<1)
                    return search(children[j].next, key, ht-1);
            }
        }
        return null;
    }

    private String geneSearch(BTreeNode x, Long key, int ht) {
        TreeObject[] children = x.children;
        // external node
        if (ht == 0) {
            for (int j = 0; j < x.numChildren; j++) {
                if (compareTo(key, children[j].key)==0) return ((String) children[j].dnaVal+": "+children[j].frequency);
            }
        }

        // internal node
        else {
            for (int j = 0; j < x.numChildren; j++) {
                if (j+1 == x.numChildren || compareTo(key, children[j+1].key)<1)
                    return search(children[j].next, key, ht-1);
            }
        }
        return null;
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
     * @return true if this symbol table is empty; false otherwise
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
     * Returns the height of this B-tree.
     * @return the height of this B-tree
     */
    public int height() {
        return height;
    }

    public BTreeNode getRoot() { return root;}

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

    /**
     * A private class that helps manage put, by allowing self recursion and managing when splits happen.
     * @param h the BTreeNode that is input for insertion.
     * @param key the key to be inserted
     * @param dnaVal the String to be paired with the key input
     * @param ht the current height of the BTree.
     * @return Null if there is no need to return a split node set.
     * @return A split node if one node becomes overfilled.
     */
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


    /**
     * Splits a node in half
     * @param h The node to be split
     * @return a node with the splits implemented.
     */
    private BTreeNode split(BTreeNode h) {
        BTreeNode t = new BTreeNode(M/2);
        h.numChildren = M/2;
        for (int j = 0; j < M/2; j++)
            t.children[j] = h.children[M/2+j];
        return t;
    }

    /**
     * Returns a string representation of this B-tree.
     * @return a string representation of this B-tree.
     */
    public String toString() {
        return toString(root, height) + "\n";
    }

    /**
     * A internal private class that uses a StringBuilder to append the children of nodes, account for repeats, and display it all
     * @param h the node to be transcribed (It is generally root).
     * @param ht the height of the B-tree. (generally the height)
     * @return A value mimicing this: Value: frewuency.
     */
    private String toString(BTreeNode h, int ht) {
        StringBuilder s = new StringBuilder();
        TreeObject[] children = h.children;

        if (ht == 0) {
            for (int j = 0; j < h.numChildren; j++) {
                s.append(children[j].dnaVal + ": " + children[j].frequency + "\n");
            }
        }
        else {
            for (int j = 0; j < h.numChildren; j++) {
                s.append(toString(children[j].next, ht-1));
            }
        }
        return s.toString();
    }

    /**
     * an implemented compareTo to ensure that comparisons for insertion work as intended.
     * @param k1 the key to be compared
     * @param k2 the key comparing against k1.
     * @return 3 potential integers. -1 if k1 is less than k2, 0 if they are equal, and 1 if k1 is greater than k2.
     */
    public int compareTo(Comparable k1, Comparable k2)
    {
        return k1.compareTo(k2);
    }
}