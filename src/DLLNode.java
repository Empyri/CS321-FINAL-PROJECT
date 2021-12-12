/**
 * add
 */
public class DLLNode<T> {


    private DLLNode<T> next = null;
    private DLLNode<T> previous=null;
    private T data = null;


    /**
     * Creates a node storing the specified element.
     *
     * @param data
     *            the element to be stored within the new node
     */
    public DLLNode(T data) {
        next = null;
        previous=null;
        data = data;
    }

    /**
     * Returns the node that follows this one.
     *
     * @return the node that follows the current one
     */
    public DLLNode<T> getNext() {
        return next;
    }

    /**
     * Sets the node that follows this one.
     *
     * @param node
     *            the node to be set to follow the current one
     */
    public void setNext(DLLNode node) {

        next = node;
    }

    public DLLNode<T> getPrevious() {

        return previous;
    }

    public void setPrevious(DLLNode<T> previous) {
        this.previous = previous;
    }

    /**
     * Returns the element stored in this node.
     *
     * @return the element stored in this node
     */
    public T getElement() {

        return data;
    }

    /**
     * Sets the element stored in this nodeDLLNode.
     *
     * @param data
     *            the element to be stored in this node
     */
    public void setElement(T data) {
        data = data;
    }

    @Override
    public String toString() {

        return "Element: " + data.toString() + " Has next: " + (next != null);
    }
}
