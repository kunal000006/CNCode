import java.util.*;

class Node {
    int id;
    Map<Node, Integer> neighbors; // Neighbor node and the cost to reach them

    Node(int id) {
        this.id = id;
        this.neighbors = new HashMap<>();
    }

    void addNeighbor(Node neighbor, int cost) {
        neighbors.put(neighbor, cost);
    }
}

class AODV {
    Map<Integer, Node> nodes;

    AODV() {
        nodes = new HashMap<>();
    }

    void addNode(int id) {
        nodes.put(id, new Node(id));
    }

    void addEdge(int from, int to, int cost) {
        Node fromNode = nodes.get(from);
        Node toNode = nodes.get(to);
        if (fromNode != null && toNode != null) {
            fromNode.addNeighbor(toNode, cost);
            toNode.addNeighbor(fromNode, cost); // Assuming undirected graph
        }
    }

    List<Node> findShortestPath(int startId, int endId) {
        Node start = nodes.get(startId);
        Node end = nodes.get(endId);

        if (start == null || end == null) {
            return Collections.emptyList();
        }

        Map<Node, Integer> distances = new HashMap<>();
        Map<Node, Node> previous = new HashMap<>();
        PriorityQueue<Node> queue = new PriorityQueue<>(Comparator.comparingInt(distances::get));

        for (Node node : nodes.values()) {
            if (node == start) {
                distances.put(node, 0);
            } else {
                distances.put(node, Integer.MAX_VALUE);
            }
            queue.add(node);
        }

        while (!queue.isEmpty()) {
            Node current = queue.poll();
            if (current == end) {
                break;
            }

            for (Map.Entry<Node, Integer> neighborEntry : current.neighbors.entrySet()) {
                Node neighbor = neighborEntry.getKey();
                int cost = neighborEntry.getValue();
                int newDist = distances.get(current) + cost;

                if (newDist < distances.get(neighbor)) {
                    queue.remove(neighbor);
                    distances.put(neighbor, newDist);
                    previous.put(neighbor, current);
                    queue.add(neighbor);
                }
            }
        }

        List<Node> path = new LinkedList<>();
        for (Node at = end; at != null; at = previous.get(at)) {
            path.add(0, at);
        }

        return path.isEmpty() || path.get(0) != start ? Collections.emptyList() : path;
    }

    public static void main(String[] args) {
        AODV aodv = new AODV();
        aodv.addNode(1);
        aodv.addNode(2);
        aodv.addNode(3);
        aodv.addNode(4);
        aodv.addEdge(1, 2, 1);
        aodv.addEdge(2, 3, 2);
        aodv.addEdge(1, 3, 2);
        aodv.addEdge(3, 4, 1);

        List<Node> path = aodv.findShortestPath(1, 4);
        for (Node node : path) {
            System.out.print(node.id + " ");
        }
    }
}
