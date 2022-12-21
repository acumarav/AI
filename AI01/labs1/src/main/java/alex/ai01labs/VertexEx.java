package alex.ai01labs;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

public class VertexEx {

    private String name;
    private boolean visited;
    private final List<VertexEx> adjacencyList = new ArrayList<>();

    public VertexEx(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public List<VertexEx> getNeighbors() {
        return adjacencyList;
    }

    public void addNeighbor(VertexEx... vertexExes) {
        this.adjacencyList.addAll(asList(vertexExes));
    }

    @Override
    public String toString() {
        return name;
    }
}
