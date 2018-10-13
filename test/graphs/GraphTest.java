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
public class GraphTest {

    public GraphTest() {
    }

    @Before
    public void setUp() {
    }

    @Rule
    public ExpectedException exception = ExpectedException.none();


    @Test
    public void testAddExistingEdge() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Edge (0, 1) already exists in the graph.");

        Graph G = new Graph(2);
        G.addEdge(0, 1);
        G.addEdge(0, 1);
    }

    @Test
    public void testAddEdgeWithWrongNrOfEdgeVars() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("There should be 0 edge variable(s), but there"
                + " is/are 1 edge variable(s)");

        Graph G = new Graph(2);
        G.addEdge(0, 1, 2);
    }

    @Test
    public void testAddEdgeWithNonExistingVertex() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("The argument vertex is not a vertex in the "
                + "graph");

        Graph G = new Graph(2);
        G.addEdge(1, 2);
    }

    @Test
    public void testAddEdge() {
        Graph G = new Graph(2);

        G.addEdge(0, 1);

        assertEquals(G.toString(), "0 - 1");
    }


    @Test
    public void testSetNonExistingEdgeVariables() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Edge (0, 1) does not exist in the graph");

        Graph G = new Graph(2, 1);
        G.setEdgeVariables(0, 1, 1);
    }

    @Test
    public void testSetUnvalidNrOfEdgeVariables1() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("There should be "
                    + "1 edge variable(s), but there is/are 2 edge variable(s)");

        Graph G = new Graph(2, 1);
        G.addEdge(0, 1, 1);
        G.setEdgeVariables(0, 1, 1, 2);
    }

    @Test
    public void testSetUnvalidNrOfEdgeVariables2() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("There should be "
                    + "1 edge variable(s), but there is/are 0 edge variable(s)");

        Graph G = new Graph(2, 1);
        G.addEdge(0, 1, 1);
        G.setEdgeVariables(0, 1);
    }

    @Test
    public void testSetEdgeVariables() {
        Graph G = new Graph(2, 1);
        G.addEdge(0, 1, 1);
        G.setEdgeVariables(0, 1, 2);

        assertEquals(G.toString(), "0 - 1:\t2");
    }


    @Test
    public void testSetNonExistingEdgeVariable() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Edge (0, 1) does not exist in the graph");

        Graph G = new Graph(2, 1);
        G.setEdgeVariable(0, 1, 1, 2);
    }

    @Test
    public void testSetEdgeNonExistingVariable1() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Edge variable "
                    + "2 does not exist, because there is/are only 1 edge "
                    + "variable(s).");

        Graph G = new Graph(2, 1);
        G.addEdge(0, 1, 1);
        G.setEdgeVariable(0, 1, 2, 2);
    }

    @Test
    public void testSetEdgeNonExistingVariable2() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Edge variable 0 is the "
                    + "destination vertex and so not an edge variable.");

        Graph G = new Graph(2, 1);
        G.addEdge(0, 1, 1);
        G.setEdgeVariable(0, 1, 0, 2);
    }

    @Test
    public void testSetEdgeNonExistingVariable3() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Negative edge variable number.");

        Graph G = new Graph(2, 1);
        G.addEdge(0, 1, 1);
        G.setEdgeVariable(0, 1, -1, 2);
    }

    @Test
    public void testSetEdgeVariable() {
        Graph G = new Graph(2, 1);
        G.addEdge(0, 1, 1);
        G.setEdgeVariable(0, 1, 1, 2);

        assertEquals(G.toString(), "0 - 1:\t2");
    }


    @Test
    public void testRemoveNonExistingEdge() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Edge (0, 1) does not exist in the graph");

        Graph G = new Graph(2);
        G.removeEdge(0, 1);
    }

    @Test
    public void testRemoveEdge() {
        Graph G = new Graph(3);
        G.addEdge(0, 1);
        G.addEdge(0, 2);
        G.removeEdge(0, 1);

        assertEquals(G.toString(), "0 - 2");
    }


    @Test
    public void testHasExistintEdge() {
        Graph G = new Graph(2, 1);
        G.addEdge(0, 1, 2);

        assertTrue(G.hasEdge(0, 1));
    }

    @Test
    public void testHasNonExistingEdge() {
        Graph G = new Graph(2, 1);
        G.addEdge(0, 1, 2);

        assertFalse(G.hasEdge(0, 2));
    }


    @Test
    public void testGetNonExistingEdgeVariable() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Edge (0, 1) does not exist in the graph");

        Graph G = new Graph(2, 1);
        G.getEdgeVariable(0, 1, 1);
    }

    @Test
    public void testGetEdgeNonExistingVariable1() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Edge variable "
                    + "2 does not exist, because there is/are only 1 edge "
                    + "variable(s).");

        Graph G = new Graph(2, 1);
        G.addEdge(0, 1, 1);
        G.getEdgeVariable(0, 1, 2);
    }

    @Test
    public void testGetEdgeNonExistingVariable2() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Edge variable 0 is the "
                    + "destination vertex and so not an edge variable.");

        Graph G = new Graph(2, 1);
        G.addEdge(0, 1, 1);
        G.getEdgeVariable(0, 1, 0);
    }

    @Test
    public void testGetEdgeNonExistingVariable3() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Negative edge variable number.");

        Graph G = new Graph(2, 1);
        G.addEdge(0, 1, 1);
        G.getEdgeVariable(0, 1, -1);
    }

    @Test
    public void testGetEdgeVariable() {
        Graph G = new Graph(2, 2);
        G.addEdge(0, 1, 2, 3);

        assertEquals(G.getEdgeVariable(0, 1, 2), 3);
    }


    @Test
    public void testGetNonExistingEdgeVariables() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Edge (0, 1) does not exist in the graph");

        Graph G = new Graph(2, 1);
        G.getEdgeVariables(0, 1);
    }

    @Test
    public void testGetEdgeVariables() {
        Graph G = new Graph(2, 2);
        G.addEdge(0, 1, 2, 3);
        int[] vars = G.getEdgeVariables(0, 1);

        assertTrue(Arrays.equals(vars, new int[]{2, 3}));
    }


    @Test
    public void testGetNonExistingAdjList() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("The argument vertex is not a vertex in the graph");

        Graph G = new Graph(2);
        List<int[]> adjList = G.getAdjList(2);
    }

    @Test
    public void testGetAdjList() {
        Graph G = new Graph(3, 2);
        G.addEdge(0, 1, 2, 3);
        G.addEdge(0, 2, 4, 5);

        List<int[]> adjList = G.getAdjList(0);

        assertTrue(Arrays.equals(adjList.get(0), new int[]{1, 2, 3}));
        assertTrue(Arrays.equals(adjList.get(1), new int[]{2, 4, 5}));
    }


    @Test
    public void testNoEdgeVariablesToString() {
        Graph G = new Graph(5);
        G.addEdge(0, 4);
        G.addEdge(4, 1);
        G.addEdge(3, 4);
        G.addEdge(2, 3);
        G.addEdge(0, 0);

        assertEquals(G.toString(), "0 - 4\n0 - 0\n2 - 3\n3 - 4\n4 - 1");
    }

    @Test
    public void testEdgeVariablesToString() {
        Graph G = new Graph(5, 2);
        G.addEdge(0, 4, 1, 2);
        G.addEdge(4, 1, 3, 4);
        G.addEdge(3, 4, 5, 6);
        G.addEdge(2, 3, 7, 8);
        G.addEdge(0, 0, 9, 10);

        assertEquals(G.toString(), "0 - 4:\t1\t2\n0 - 0:\t9\t10\n2 - 3:\t7\t8"
                + "\n3 - 4:\t5\t6\n4 - 1:\t3\t4");
    }


    @Test
    public void testEquals1() {
        Graph G1 = new Graph(2);
        Graph G2 = new Graph(3);

        assertFalse(G1.equals(G2));
    }

    @Test
    public void testEquals2() {
        Graph G1 = new Graph(2, 1);
        Graph G2 = new Graph(2, 2);

        assertTrue(G1.equals(G2));
    }

    @Test
    public void testEquals3() {
        Graph G1 = new Graph(3, 1);
        G1.addEdge(0, 1, 2);
        G1.addEdge(0, 2, 3);

        Graph G2 = new Graph(3, 1);
        G2.addEdge(0, 1, 2);

        assertFalse(G1.equals(G2));
    }

    @Test
    public void testEquals4() {
        Graph G1 = new Graph(3, 1);
        G1.addEdge(0, 1, 2);
        G1.addEdge(0, 2, 3);
        G1.addEdge(2, 0, 4);

        Graph G2 = new Graph(3, 1);
        G2.addEdge(0, 1, 2);
        G2.addEdge(0, 2, 3);
        G2.addEdge(2, 0, 5);

        assertFalse(G1.equals(G2));
    }

    @Test
    public void testEquals5() {
        Graph G1 = new Graph(3, 1);
        G1.addEdge(0, 1, 2);
        G1.addEdge(0, 2, 3);
        G1.addEdge(2, 0, 4);

        Graph G2 = new Graph(3, 1);
        G2.addEdge(0, 1, 2);
        G2.addEdge(0, 2, 3);
        G2.addEdge(2, 0, 4);

        assertTrue(G1.equals(G2));
    }
}
