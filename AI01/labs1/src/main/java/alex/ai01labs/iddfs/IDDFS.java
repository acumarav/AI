package alex.ai01labs.iddfs;

import java.util.Stack;

/**
 * IDDFS - Iterative Dependent Depth First Search
 */
public class IDDFS {

    private final Node targetNode;
    private boolean isTargetFound;
    private int maxLength;

    public IDDFS(Node targetNode) {
        this.targetNode = targetNode;
    }

    public static void main(String[] args) {
        Node node0 = new Node("A");
        Node node1 = new Node("B");
        Node node2 = new Node("C");
        Node node3 = new Node("D");
        Node node4 = new Node("E");
        Node node5 = new Node("F");

        node0.addNeighbors(node1, node2);

        node1.addNeighbors(node3);
        node3.addNeighbors(node4);

        // Exist way to the node
        new IDDFS(node4).run(node0);

        // Does not exist way
        new IDDFS(node5).run(node0);
    }

    public void run(Node root) {
        int depth = 0;
        while (!isTargetFound) {

            if (depth > maxLength) {
                System.out.println("Node is not found...");
                break;
            }

            System.out.println();
            dfs(root, depth);
            depth++;
        }
    }

    private void dfs(Node source, int depth) {
        Stack<Node> stack = new Stack<>();

        stack.push(source);
        while (!stack.isEmpty()) {
            Node actual = stack.pop();
            System.out.print(actual + " ");

            if (actual.getName().equals(targetNode.getName())) {
                System.out.println("Node has been found...");
                isTargetFound = true;
                return;
            }

            if (!actual.getNeighbors().isEmpty()) {
                maxLength = actual.getDepthLevel() + 1;
            }

            if (actual.getDepthLevel() >= depth) {
                continue; //do not step in to the children
            }
            for (Node node : actual.getNeighbors()) {
                node.setDepthLevel(actual.getDepthLevel() + 1);
                stack.push(node);
            }

        }
    }
}
