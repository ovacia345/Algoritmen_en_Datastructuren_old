package graphs;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author N.C.M. van Nistelrooij
 */
public class Graph {
    private final int nrVertices;
    private final int nrEdgeVariables;
    private final List<int[]>[] adjLists;

    /**
     * Initializes graph with nrEdgeVariables edge variables and nrVertices vertices
     * @param nrVertices number of vertices
     * @param nrEdgeVars number of edge variables
     */
    public Graph(int nrVertices, int nrEdgeVars) {
        if (nrVertices >= 0 && nrEdgeVars >= 0) {
            this.nrVertices = nrVertices;
            this.nrEdgeVariables = nrEdgeVars;

            adjLists = new List[nrVertices];
            for (int u = 0; u < nrVertices; u++) {
                adjLists[u] = new LinkedList<>();
            }
        } else {
            throw new IllegalArgumentException("Cannot have a negative number of "
                + "vertices/edge variables.");
        }
    }

    /**
     * Initializes graph with 0 edge variables and nrVertices vertices
     * @param nrVertices number of vertices
     */
    public Graph(int nrVertices) {
        this(nrVertices, 0);
    }

    /**
     * Adds an edge to the graph with src as source, vars[0] as destination and
     * vars[1:vars.length] as edge variables
     * @param src source vertex
     * @param vars integer array of [destination vertex] U [edge variables]
     */
    public void addEdge(int src, int... vars) {
        checkNrEdgeVariables(vars.length - 1);
        checkHasNotEdge(src, vars[0]);
        checkVertex(vars[0]);

        adjLists[src].add(vars);
    }

    /**
     * Change an already existing edge's variables with src as source,
     * newVars[0] as destination and newVars[1:newVars.length] as new edge
     * variables
     * @param src source vertex
     * @param newVars integer array of
     * [destination vertex] U [new edge variables]
     */
    public void setEdgeVariables(int src, int... newVars) {
        checkNrEdgeVariables(newVars.length - 1);

        List<int[]> adjList = getAdjList(src);
        for (int i = 0; i < adjList.size(); i++) {
            int[] oldVars = adjList.get(i);
            if (oldVars[0] == newVars[0]) {
                adjList.set(i, newVars);
                return;
            }
        }

        throw new IllegalArgumentException(String.format(
                "Edge (%d, %d) does not exist"
                + " in the graph", src, newVars[0]));
    }

    /**
     * Change a single already existing edge's variable with src as source,
 dest as destination, edgeVariableNr as edge variable number and value as
 the new value to be assigned to the edge variable
     * @param src source vertex
     * @param dest destination vertex
     * @param edgeVariableNr edge variable number. Must be in [1,nrEdgeVariables]
     * @param value the new value of edge variable edgeVariableNr
     */
    public void setEdgeVariable(int src, int dest, int edgeVariableNr, int value) {
        checkEdgeVariableNr(edgeVariableNr);

        List<int[]> adjList = getAdjList(src);
        for (int i = 0; i < adjList.size(); i++) {
            int[] vars = adjList.get(i);
            if (vars[0] == dest) {
                vars[edgeVariableNr] = value;
                return;
            }
        }

        throw new IllegalArgumentException(String.format(
                "Edge (%d, %d) does not exist"
                + " in the graph", src, dest));
    }

    /**
     * Removes edge (src, dest) if it exists
     * @param src source vertex
     * @param dest destination vertex
     */
    public void removeEdge(int src, int dest) {
        List<int[]> adjList = getAdjList(src);
        for (int i = 0; i < adjList.size(); i++) {
            int[] vars = adjList.get(i);
            if (vars[0] == dest) {
                adjList.remove(i);
                return;
            }
        }

        throw new IllegalArgumentException(String.format(
                "Edge (%d, %d) does not exist"
                + " in the graph", src, dest));
    }

    /**
     * Return whether the edge (src, dest) exists in the graph
     * @param src source vertex
     * @param dest destination vertex
     * @return Whether or not (src, dest) exists in the graph
     */
    public boolean hasEdge(int src, int dest) {
        for (int[] vars : getAdjList(src)) {
            if (vars[0] == dest) {
                return true;
            }
        }

        return false;
    }

    /**
     * Gets edge variable edgeVariableNr of edge (src, dest)
     * @param src source vertex
     * @param dest destination vertex
     * @param edgeVariableNr edge variable number. Must be in [1,nrEdgeVariables]
     * @return edge variable edgeVariableNr of edge (src, dest) if it exists
     */
    public int getEdgeVariable(int src, int dest, int edgeVariableNr) {
        checkEdgeVariableNr(edgeVariableNr);

        return getEdgeVariables(src, dest)[edgeVariableNr - 1];
    }

    /**
     * Gets egde variables of edge (src, dest)
     * @param src source vertex
     * @param dest destination vertex
     * @return edge variables of edge (src, dest) if it exists
     */
    public int[] getEdgeVariables(int src, int dest) {
        for (int[] vars : getAdjList(src)) {
            if (vars[0] == dest) {
                return Arrays.copyOfRange(vars, 1, vars.length);
            }
        }

        throw new IllegalArgumentException(String.format(
                "Edge (%d, %d) does not exist"
                + " in the graph", src, dest));
    }

    /**
     * Gets number of vertices of the graph
     * @return number of vertices
     */
    public int getNrVertices() {
        return nrVertices;
    }

    /**
     * Gets number of edge variables of the graph
     * @return number of edge variables
     */
    public int getNrEdgeVariables() {
        return nrEdgeVariables;
    }

    /**
     * Gets all adjacency lists of the graph
     * @return adjacency lists of the graph
     */
    public List<int[]>[] getAdjLists() {
        return adjLists;
    }

    /**
     * Gets the adjacency list of vertex u
     * @param u the vertex for which the adjacency list will be returned
     * @return the adjacency list of u
     */
    public List<int[]> getAdjList(int u) {
        checkVertex(u);
        return adjLists[u];
    }

    private void checkVertex(int u) {
        if (u < 0 || u >= nrVertices) {
            throw new IllegalArgumentException(String.format(
                    "Vertex %d is not a vertex in the graph", u));
        }
    }

    private void checkHasNotEdge(int src, int dest) {
        if (hasEdge(src, dest)) {
            throw new IllegalArgumentException(String.format("Edge (%d, %d)"
                    + " already exists in the graph.", src, dest));
        }
    }

    private void checkNrEdgeVariables(int nrEdgeVariables) {
        if (this.nrEdgeVariables != nrEdgeVariables) {
            throw new IllegalArgumentException(String.format("There should be "
                    + "%d edge variable(s), but there is/are %d edge variable(s)",
                    this.nrEdgeVariables, nrEdgeVariables));
        }
    }

    private void checkEdgeVariableNr(int edgeVariableNr) {
        if (edgeVariableNr > nrEdgeVariables) {
            throw new IllegalArgumentException(String.format("Edge variable "
                    + "%d does not exist, because there is/are only %d edge "
                    + "variable(s).", edgeVariableNr, nrEdgeVariables));
        }
        if (edgeVariableNr == 0) {
            throw new IllegalArgumentException("Edge variable 0 is the "
                    + "destination vertex and so not an edge variable.");
        }
        if (edgeVariableNr < 0) {
            throw new IllegalArgumentException("Negative edge variable number.");
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (nrEdgeVariables == 0) {
            for (int u = 0; u < nrVertices; u++) {
                for (int[] vars : getAdjList(u)) {
                    sb.append(String.format("%d - %d\n", u, vars[0]));
                }
            }
        } else {
            for (int u = 0; u < nrVertices; u++) {
                for (int[] vars : getAdjList(u)) {
                    sb.append(String.format("%d - %d:\t", u, vars[0]));
                    for (int var = 1; var < nrEdgeVariables; var++) {
                        sb.append(String.format("%d\t", vars[var]));
                    }
                    sb.append(String.format("%d\n", vars[nrEdgeVariables]));
                }
            }
        }

        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Graph) {
            Graph G = (Graph)o;

            if (nrVertices == G.getNrVertices()) {
                for (int u = 0; u < nrVertices; u++) {
                    List<int[]> adjList1 = getAdjList(u);
                    List<int[]> adjList2 = G.getAdjList(u);

                    if (adjList1.size() == adjList2.size()) {
                        for (int i = 0; i < adjList1.size(); i++) {
                            if (!Arrays.equals(adjList1.get(i), adjList2.get(i))) {
                                return false;
                            }
                        }
                    } else {
                        return false;
                    }
                }

                return true;
            }
        }

        return false;
    }
}
