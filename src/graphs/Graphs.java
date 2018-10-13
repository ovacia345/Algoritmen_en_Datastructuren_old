package graphs;

import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 *
 * @author N.C.M. van Nistelrooij
 */
public class Graphs {
    private static enum Color {
        WHITE, GRAY, BLACK
    };

    /**
     * BFS run on G from vertex s and outputs the distances from said vertex
     * and all the parents in the breadth-first tree
     * @param G the input graph
     * @param s the source vertex
     * @return output, where output[0] is the distance array and output[1] is the
     * parent array
     */
    public static int[][] BFS(Graph G, int s) {
        checkVertex(G, s);
        int nrVertices = G.getNrVertices();
        Color[] colors = new Color[nrVertices];
        int[] ds = new int[nrVertices];
        int[] parents = new int[nrVertices];

        for (int u = 0; u < nrVertices; u++) {
            colors[u] = Color.WHITE;
            ds[u] = Integer.MAX_VALUE;
            parents[u] = -1;
        }
        colors[s] = Color.GRAY;
        ds[s] = 0;

        Queue<Integer> Q = new LinkedList<>();
        Q.add(s);
        while (!Q.isEmpty()) {
            int u = Q.remove();
            for (int[] vars : G.getAdjList(u)) {
                int v = vars[0];
                if (colors[v] == Color.WHITE) {
                    colors[v] = Color.GRAY;
                    ds[v] = ds[u] + 1;
                    parents[v] = u;
                    Q.add(v);
                }
            }
            colors[u] = Color.BLACK;
        }

        return new int[][]{ds, parents};
    }

    /**
     * DFS run on G and outputs the discovery times, the finishing times and all
     * the parents in the depth-first tree
     * @param G the input graph
     * @return output, where output[0] is the discovery time array, output[1] is
     * the finishing time array and output[2] is the parent array
     */
    public static int[][] DFS(Graph G) {
        int nrVertices = G.getNrVertices();
        Color[] colors = new Color[nrVertices];
        int[] ds = new int[nrVertices];
        int[] fs = new int[nrVertices];
        int[] parents = new int[nrVertices];

        for (int u = 0; u < nrVertices; u++) {
            colors[u] = Color.WHITE;
            parents[u] = -1;
        }
        int[] time = {0};
        for (int u = 0; u < nrVertices; u++) {
            if (colors[u] == Color.WHITE) {
                DFSVisit(G, u, colors, ds, fs, parents, time);
            }
        }

        return new int[][]{ds, fs, parents};
    }

    private static void DFSVisit(Graph G, int u, Color[] colors, int[] ds,
            int[] fs, int[] parents, int[] time) {
        time[0]++;
        ds[u] = time[0];
        colors[u] = Color.GRAY;
        for (int[] vars : G.getAdjList(u)) {
            int v = vars[0];
            if (colors[v] == Color.WHITE) {
                parents[v] = u;
                DFSVisit(G, v, colors, ds, fs, parents, time);
            }
        }
        colors[u] = Color.BLACK;
        time[0]++;
        fs[u] = time[0];
    }

    /**
     * Bellman-Ford run on G from vertex s and outputs distances from said
     * vertex, whether or not there is a negative-weight cycle and all the
     * parents in the Bellman-Ford tree
     * @param G the input graph
     * @param s the source vertex
     * @return output, where output[0] is the distance array, output[1] tells
     * whether there is a negative-weight cycle; if output[1] == 0,
     * there is no negative-weight cycle and if output[1] == 1 there is
     * a negative-weight cycle. output[2] is the parent array
     */
    public static int[][] BellmanFord(Graph G, int s) {
        checkNrEdgeVariables(G, 1);
        int nrVertices = G.getNrVertices();
        int[] ds = new int[nrVertices];
        int[] parents = new int[nrVertices];

        InitializeSingleSource(G, s, ds, parents);
        for (int i = 0; i < nrVertices - 1; i++) {
            for (int u = 0; u < nrVertices; u++) {
                for (int[] vars : G.getAdjList(u)) {
                    int v = vars[0];
                    int w = vars[1];
                    RelaxBellmanFord(u, v, w, ds, parents);
                }
            }
        }
        for (int u = 0; u < nrVertices; u++) {
            for (int[] vars : G.getAdjList(u)) {
                int v = vars[0];
                int w = vars[1];
                if (ds[v] > ds[u] + w) {
                    return new int[][]{ds, {1}, parents};
                }
            }
        }
        return new int[][]{ds, {0}, parents};
    }

    private static void RelaxBellmanFord(int u, int v, int w, int[] ds,
            int[] parents) {
        if (ds[v] > ds[u] + w) {
            ds[v] = ds[u] + w;
            parents[v] = u;
        }
    }

    /**
     * Dijkstra's run on G from vertex s and outputs distances from said vertex,
     * and all the parents in the Dijkstra's tree
     * @param G the input graph with only positive edge weights
     * @param s the source vertex
     * @return output, where output[0] is the distance array and output[1] is
     * the parent array
     */
    public static int[][] Dijkstra(Graph G, int s) {
        checkNrEdgeVariables(G, 1);
        checkNonPositiveEdgeWeights(G);
        int nrVertices = G.getNrVertices();
        int[] ds = new int[nrVertices];
        int[] parents = new int[nrVertices];

        InitializeSingleSource(G, s, ds, parents);
        List<Integer> S = new LinkedList<>();

        Queue<int[]> Q = new PriorityQueue<>(nrVertices,
                (int[] u, int[] v) -> u[1] - v[1]);
        for (int u = 0; u < nrVertices; u++) {
            Q.add(new int[]{u, ds[u]});
        }

        while (!Q.isEmpty()) {
            int u = Q.remove()[0];
            S.add(u);
            for (int[] vars : G.getAdjList(u)) {
                int v = vars[0];
                int w = vars[1];
                RelaxDijkstra(Q, u, v, w, ds, parents);
            }
        }

        return new int[][]{ds, parents};
    }

    private static void RelaxDijkstra(Queue<int[]> Q, int u,
            int v, int w, int[] ds, int[] parents) {
        if (ds[v] > ds[u] + w) {
            Q.remove(new int[]{v, ds[v]});
            ds[v] = ds[u] + w;
            Q.add(new int[]{v, ds[v]});
            parents[v] = u;
        }
    }

    private static void InitializeSingleSource(Graph G, int s, int[] ds,
            int[] parents) {
        checkVertex(G, s);

        for (int u = 0; u < G.getNrVertices(); u++) {
            ds[u] = Integer.MAX_VALUE;
            parents[u] = -1;
        }
        ds[s] = 0;
    }

    /**
     * Edmonds-Karp's run on G with source s and sink t and outputs the
     * maximum flow graph
     * @param G the input graph with only positive capacities specified
     * @param s the source vertex
     * @param t the sink vertex
     * @return the maximum flow graph as computed by Edmonds-Karp
     */
    public static Graph EdmondsKarp(Graph G, int s, int t) {
        checkVertex(G, s);
        checkVertex(G, t);
        checkEqualSourceAndSink(s, t);
        checkNrEdgeVariables(G, 1);
        checkAntiParallelEdges(G);
        checkNonPositiveEdgeWeights(G);

        G = makeFlowGraph(G);
        Graph Gf = makeResidualGraph(G);

        int[] parents = BFS(Gf, s)[1];
        while (parents[t] != -1) {
            List<int[]> p = getPath(Gf, t, parents);
            p.remove(0);
            int pathResidualCapacity = getPathResidualCapacity(p);

            int u = s;
            for (int[] vars : p) {
                int v = vars[0];
                augmentFlow(G, Gf, u, v, pathResidualCapacity);

                u = v;
            }

            parents = BFS(Gf, s)[1];
        }

        return G;
    }

    /**
     *
     * @param G
     * @param s
     * @param t
     * @return
     */
    public static Graph capacityScaling(Graph G, int s, int t) {
        checkVertex(G, s);
        checkVertex(G, t);
        checkEqualSourceAndSink(s, t);
        checkNrEdgeVariables(G, 1);
        checkAntiParallelEdges(G);
        checkNonPositiveEdgeWeights(G);

        int delta = largestPowerOf2SmallerEqual(getMaxCapacity(G));
        G = makeFlowGraph(G);

        while(delta >= 1) {
            Graph GfDelta = makeResidualGraph(G, delta);

            int[] parents = BFS(GfDelta, s)[1];
            while (parents[t] != -1) {
                List<int[]> p = getPath(GfDelta, t, parents);
                p.remove(0);
                int pathResidualCapacity = getPathResidualCapacity(p);

                int u = s;
                for (int[] vars : p) {
                    int v = vars[0];
                    augmentFlow(G, GfDelta, u, v, pathResidualCapacity);

                    u = v;
                }

                parents = BFS(GfDelta, s)[1];
            }

            delta = delta / 2;
        }

        return G;
    }
    
    private static int largestPowerOf2SmallerEqual(int i) {
        int powerOf2 = 1;
        
        while(i > powerOf2) {
            powerOf2 = powerOf2 * 2;
        }
        
        return i == powerOf2 ? i : powerOf2 / 2;
    }

    private static int getMaxCapacity(Graph G) {
        int nrVertices = G.getNrVertices();

        int maxCapacity = Integer.MIN_VALUE;
        for(int u = 0; u < nrVertices; u++) {
            for(int[] vars : G.getAdjList(u)) {
                maxCapacity = Math.max(maxCapacity, vars[1]);
            }
        }

        return maxCapacity == Integer.MIN_VALUE ? 1 : maxCapacity;
    }

    private static void augmentFlow(Graph G, Graph Gf, int u, int v,
            int pathResidualCapacity) {
        if (G.hasEdge(u, v)) {
            int[] vars = G.getEdgeVariables(u, v);
            int oldF = vars[0];
            int c = vars[1];
            if (oldF == 0) {
                Gf.addEdge(v, u, 0);
            }

            int newF = oldF + pathResidualCapacity;
            G.setEdgeVariable(u, v, 1, newF);

            if (newF == c) {
                Gf.removeEdge(u, v);
            } else {
                Gf.setEdgeVariable(u, v, 1, c - newF);
            }
            Gf.setEdgeVariable(v, u, 1, newF);
        } else {
            int[] vars = G.getEdgeVariables(v, u);
            int oldF = vars[0];
            int c = vars[1];
            if (oldF == c) {
                Gf.addEdge(v, u, 0);
            }

            int newF = oldF - pathResidualCapacity;
            G.setEdgeVariable(v, u, 1, newF);

            if (newF == 0) {
                Gf.removeEdge(u, v);
            } else {
                Gf.setEdgeVariable(u, v, 1, newF);
            }
            Gf.setEdgeVariable(v, u, 1, c - newF);
        }
    }

    /**
     * Computes the path to s based on parents and returns a list of vertices
     * and edges with edge variables
     * @param G the input graph
     * @param s the last vertex of the path
     * @param parents an array with the parent vertex of all vertices. If a
     * vertex does not have a parent, the parent value of said vertex must be -1
     * @return a list of integer arrays. The first integer array is just the
     * first vertex of the path. The other integer arrays are the edges from the
     * previous to the current vertex with edge variables. So [[first vertex],
     * [second vertex] U [edge variables first edge], ... , [last vertex] U
     * [edge variables last edge]].
     */
    public static List<int[]> getPath(Graph G, int s, int[] parents) {
        checkVertex(G, s);
        for (int parent : parents) {
            if (parent != -1) {
                checkVertex(G, parent);
            }
        }

        return getPathRecursive(G, s, parents, 0);
    }

    private static List<int[]> getPathRecursive(Graph G, int s, int[] parents,
            int recursiveCalls) {
        if (parents[s] == -1) {
            List<int[]> p = new LinkedList<>();
            p.add(new int[]{s});
            return p;
        }

        recursiveCalls++;
        if(recursiveCalls < G.getNrVertices()) {
            List<int[]> p = getPathRecursive(G, parents[s], parents,
                    recursiveCalls);
            for (int[] vars : G.getAdjList(parents[s])) {
                if (vars[0] == s) {
                    p.add(vars);
                }
            }
            return p;
        }

        throw new IllegalArgumentException("A cycle has been detected");
    }

    /**
     * Computes the transpose of G and returns it
     * @param G the input graph
     * @return the transpose of the input graph
     */
    public static Graph transpose(Graph G) {
        int nrVertices = G.getNrVertices();
        int nrEdgeVars = G.getNrEdgeVariables();
        Graph GT = new Graph(nrVertices, nrEdgeVars);
        for (int u = 0; u < nrVertices; u++) {
            for (int[] vars : G.getAdjList(u)) {
                int v = vars[0];

                int[] transposedVars = new int[nrEdgeVars + 1];
                transposedVars[0] = u;
                for (int i = 1; i <= nrEdgeVars; i++) {
                    transposedVars[i] = vars[i];
                }

                GT.addEdge(v, transposedVars);
            }
        }

        return GT;
    }

    private static int getPathResidualCapacity(List<int[]> p) {
        int pathResCap = Integer.MAX_VALUE;

        for (int[] vars : p) {
            int cf = vars[1];
            pathResCap = Math.min(pathResCap, cf);
        }

        return pathResCap;
    }

    private static Graph makeFlowGraph(Graph G) {
        int nrVertices = G.getNrVertices();
        Graph GPrime = new Graph(nrVertices, 2);
        for (int u = 0; u < nrVertices; u++) {
            for (int[] vars : G.getAdjList(u)) {
                int v = vars[0];
                int c = vars[1];
                GPrime.addEdge(u, v, 0, c);
            }
        }

        return GPrime;
    }

    private static Graph makeResidualGraph(Graph G) {
        return makeResidualGraph(G, 1);
    }

    private static Graph makeResidualGraph(Graph G, int delta) {
        int nrVertices = G.getNrVertices();
        Graph Gf = new Graph(nrVertices, 1);
        for (int u = 0; u < nrVertices; u++) {
            for (int[] vars : G.getAdjList(u)) {
                int v = vars[0];
                int f = vars[1];
                int c = vars[2];

                if(c >= delta) {
                    if (f != 0) {
                        Gf.addEdge(v, u, f);
                    }
                    if (f != c) {
                        Gf.addEdge(u, v, c - f);
                    }
                }
            }
        }

        return Gf;
    }

    private static void checkEqualSourceAndSink(int s, int t) {
        if(s == t) {
            throw new IllegalArgumentException("The source vertex is equal to "
                    + "the sink vertex");
        }
    }

    private static void checkVertex(Graph G, int u) {
        int nrVertices = G.getNrVertices();
        if (u < 0 || u >= nrVertices) {
            throw new IllegalArgumentException(String.format(
                    "Vertex %d is not a vertex in the graph", u));
        }
    }

    private static void checkNrEdgeVariables(Graph G, int nrEdgeVariables) {
        if (G.getNrEdgeVariables() != nrEdgeVariables) {
            throw new IllegalArgumentException(String.format("There should be "
                    + "%d edge variable(s), but there is/are %d edge variable(s)",
                    nrEdgeVariables, G.getNrEdgeVariables()));
        }
    }

    private static void checkAntiParallelEdges(Graph G) {
        int nrVertices = G.getNrVertices();
        for (int u = 0; u < nrVertices; u++) {
            for (int v = u + 1; v < nrVertices; v++) {
                if (G.hasEdge(u, v) && G.hasEdge(v, u)) {
                    throw new IllegalArgumentException("The input graph in a "
                            + "Ford-Fulkerson method cannot have anti-parallel "
                            + "edges");
                }
            }
        }
    }

    private static void checkNonPositiveEdgeWeights(Graph G) {
        int nrVertices = G.getNrVertices();
        for (int u = 0; u < nrVertices; u++) {
            for (int[] vars : G.getAdjList(u)) {
                int w = vars[1];
                if (w <= 0) {
                    throw new IllegalArgumentException("Non-positive edge "
                            + "weights are not allowed.");
                }
            }
        }
    }
}
