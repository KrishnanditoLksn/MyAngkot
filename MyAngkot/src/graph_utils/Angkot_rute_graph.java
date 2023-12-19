package graph_utils;

import java.util.*;

/**
 *
 * @author Michael  Diva berliano
 */
public class Angkot_rute_graph {
    private final int V;
    private final String[] nodeNames;
    private final int[][] adjMatrix;

    public Angkot_rute_graph(int V) {
        this.V = V;
        this.nodeNames = new String[V];
        this.adjMatrix = new int[V][V];
    }

    public void addEdge(String source, String destination, int weight) {
        int sourceIndex = getNodeIndex(source);
        int destinationIndex = getNodeIndex(destination);

        adjMatrix[sourceIndex][destinationIndex] = weight;
        adjMatrix[destinationIndex][sourceIndex] = weight;
    }

    public List<String> findShortestPath(String source, String destination) {
        int sourceIndex = getNodeIndex(source);
        int destinationIndex = getNodeIndex(destination);

        int[] distance = new int[V];
        Arrays.fill(distance, Integer.MAX_VALUE);

        PriorityQueue<Terminal> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(node -> node.jarak));
        distance[sourceIndex] = 0;
        priorityQueue.add(new Terminal(sourceIndex, 0));

        while (!priorityQueue.isEmpty()) {
            int u = priorityQueue.poll().vertex;

            for (int v = 0; v < V; v++) {
                int weight = adjMatrix[u][v];

                if (weight > 0 && distance[u] + weight < distance[v]) {
                    distance[v] = distance[u] + weight;
                    priorityQueue.add(new Terminal(v, distance[v]));
                }
            }
        }

        List<String> path = new ArrayList<>();
        int current = destinationIndex;

        while (current != sourceIndex) {
            path.add(getNodeName(current));
            current = findPreviousNode(current, distance);
        }

        path.add(source);
        Collections.reverse(path);

        return path;
    }

    private String getNodeName(int index) {
        return nodeNames[index];
    }

    private int getNodeIndex(String node) {
        for (int i = 0; i < V; i++) {
            if (nodeNames[i] != null && nodeNames[i].equals(node)) {
                return i;
            }
        }

        // If the node is not found, add the node to the array and return its index
        for (int i = 0; i < V; i++) {
            if (nodeNames[i] == null) {
                nodeNames[i] = node;
                return i;
            }
        }

        // If the array is full and the node is not found, return an error value
        return -1;
    }

    private int findPreviousNode(int current, int[] distance) {
        for (int v = 0; v < V; v++) {
            int weight = adjMatrix[current][v];

            if (weight > 0 && distance[current] == distance[v] + weight) {
                return v;
            }
        }
        return -1;
    }
}
