package alex.ai01labs;

import java.util.List;
import java.util.Stack;

public class DepthFirstSearch {

    private final Stack<Vertex> stack = new Stack<>();

    public static void main(String[] args) {
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

        DepthFirstSearch dfs = new DepthFirstSearch();
        dfs.traverse(a);
    }

    public void traverse(Vertex root) {
    }

    public void dfs(List<Vertex> vertexList) {
        //it may happen that we have independent clusters
        for (Vertex v : vertexList) {
            if (!v.isVisited()) {
                v.setVisited(true);
                dfsHelper(v);
            }
        }
    }

    private void dfsHelper(Vertex rootVertex) {
        stack.add(rootVertex);
        rootVertex.setVisited(true);
        while (!stack.isEmpty()) {
            var actualVertex = stack.pop();
            System.out.println(actualVertex);
            for (Vertex v : actualVertex.getAdjacencyList())
                if (!v.isVisited()) {
                    v.setVisited(true);
                    stack.add(v);
                }
        }
    }
}
