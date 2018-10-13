package graphs;

import java.util.Arrays;
import java.util.List;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 *
 * @author N.C.M. van Nistelrooij
 */
public class GraphsTest {

    public GraphsTest() {
    }

    @Before
    public void setUp() {
    }

    @Rule
    public ExpectedException exception = ExpectedException.none();


    @Test
    public void testBFSNonExistingVertex() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Vertex 2 is not a vertex in the graph");

        Graph G = new Graph(2);
        int[][] BFSOutput = Graphs.BFS(G, 2);
    }

    @Test
    public void testBFSNoEdges() {
        Graph G = new Graph(2);
        int[][] BFSOutput = Graphs.BFS(G, 0);

        assertTrue(Arrays.equals(BFSOutput[0], new int[]{0, Integer.MAX_VALUE}));
        assertTrue(Arrays.equals(BFSOutput[1], new int[]{-1, -1}));
    }

    @Test
    public void testBFS() {
        Graph G = new Graph(3);
        G.addEdge(0, 1);
        G.addEdge(1, 2);
        G.addEdge(0, 2);

        int[][] BFSOutput = Graphs.BFS(G, 0);

        assertTrue(Arrays.equals(BFSOutput[0], new int[]{0, 1, 1}));
        assertTrue(Arrays.equals(BFSOutput[1], new int[]{-1, 0, 0}));
    }


    @Test
    public void testDFSNoEdges() {
        Graph G = new Graph(2);
        int[][] DFSOutput = Graphs.DFS(G);

        assertTrue(Arrays.equals(DFSOutput[0], new int[]{1, 3}));
        assertTrue(Arrays.equals(DFSOutput[1], new int[]{2, 4}));
        assertTrue(Arrays.equals(DFSOutput[2], new int[]{-1, -1}));
    }

    @Test
    public void testDFS() {
        Graph G = new Graph(3);
        G.addEdge(0, 1);
        G.addEdge(1, 2);
        G.addEdge(0, 2);

        int[][] DFSOutput = Graphs.DFS(G);

        assertTrue(Arrays.equals(DFSOutput[0], new int[]{1, 2, 3}));
        assertTrue(Arrays.equals(DFSOutput[1], new int[]{6, 5, 4}));
        assertTrue(Arrays.equals(DFSOutput[2], new int[]{-1, 0, 1}));
    }


    @Test
    public void testBellmanFordTooManyEdgeVariables() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("There should be 1 edge variable(s), "
                + "but there is/are 2 edge variable(s)");

        Graph G = new Graph(2, 2);
        int[][] DFSOutput = Graphs.BellmanFord(G, 0);
    }

    @Test
    public void testBellmanFordTooFewEdgeVariables() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("There should be 1 edge variable(s), "
                + "but there is/are 0 edge variable(s)");

        Graph G = new Graph(2);
        int[][] DFSOutput = Graphs.BellmanFord(G, 0);
    }

    @Test
    public void testBellmanFordNonExistingVertex() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Vertex 2 is not a vertex in the graph");

        Graph G = new Graph(2, 1);
        int[][] DFSOutput = Graphs.BellmanFord(G, 2);
    }

    @Test
    public void testBellmanFordNegativeWeightCycle() {
        Graph G = new Graph(3, 1);
        G.addEdge(0, 1, 1);
        G.addEdge(1, 2, 2);
        G.addEdge(2, 0, -4);

        int[][] BellmanFordOutput = Graphs.BellmanFord(G, 0);

        assertTrue(Arrays.equals(BellmanFordOutput[0], new int[]{-2, 0, 2}));
        assertTrue(Arrays.equals(BellmanFordOutput[1], new int[]{1}));
        assertTrue(Arrays.equals(BellmanFordOutput[2], new int[]{2, 0, 1}));
    }

    @Test
    public void testBellmanFordNoEdges() {
        Graph G = new Graph(3, 1);
        int[][] BellmanFordOutput = Graphs.BellmanFord(G, 0);

        assertTrue(Arrays.equals(BellmanFordOutput[0], new int[]{0,
        Integer.MAX_VALUE, Integer.MAX_VALUE}));
        assertTrue(Arrays.equals(BellmanFordOutput[1], new int[]{0}));
        assertTrue(Arrays.equals(BellmanFordOutput[2], new int[]{-1, -1, -1}));
    }

    @Test
    public void testBellmanFord() {
        Graph G = new Graph(3, 1);
        G.addEdge(0, 1, 1);
        G.addEdge(1, 2, 3);
        G.addEdge(2, 0, 2);

        int[][] BellmanFordOutput = Graphs.BellmanFord(G, 0);

        assertTrue(Arrays.equals(BellmanFordOutput[0], new int[]{0, 1, 4}));
        assertTrue(Arrays.equals(BellmanFordOutput[1], new int[]{0}));
        assertTrue(Arrays.equals(BellmanFordOutput[2], new int[]{-1, 0, 1}));
    }


    @Test
    public void testDijkstraTooManyEdgeVariables() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("There should be 1 edge variable(s), "
                + "but there is/are 2 edge variable(s)");

        Graph G = new Graph(2, 2);
        int[][] DijkstraOutput = Graphs.Dijkstra(G, 0);
    }

    @Test
    public void testDijkstraTooFewEdgeVariables() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("There should be 1 edge variable(s), "
                + "but there is/are 0 edge variable(s)");

        Graph G = new Graph(2);
        int[][] DijkstraOutput = Graphs.Dijkstra(G, 0);
    }

    @Test
    public void testDijkstraNonExistingVertex() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Vertex 2 is not a vertex in the graph");

        Graph G = new Graph(2, 1);
        int[][] DijkstraOutput = Graphs.Dijkstra(G, 2);
    }

    @Test
    public void testDijkstraNegativeEdgeWeights() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Non-positive edge weights are not allowed.");

        Graph G = new Graph(2, 1);
        G.addEdge(0, 1, -1);

        int[][] DijkstraOutput = Graphs.Dijkstra(G, 0);
    }

    @Test
    public void testDijkstraZeroEdgeWeights() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Non-positive edge weights are not allowed.");

        Graph G = new Graph(2, 1);
        G.addEdge(0, 1, 0);

        int[][] DijkstraOutput = Graphs.Dijkstra(G, 0);
    }

    @Test
    public void testDijkstraNoEdges() {
        Graph G = new Graph(3, 1);
        int[][] DijkstraOutput = Graphs.Dijkstra(G, 0);

        assertTrue(Arrays.equals(DijkstraOutput[0], new int[]{0,
        Integer.MAX_VALUE, Integer.MAX_VALUE}));
        assertTrue(Arrays.equals(DijkstraOutput[1], new int[]{-1, -1, -1}));
    }

    @Test
    public void testDijkstra() {
        Graph G = new Graph(3, 1);
        G.addEdge(0, 1, 1);
        G.addEdge(1, 2, 3);
        G.addEdge(2, 0, 2);

        int[][] DijkstraOutput = Graphs.Dijkstra(G, 0);

        assertTrue(Arrays.equals(DijkstraOutput[0], new int[]{0, 1, 4}));
        assertTrue(Arrays.equals(DijkstraOutput[1], new int[]{-1, 0, 1}));
    }


    @Test
    public void testEdmondsKarpTooManyEdgeVariables() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("There should be 1 edge variable(s), "
                + "but there is/are 2 edge variable(s)");

        Graph G = new Graph(2, 2);
        Graph GStar = Graphs.EdmondsKarp(G, 0, 1);
    }

    @Test
    public void testEdmondsKarpTooFewEdgeVariables() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("There should be 1 edge variable(s), "
                + "but there is/are 0 edge variable(s)");

        Graph G = new Graph(2);
        Graph GStar = Graphs.EdmondsKarp(G, 0, 1);
    }

    @Test
    public void testEdmondsKarpNonExistingVertex1() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Vertex 2 is not a vertex in the graph");

        Graph G = new Graph(2, 1);
        Graph GStar = Graphs.EdmondsKarp(G, 2, 1);
    }

    @Test
    public void testEdmondsKarpNonExistingVertex2() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Vertex 2 is not a vertex in the graph");

        Graph G = new Graph(2, 1);
        Graph GStar = Graphs.EdmondsKarp(G, 1, 2);
    }

    @Test
    public void testEdmondsKarpEqualSourceSink() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("The source vertex is equal to the"
                + " sink vertex");

        Graph G = new Graph(2, 1);
        Graph GStar = Graphs.EdmondsKarp(G, 0, 0);
    }

    @Test
    public void testEdmondsKarpNegativeEdgeWeights() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Non-positive edge weights are not allowed.");

        Graph G = new Graph(2, 1);
        G.addEdge(0, 1, -1);

        Graph GStar = Graphs.EdmondsKarp(G, 0, 1);
    }

    @Test
    public void testEdmondsKarpZeroEdgeWeights() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Non-positive edge weights are not allowed.");

        Graph G = new Graph(2, 1);
        G.addEdge(0, 1, 0);

        Graph GStar = Graphs.EdmondsKarp(G, 0, 1);
    }

    @Test
    public void testEdmondsKarpAntiParallelEdges() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("The input graph in a Ford-Fulkerson method "
                + "cannot have anti-parallel edges");

        Graph G = new Graph(2, 1);
        G.addEdge(0, 1, 2);
        G.addEdge(1, 0, 1);

        Graph GStar = Graphs.EdmondsKarp(G, 0, 1);
    }

    @Test
    public void testEdmondsKarpNoEdges() {
        Graph G = new Graph(3, 1);
        Graph GStar = Graphs.EdmondsKarp(G, 0, 2);

        assertEquals(G, GStar);
    }

    @Test
    public void testEdmondsKarp() {
        Graph G = new Graph(4, 1);
        G.addEdge(0, 1, 3);
        G.addEdge(0, 2, 2);
        G.addEdge(1, 2, 2);
        G.addEdge(1, 3, 2);
        G.addEdge(2, 3, 3);

        Graph result = Graphs.EdmondsKarp(G, 0, 3);

        Graph expectedResult = new Graph(4, 2);
        expectedResult.addEdge(0, 1, 3, 3);
        expectedResult.addEdge(0, 2, 2, 2);
        expectedResult.addEdge(1, 2, 1, 2);
        expectedResult.addEdge(1, 3, 2, 2);
        expectedResult.addEdge(2, 3, 3, 3);

        assertEquals(result, expectedResult);
    }


    @Test
    public void testGetPathNonExistingVertex() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Vertex 2 is not a vertex in the graph");

        Graph G = new Graph(2);
        List<int[]> p = Graphs.getPath(G, 2, new int[]{-1, -1});
    }

    @Test
    public void testGetPathNegativeParent() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Vertex -2 is not a vertex in the graph");

        Graph G = new Graph(2);
        List<int[]> p = Graphs.getPath(G, 1, new int[]{-2, -1});
    }

    @Test
    public void testGetPathNonExistingParent() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Vertex 2 is not a vertex in the graph");

        Graph G = new Graph(2);
        List<int[]> p = Graphs.getPath(G, 1, new int[]{2, -1});
    }

    @Test
    public void testGetPathNoParents() {
        Graph G = new Graph(2);
        List<int[]> p = Graphs.getPath(G, 0, new int[]{-1, -1});

        assertEquals(p.size(), 1);
        assertTrue(Arrays.equals(p.get(0), new int[]{0}));
    }

    @Test
    public void testGetPathCycle() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("A cycle has been detected");

        Graph G = new Graph(3);
        List<int[]> p = Graphs.getPath(G, 0, new int[]{2, 0, 1});
    }

    @Test
    public void testGetPath() {
        Graph G = new Graph(3, 2);
        G.addEdge(0, 1, 3, 4);
        G.addEdge(1, 2, 4, 5);
        
        List<int[]> p =  Graphs.getPath(G, 2, new int[]{-1, 0, 1});

        assertEquals(p.size(), 3);
        assertTrue(Arrays.equals(p.get(0), new int[]{0}));
        assertTrue(Arrays.equals(p.get(1), new int[]{1, 3, 4}));
        assertTrue(Arrays.equals(p.get(2), new int[]{2, 4, 5}));
    }

    @Test
    public void testTransposeTranposed() {
        Graph G = new Graph(3, 2);
        G.addEdge(0, 1, 2, 3);
        G.addEdge(0, 2, 3, 4);
        G.addEdge(2, 1, 6, 7);
        G.addEdge(1, 2, 9, 10);

        Graph GTransposed = Graphs.transpose(G);

        Graph GTransposedTransposed = Graphs.transpose(GTransposed);

        assertEquals(G, GTransposedTransposed);
    }
    
    @Test
    public void testTranposeNoEdges() {
        Graph G = new Graph(3);
        
        Graph GTransposed = Graphs.transpose(G);
        
        assertEquals(G, GTransposed);
    }

    @Test
    public void testTranspose() {
        Graph G = new Graph(3, 2);
        G.addEdge(0, 1, 2, 3);
        G.addEdge(0, 2, 3, 4);
        G.addEdge(2, 1, 6, 7);
        G.addEdge(1, 2, 9, 10);

        Graph GTransposedResult = Graphs.transpose(G);

        Graph GTransposedExpectedResult = new Graph(3, 2);
        GTransposedExpectedResult.addEdge(1, 0, 2, 3);
        GTransposedExpectedResult.addEdge(2, 0, 3, 4);
        GTransposedExpectedResult.addEdge(1, 2, 6, 7);
        GTransposedExpectedResult.addEdge(2, 1, 9, 10);

        assertEquals(GTransposedResult, GTransposedExpectedResult);
    }
}
