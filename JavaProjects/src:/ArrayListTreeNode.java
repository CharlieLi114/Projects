import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * An implementation of SimpleTreeNode using ArrayList for child storage.
 */
public class ArrayListTreeNode<E> implements SimpleTreeNode<E> {

    private E data;
    private ArrayListTreeNode<E> parent = null;
    private ArrayList<ArrayListTreeNode<E>> children = new ArrayList<>();

    /**
     * Constructs a node as root of its own one-element tree.
     * * @param data The data to be stored in the node.
     */
    public ArrayListTreeNode(E data) {
        this.data = data;
    }

    /**
     * Returns the data stored in this node.
     * * @return the data stored in the node
     */
    public E getData() {
        return data;
    }

    /**
     * Modifies the data stored in this node.
     * * @param data the new data to store
     */
    public void setData(E data) {
        this.data = data;
    }

    /**
     * Returns the parent of this node, or null if root.
     * * @return the parent node
     */
    public SimpleTreeNode<E> getParent() {
        return parent;
    }

    /**
     * Returns the children of this node as a read-only list.
     * * @return a List of children
     */
    public List<? extends SimpleTreeNode<E>> getChildren() {
        return Collections.unmodifiableList(children);
    }

    /**
     * Removes child from its current parent and inserts it
     * at the given index of this node.
     * * @param index position to insert
     * @param child the node to add
     */
    public void insertChildAt(int index, SimpleTreeNode<E> child) {
        for (ArrayListTreeNode<E> node = this; node != null; node = node.parent) {
            if (node == child) {
                throw new IllegalArgumentException("Child is an ancestor");
            }
        }

        ArrayListTreeNode<E> childNode = (ArrayListTreeNode<E>) child;
        children.add(index, childNode);

        if (childNode != null) {
            childNode.removeFromParent();
            childNode.parent = this;
        }
    }

    /**
     * Remove this node and its descendants from the tree. 
     */
    public void removeFromParent() {
        if (parent != null) {
            parent.children.remove(this);
            this.parent = null;
        }
    }

    /**
     * Prints the nodes in this tree in breadth-first order using an ArrayList as a queue.
     */
    public void printBreadthFirst() {
        List<ArrayListTreeNode<E>> queue = new ArrayList<>();
        queue.add(this);

        while (!queue.isEmpty()) {
            ArrayListTreeNode<E> current = queue.remove(0);
            System.out.println(current.data);

            for (ArrayListTreeNode<E> node : current.children) {
                queue.add(node);
            }
        }
    }

    @Override
    public String toString() {
        return data.toString();
    }
}