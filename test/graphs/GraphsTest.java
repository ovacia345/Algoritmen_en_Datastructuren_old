package graphs;

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
    public void testBFS() {
        System.out.println("BFS");
        Graph G = null;
        int s = 0;
        int[][] expResult = null;
        int[][] result = Graphs.BFS(G, s);
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Test
    public void testDFS() {
        System.out.println("DFS");
        Graph G = null;
        int[][] expResult = null;
        int[][] result = Graphs.DFS(G);
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Test
    public void testBellmanFord() {
        System.out.println("BellmanFord");
        Graph G = null;
        int s = 0;
        int[][] expResult = null;
        int[][] result = Graphs.BellmanFord(G, s);
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Test
    public void testDijkstra() {
        System.out.println("Dijkstra");
        Graph G = null;
        int s = 0;
        int[][] expResult = null;
        int[][] result = Graphs.Dijkstra(G, s);
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Test
    public void testEdmondsKarp() {
        System.out.println("EdmondsKarp");
        Graph G = null;
        int s = 0;
        int t = 0;
        Graph expResult = null;
        Graph result = Graphs.EdmondsKarp(G, s, t);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetPath() {
        System.out.println("getPath");
        Graph G = null;
        int s = 0;
        int[] parents = null;
        List expResult = null;
        List result = Graphs.getPath(G, s, parents);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Test
    public void testTranspose() {
        System.out.println("transpose");
        Graph G = null;
        Graph expResult = null;
        Graph result = Graphs.transpose(G);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
