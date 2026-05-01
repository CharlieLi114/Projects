public class Main {
    /**
     * Main method to execute tree construction and printing.
     *
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {
        ArrayListTreeNode<String> nodeA = new ArrayListTreeNode<>("A");
        ArrayListTreeNode<String> nodeB = new ArrayListTreeNode<>("B");
        ArrayListTreeNode<String> nodeC = new ArrayListTreeNode<>("C");
        ArrayListTreeNode<String> nodeD = new ArrayListTreeNode<>("D");
        ArrayListTreeNode<String> nodeE = new ArrayListTreeNode<>("E");
        ArrayListTreeNode<String> nodeF = new ArrayListTreeNode<>("F");

        // A is the root.
        // A has children: B, C, D
        nodeA.insertChildAt(0, nodeB);
        nodeA.insertChildAt(1, nodeC);
        nodeA.insertChildAt(2, nodeD);

        // B has children: E, F
        nodeB.insertChildAt(0, nodeE);
        nodeB.insertChildAt(1, nodeF);

        System.out.println("Breadth-First Traversal Output:");
        nodeA.printBreadthFirst();
    }
}
