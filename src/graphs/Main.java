package graphs;

/**
 *
 * @author N.C.M. van Nistelrooij
 */
public class Main {
    public static void main(String[] args) {
        testEdmondsKarp();
        testCapacityScaling();
    }

    private static void testEdmondsKarp() {
        Graph G = new Graph(8, 1);
        G.addEdge(0, 1, 10);
        G.addEdge(0, 4, 10);
        G.addEdge(1, 2, 10);
        G.addEdge(2, 3, 10);
        G.addEdge(3, 7, 10);
        G.addEdge(4, 3, 1);
        G.addEdge(4, 5, 10);
        G.addEdge(5, 6, 10);
        G.addEdge(6, 7, 10);

        long before = System.currentTimeMillis();

        Graph GStar = Graphs.EdmondsKarp(G, 0, 7);

        long after = System.currentTimeMillis();
        System.out.println(String.format("Edmonds-Karp time: %dms", after
                - before));
    }

    private static void testCapacityScaling() {
        Graph G = new Graph(8, 1);
        G.addEdge(0, 1, 10);
        G.addEdge(0, 4, 10);
        G.addEdge(1, 2, 10);
        G.addEdge(2, 3, 10);
        G.addEdge(3, 7, 10);
        G.addEdge(4, 3, 1);
        G.addEdge(4, 5, 10);
        G.addEdge(5, 6, 10);
        G.addEdge(6, 7, 10);

        long before = System.currentTimeMillis();

        Graph GStar = Graphs.capacityScaling(G, 0, 7);

        long after = System.currentTimeMillis();
        System.out.println(String.format("Capacity Scaling time: %dms", after
                - before));
    }
}
