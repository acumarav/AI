package alex.ai01labs.astar;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.Arrays.asList;

@Getter
public class Node {

    private String name;
    private double x;
    private double y;
    @Setter
    private double g;
    @Setter
    private double h;
    @Setter
    private double f;

    private List<Edge> adjacencyList = new ArrayList<>();
    @Setter
    private Node parent; //track previous node in the shortest path

    public Node(String name, double x, double y) {
        this.name = name;
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return name;
    }

    void addNeighbors(Edge... neighbors){
        this.adjacencyList.addAll(asList(neighbors));
    }
}
