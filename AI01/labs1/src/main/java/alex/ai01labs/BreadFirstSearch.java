package alex.ai01labs;


import java.util.LinkedList;
import java.util.Queue;

public class BreadFirstSearch {

    public void traverse(Vertex root) {
        Queue<Vertex> queue = new LinkedList<>();
        root.setVisited(true);
        queue.add(root);

        while (!queue.isEmpty()) {
            var actualVertex = queue.remove();
            System.out.println("Visiting: " + actualVertex);
            for (Vertex v : actualVertex.getAdjacencyList())
                if (!v.isVisited()) {
                    v.setVisited(true);
                    queue.add(v);
                }
        }
    }

    public static void main(String[] args){
        BreadFirstSearch bfs = new BreadFirstSearch();

        Vertex a = new Vertex("A");
        Vertex b = new Vertex("B");
        Vertex c = new Vertex("C");
        Vertex d = new Vertex("D");
        Vertex e = new Vertex("E");
        Vertex f = new Vertex("F");
        Vertex g = new Vertex("G");
        Vertex h = new Vertex("H");

        a.addNeighbor(b);
        a.addNeighbor(f);
        a.addNeighbor(g);

        b.addNeighbor(a);
        b.addNeighbor(c);
        b.addNeighbor(d);

        c.addNeighbor(b);

        d.addNeighbor(b);
        d.addNeighbor(e);

        g.addNeighbor(a);
        g.addNeighbor(h);

        h.addNeighbor(g);

        bfs.traverse(a);
        // Expected order: "A" -> "B" -> "F" -> "G" -> "C" -> "D" -> "H" -> "E"
    }
}
