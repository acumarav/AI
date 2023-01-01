package alex.ai01labs.iddfs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Node {
    private final String name;
    private int depthLevel;
    private final List<Node> adjacencyList = new ArrayList<>();

    public Node(String name) {
        this.name = name;
    }

    public void addNeighbors(Node... neighbors) {
        adjacencyList.addAll(Arrays.asList(neighbors));
    }

    public List<Node> getNeighbors() {
        return Collections.unmodifiableList(adjacencyList);
    }

    public String getName() {
        return name;
    }

    public int getDepthLevel() {
        return depthLevel;
    }

    public void setDepthLevel(int depthLevel) {
        this.depthLevel = depthLevel;
    }

    @Override
    public String toString() {
        return name;
    }
}
