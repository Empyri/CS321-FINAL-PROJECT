import com.sun.source.tree.Tree;

/**
 * The nodes for Btrees, which will hold TreeObjects.
 * @Param TreeObject
 * @Author Alayne Rice
 * @Semester
 */
public class BTreeNode{
    private int M; //order of tree
    private int keys = M - 1;
    private TreeObject[] children = new TreeObject[M];
    private BTreeNode[] childNodes = new BTreeNode[keys];
    private int numChildren;
    // min children - internal nodes = M/2
    //root has min children of 0
    //min keys - root node - 1
    //min keys - all other nodes - (M/2)-1

    /**
     * Creates an empty node with no children.
     *
     * @param mVal Order of tree for calculation inside the node.
     */
    public BTreeNode(int mVal) {
        this(mVal, 0);

    }


    public BTreeNode(int mVal, int childrenVal) {
        M = mVal;
        numChildren = childrenVal;
    }


    public void addChildren(int location, TreeObject item) {
        children[location] = item;
    }

    public void addNMode(int location, BTreeNode item){
        childNodes[location] = item;
    }


    public int getM() {
        return M;
    }

    public void setM(int newM) {
        M = newM;
    }

    public int getNumChildren() {
        return numChildren;
    }

    public void setNumChildren(int newNum) {
        numChildren = newNum;
    }

    //unsure if neccessary?

    /**
     * returns a child node from the current node.
     *
     * @param location the location of the child you want to gather.
     * @return the child you wish to gather.
     */
    public BTreeNode getChildNode(int location) {
        BTreeNode child = (BTreeNode) childNodes[location];
        return child;
    }

    public TreeObject getChildObject(int location){
        return (TreeObject) children[location];
    }


}

