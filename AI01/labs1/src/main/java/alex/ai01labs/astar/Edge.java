package alex.ai01labs.astar;

import lombok.Getter;

@Getter
public class Edge {

    private double weight;
    private Node target;

    public Edge(Node target, double weight) {
        this.weight = weight;
        this.target = target;
    }


}
