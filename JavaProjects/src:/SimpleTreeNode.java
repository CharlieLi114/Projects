import java.util.List;

/**
 * A simple interface for tree nodes.
 * Defines the contract for General Tree structures.
 *
 * @author Chengyu Li { @literal <lic24@wfu.edu >}
 * @version 1.0
 */
public interface SimpleTreeNode<E> {

    /**
     * Returns the data stored in this node.
     * @return the data object
     */
    E getData();

    /**
     * Replaces data object stored in this node with data.
     * @param data the new data to store
     */
    void setData(E data);

    /**
     * Returns the parent of this node, or null if root.
     * @return the parent node or null
     */
    SimpleTreeNode<E> getParent();

    /**
     * Returns the children of this node as a list of nodes.
     * List is read-only to prevent callers from mangling the actual tree.
     * @return a List of child nodes
     */
    List<? extends SimpleTreeNode<E>> getChildren();

    /**
     * Removes child from its current parent and inserts it
     * at the given index of this node. Indices start at 0.
     * @param index the position to insert the child
     * @param child the node to adopt
     */
    void insertChildAt(int index, SimpleTreeNode<E> child);

    /**
     * Removes this node, and all its descendants, from the
     * tree it is in. Does nothing if this node is a root.
     */
    void removeFromParent();

    /**
     * Prints the nodes in this tree in breadth-first order.
     */
    void printBreadthFirst();
}