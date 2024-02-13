package Qn_3;
// Question .3(b)
    import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

// Represents an edge in the graph with source, destination, and weight attributes
class Edge {
    int source, destination, weight;

    public Edge(int source, int destination, int weight) {
        this.source = source;
        this.destination = destination;
        this.weight = weight;
    }
}

// Manages the graph and provides methods to add edges and find the MST using Kruskal's algorithm
class Graph {
    private int vertices;
    private List<Edge> edges;

    // Constructor to initialize the graph with the given number of vertices
    public Graph(int vertices) {
        this.vertices = vertices;
        edges = new ArrayList<>();
    }

    // Method to add an edge to the graph with the given source, destination, and weight
    public void addEdge(int source, int destination, int weight) {
        Edge edge = new Edge(source, destination, weight);
        edges.add(edge);
    }

    // Kruskal's algorithm using priority queue (min heap) to find the Minimum Spanning Tree (MST)
    public List<Edge> kruskalMST() {
        List<Edge> result = new ArrayList<>(); // Stores the edges of the MST
        PriorityQueue<Edge> pq = new PriorityQueue<>((a, b) -> a.weight - b.weight); // Priority queue for edge selection

        // Add all edges to the priority queue
        for (Edge edge : edges) {
            pq.offer(edge);
        }

        int[] parent = new int[vertices]; // Array to track the parent of each vertex
        Arrays.fill(parent, -1); // Initialize parent array with -1

        int edgeCount = 0; // Counter to track the number of edges in the MST
        while (edgeCount < vertices - 1 && !pq.isEmpty()) {
            Edge edge = pq.poll(); // Select the edge with the minimum weight from the priority queue
            int x = findParent(parent, edge.source); // Find the parent of the source vertex
            int y = findParent(parent, edge.destination); // Find the parent of the destination vertex

            if (x != y) {
                result.add(edge); // Add the edge to the MST
                union(parent, x, y); // Perform union operation to merge the sets
                edgeCount++; // Increment the edge count
            }
        }

        return result; // Return the list of edges in the MST
    }

    // Method to find the parent of a vertex using path compression
    private int findParent(int[] parent, int vertex) {
        if (parent[vertex] == -1)
            return vertex;
        return findParent(parent, parent[vertex]);
    }

    // Method to perform union operation of two sets
    private void union(int[] parent, int x, int y) {
        int xSet = findParent(parent, x);
        int ySet = findParent(parent, y);
        parent[xSet] = ySet;
    }
}

// Main class
public class KruskalPriorityQueue {
    public static void main(String[] args) {
        int vertices = 4;
        Graph graph = new Graph(vertices); // Create a graph with 4 vertices
        graph.addEdge(0, 1, 10); // Add edges to the graph
        graph.addEdge(0, 2, 6);
        graph.addEdge(0, 3, 5);
        graph.addEdge(1, 3, 15);
        graph.addEdge(2, 3, 4);

        // Find the MST using Kruskal's algorithm with priority queue (min heap) and print the edges in the MST
        List<Edge> result = graph.kruskalMST();
        System.out.println("Edges in MST:");
        for (Edge edge : result) {
            System.out.println(edge.source + " - " + edge.destination + " : " + edge.weight);
        }
    }
}
