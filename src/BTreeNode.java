import java.lang.reflect.Array;
import java.util.ArrayList;


/**
 * The nodes for Btrees, which will hold TreeObjects.
 * @Param TreeObject
 * @Author Alayne Rice
 * @Semester
 */
public class BTreeNode<TreeObject> {
    private int M; //order of tree
    private int keys = M - 1;
    private BTreeNode[] children;
    private Object[] arrayVals;
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
        children = new BTreeNode[M];
        arrayVals = new Object[keys];
    }


    public int getM() {
        return M;
    }

    public void setM(int newM) {
        M = newM;
    }

    //unsure if neccessary?
    /**
     * returns a child node from the current node.
     *
     * @param location
     * @return
     */
    public BTreeNode getChild(int location) {
        return children[location];
    }

    public TreeObject getObject(int location){
        return (TreeObject) arrayVals[location];
    }


    }


// probably needs other shit Iunno yet!