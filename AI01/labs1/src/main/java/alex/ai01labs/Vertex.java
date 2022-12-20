package alex.ai01labs;

import java.util.ArrayList;
import java.util.List;

public class Vertex {
    private final String name;
    private boolean visited;
    private List<Vertex> ajacencyList;

    public Vertex(String name) {
        this.name = name;
        this.ajacencyList = new ArrayList<>();

    }

    public String getName() {
        return name;
    }

    public boolean isVisited() {
        return visited;
    }

    public List<Vertex> getAdjacencyList() {
        return ajacencyList;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public void addNeighbor(Vertex vertex) {
        this.ajacencyList.add(vertex);
    }

    @Override
    public String toString() {
        return this.name;
    }
}
