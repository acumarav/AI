package alex.ai01labs;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

public class DepthFirstSearchRecursion {

    public static void main(String[] args) {
        VertexEx v1 = new VertexEx("A");
        VertexEx v2 = new VertexEx("B");
        VertexEx v3 = new VertexEx("C");
        VertexEx v4 = new VertexEx("D");
        VertexEx v5 = new VertexEx("E");
        VertexEx v6 = new VertexEx("F");
        VertexEx v7 = new VertexEx("G");
        VertexEx v8 = new VertexEx("H");

        List<VertexEx> list = new ArrayList<>();
        list.addAll(asList(v1, v2, v3, v4, v5, v6, v7, v8));

        v1.addNeighbor(v2, v6, v7);
        v2.addNeighbor(v3, v4);
        v4.addNeighbor(v5);
        v7.addNeighbor(v8);

        DepthFirstSearchRecursion dfsEx = new DepthFirstSearchRecursion();
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
        System.out.println(rootVertex);
        for (var neighbor : rootVertex.getNeighbors()) {
            if (!neighbor.isVisited()) {
                neighbor.setVisited(true);
                dfsHelper(neighbor);
            }
        }
    }
}
