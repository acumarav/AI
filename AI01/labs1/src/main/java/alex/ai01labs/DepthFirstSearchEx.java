package alex.ai01labs;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import static java.util.Arrays.asList;

/**
 * Naive implementation of DFS with isolated clusters
 */
public class DepthFirstSearchEx {
    private final Stack<VertexEx> stack = new Stack();

    public static void main(String[] args) {
        VertexEx v1 = new VertexEx("A");
        VertexEx v2 = new VertexEx("B");
        VertexEx v3 = new VertexEx("C");
        VertexEx v4 = new VertexEx("D");
        VertexEx v5 = new VertexEx("E");

        List<VertexEx> list = new ArrayList<>();
        list.addAll(asList(v1, v2, v3, v4, v5));

        //Two isolated clusters  A-B-C D-E
        v1.addNeighbor(v2, v3);
        v4.addNeighbor(v5);

        DepthFirstSearchEx dfsEx = new DepthFirstSearchEx();
        dfsEx.dfs(list);

    }


    public void dfs(List<VertexEx> vertexExes) {
        for (VertexEx v : vertexExes) {
            if (!v.isVisited()) {
                v.setVisited(true);
                dfsHelper(v);

            }
        }
    }

    private void dfsHelper(VertexEx rootVertex) {
        stack.add(rootVertex);
        rootVertex.setVisited(true);

        while (!stack.isEmpty()) {
            var vertex = stack.pop();
            System.out.println(vertex);

            for (VertexEx v : vertex.getNeighbors()) {
                if (!v.isVisited()) {
                    v.setVisited(true);
                    stack.add(v);
                }

            }

        }

    }

}
