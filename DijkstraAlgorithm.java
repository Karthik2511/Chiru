import java.util.*;

class Graph {
    private final Map<String, List<Edge>> vertices = new HashMap<>();

    public void addVertex(String label, List<Edge> edges) {
        vertices.put(label, edges);
    }

    public Map<String, Integer> dijkstra(String startVertex) {
        Map<String, Integer> distances = new HashMap<>();
        PriorityQueue<Edge> priorityQueue = new PriorityQueue<>();
        Map<String, String> shortestPathTree = new HashMap<>();

        // Initialize distances with infinity
        for (String vertex : vertices.keySet()) {
            distances.put(vertex, Integer.MAX_VALUE);
        }
        distances.put(startVertex, 0);
        priorityQueue.add(new Edge(startVertex, 0));

        while (!priorityQueue.isEmpty()) {
            Edge edge = priorityQueue.poll();
            String currentVertex = edge.getDestination();
            int currentDistance = edge.getWeight();

            // Skip processing if we found a better path
            if (currentDistance > distances.get(currentVertex)) {
                continue;
            }

            // Explore neighbors
            for (Edge neighbor : vertices.getOrDefault(currentVertex, new ArrayList<>())) {
                int distance = currentDistance + neighbor.getWeight();

                // Only consider this new path if it's better
                if (distance < distances.get(neighbor.getDestination())) {
                    distances.put(neighbor.getDestination(), distance);
                    priorityQueue.add(new Edge(neighbor.getDestination(), distance));
                    shortestPathTree.put(neighbor.getDestination(), currentVertex);
                }
            }
        }

        System.out.println("Shortest path tree: " + shortestPathTree);
        return distances;
    }

    static class Edge implements Comparable<Edge> {
        private final String destination;
        private final int weight;

        public Edge(String destination, int weight) {
            this.destination = destination;
            this.weight = weight;
        }

        public String getDestination() {
            return destination;
        }

        public int getWeight() {
            return weight;
        }

        @Override
        public int compareTo(Edge other) {
            return Integer.compare(this.weight, other.weight);
        }
    }
}

public class DijkstraAlgorithm {
    public static void main(String[] args) {
        Graph graph = new Graph();

        graph.addVertex("A", Arrays.asList(
            new Graph.Edge("B", 1),
            new Graph.Edge("C", 4)
        ));
        graph.addVertex("B", Arrays.asList(
            new Graph.Edge("A", 1),
            new Graph.Edge("C", 2),
            new Graph.Edge("D", 5)
        ));
        graph.addVertex("C", Arrays.asList(
            new Graph.Edge("A", 4),
            new Graph.Edge("B", 2),
            new Graph.Edge("D", 1)
        ));
        graph.addVertex("D", Arrays.asList(
            new Graph.Edge("B", 5),
            new Graph.Edge("C", 1)
        ));

        String startVertex = "A";
        Map<String, Integer> distances = graph.dijkstra(startVertex);

        System.out.println("Shortest distances from vertex " + startVertex + ": " + distances);
    }
}
