package alex.ai01labs.astar;

import java.util.Comparator;

public class NodeComparator implements Comparator<Node> {


    @Override
    public int compare(Node node1, Node node2) {
        return Double.compare(node1.getF(), node2.getF());
    }
}
