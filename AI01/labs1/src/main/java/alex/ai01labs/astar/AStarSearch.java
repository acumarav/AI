package alex.ai01labs.astar;

import java.util.*;

public class AStarSearch {

    private final Node source;
    private final Node destination;
    private final Set<Node> explored = new HashSet<>();

    private final PriorityQueue<Node> queue = new PriorityQueue<>(new NodeComparator());

    public AStarSearch(Node source, Node destination) {
        this.source = source;
        this.destination = destination;
    }

    public static void main(String[] args) {
        Node nA1 = new Node("A", 0, 0);
        Node nB2 = new Node("B", 10, 20);
        Node nC3 = new Node("C", 20, 40);
        Node nD4 = new Node("D", 30, 10);
        Node nE5 = new Node("E", 40, 30);
        Node nF6 = new Node("F", 50, 10);
        Node nG7 = new Node("G", 50, 40);

        nA1.addNeighbors(new Edge(nB2, 10), new Edge(nD4, 50));
        nB2.addNeighbors(new Edge(nC3, 10), new Edge(nD4,20));
        nC3.addNeighbors(new Edge(nE5, 10), new Edge(nG7,30));
        nD4.addNeighbors(new Edge(nF6, 80));
        nE5.addNeighbors(new Edge(nF6, 50), new Edge(nG7,10));
        nG7.addNeighbors(new Edge(nF6, 10));

        AStarSearch search = new AStarSearch(nA1, nF6);
        search.run();
        search.printSolutionPath();
    }

    public void run() {
        queue.add(source);
        while (!queue.isEmpty()) {
            //getting node with the lowest f(x) value
            var current = queue.poll();
            explored.add(current);

            if (current == destination) {
                break;
            }

            for (var edge : current.getAdjacencyList()) {
                Node child = edge.getTarget();
                double cost = edge.getWeight();
                var tempG = current.getG() + cost;
                var tempF = tempG + heuristic(current, destination);

                if (explored.contains(child) && tempF >= child.getF()) {
                    continue;
                } else if (!queue.contains(child) || tempF < child.getF()) {
                    child.setParent(current);
                    child.setG(tempG);
                    child.setF(tempF);

                    queue.remove(child);//re-add to reorder as G and F has changed
                    queue.add(child);
                }
            }
        }
    }

    private double heuristic(Node node1, Node node2) {
        var dX = node1.getX() - node2.getX();
        var dY = node1.getY() - node2.getY();
        return Math.sqrt(dX * dX + dY * dY);
    }

    public void printSolutionPath() {
        List<Node> path = new ArrayList<>();
        for (Node node = destination; node != null; node =node.getParent()) {
            path.add(node);
        }
        Collections.reverse(path);
        System.out.println(path);
    }
}
