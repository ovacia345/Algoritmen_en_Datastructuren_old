package graphs;

/**
 *
 * @author N.C.M. van Nistelrooij
 */
public class Main {
    public static void main(String[] args) {
        Graph G = new Graph(2);

        G.addEdge(0, 1);
    }

    public static void testEdmondsKarp() {
        Graph G = new Graph(10, 1);
        G.addEdge(0, 1, 50);
        G.addEdge(0, 2, 36);
        G.addEdge(0, 3, 11);
        G.addEdge(0, 4, 8);

        G.addEdge(1, 5, 50);
        G.addEdge(1, 6, 50);
        G.addEdge(1, 7, 50);
        G.addEdge(1, 8, 50);

        G.addEdge(2, 6, 36);
        G.addEdge(2, 8, 36);

        G.addEdge(3, 7, 11);
        G.addEdge(3, 8, 11);

        G.addEdge(4, 8, 8);

        G.addEdge(5, 9, 45);
        G.addEdge(6, 9, 42);
        G.addEdge(7, 9, 10);
        G.addEdge(8, 9, 3);

        long before = System.currentTimeMillis();

        Graph GStar = Graphs.EdmondsKarp(G, 0, 9);

        long after = System.currentTimeMillis();
        System.out.println(String.format("Edmonds-Karp time: %dms", after
                - before));

        before = System.currentTimeMillis();

        int[][] BellmanFordOutput = Graphs.BellmanFord(G, 0);

        after = System.currentTimeMillis();
        System.out.println(String.format("Bellman-Ford time: %dms", after
                - before));
    }

    public static void testEdmondsKarp2() {
        Graph G = new Graph(6, 1);
        G.addEdge(0, 1, 4);
        G.addEdge(0, 2, 5);
        G.addEdge(0, 3, 6);
        G.addEdge(0, 4, 7);

        G.addEdge(1, 5, 10);

        G.addEdge(2, 1, 6);
        G.addEdge(2, 3, 2);

        G.addEdge(3, 4, 7);

        G.addEdge(4, 5, 13);

        System.out.println("Original graph:");
        System.out.println(G);

        G = Graphs.EdmondsKarp(G, 0, 5);
        System.out.println("Maximum flow graph:");
        System.out.println(G);
    }

    public static void testEdmondsKarp3() {
        Graph G = new Graph(6, 1);
        G.addEdge(0, 1, 5);
        G.addEdge(0, 2, 6);
        G.addEdge(0, 3, 7);
        G.addEdge(0, 4, 8);

        G.addEdge(1, 5, 11);

        G.addEdge(2, 1, 7);
        G.addEdge(2, 3, 3);

        G.addEdge(3, 4, 8);

        G.addEdge(4, 5, 14);

        System.out.println("Original graph:");
        System.out.println(G);

        G = Graphs.EdmondsKarp(G, 0, 5);
        System.out.println("Maximum flow graph:");
        System.out.println(G);
    }
}
