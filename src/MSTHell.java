import java.util.*;
public class MSTHell {
    static class Edge implements Comparable<Edge> {
        private final int u, v;
        private int weight;
        private int T;

        public Edge(int u, int v, int weight) {
            this.u = u;
            this.v = v;
            this.weight = weight;
            this.T = 2;
        }

        public int either() {
            return u;
        }

        public int other(int vertex) {
            if (vertex == u) {
                return v;
            } else if (vertex == v) {
                return u;
            } else {
                throw new IllegalArgumentException("Illegal endpoint");
            }
        }

        public int weight() {
            return weight;
        }


        public int compareTo(Edge other) {
            int weightComp = Integer.compare(weight, other.weight);
            if (weightComp != 0) {
                return weightComp;
            } else {
                return Integer.compare(T, other.T);
            }
        }
    }

    static class Graph {
        private final int V;
        private final List<Edge>[] adj;

        public Graph(int V) {
            this.V = V;
            adj = new List[V];
            for (int v = 0; v < V; v++) {
                adj[v] = new ArrayList<>();
            }
        }

        public void addEdge(int u, int v, int weight) {
            Edge e = new Edge(u, v, weight);
            adj[u].add(e);
            adj[v].add(e);
        }

        public Iterable<Edge> adj(int v) {
            return adj[v];
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int testCaseAmount = scanner.nextInt();
        for (int i = 0; i < testCaseAmount; i++) {
            int rowCount = scanner.nextInt();
            int colCount = scanner.nextInt();
            int startRow = scanner.nextInt() - 1;
            int startCol = scanner.nextInt() - 1;
            int endRow = scanner.nextInt() - 1;
            int endCol = scanner.nextInt() - 1;

            int[][] grid = new int[rowCount][colCount];
            for (int row = 0; row < rowCount; row++) {
                for (int col = 0; col < colCount; col++) {
                    grid[row][col] = scanner.nextInt();
                }
            }

            int minimumCost = findMinimumCost(grid, startRow, startCol, endRow, endCol);

            System.out.println(minimumCost);
        }



    }
    public static int findMinimumCost(int[][] grid, int startRow, int startCol, int endRow, int endCol) {
        int rowCount = grid.length;
        int colCount = grid[0].length;

        // Create a graph of the grid, where each cell is a node
        Graph graph = new Graph(rowCount * colCount);
        for (int row = 0; row < rowCount; row++) {
            for (int col = 0; col < colCount; col++) {
                int nodeIndex = row * colCount + col;
                if (row > 0) {
                    // Add vertical edge to the cell above
                    int aboveIndex = (row - 1) * colCount + col;
                    int edgeCost = grid[row][col] ^ grid[row-1][col];
                    graph.addEdge(nodeIndex, aboveIndex, edgeCost);
                }
                if (col > 0) {
                    // Add horizontal edge to the cell to the left
                    int leftIndex = row * colCount + (col - 1);
                    int edgeCost = grid[row][col] ^ grid[row][col-1];
                    graph.addEdge(nodeIndex, leftIndex, edgeCost);
                }
            }
        }

        // Find the MST of the graph using Prim's algorithm
        PriorityQueue<Edge> pq = new PriorityQueue<>();
        boolean[] visited = new boolean[rowCount * colCount];
        visited[startRow * colCount + startCol] = true;
        for (Edge e : graph.adj[startRow * colCount + startCol]) {
            pq.add(e);
        }
        int totalCost = 0;
        while (!pq.isEmpty()) {
            Edge e = pq.remove();
            int u = e.either();
            int v = e.other(u);
            if (visited[u] && visited[v]) continue;
            visited[u] = true;
            visited[v] = true;
            totalCost += e.weight();
            for (Edge f : graph.adj[v]) {
                if (!visited[f.other(v)]) {
                    pq.add(f);
                }
            }
        }

        return totalCost;
    }


}