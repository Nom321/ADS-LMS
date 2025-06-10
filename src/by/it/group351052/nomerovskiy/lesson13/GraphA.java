package by.it.group351052.nomerovskiy.lesson13;

import java.util.*;

public class GraphA {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите структуру орграфа:");
        String input = scanner.nextLine();

        Map<String, List<String>> graph = parseGraph(input);

        List<String> sortedOrder = topologicalSort(graph);

        for (String node : sortedOrder) {
            System.out.print(node + " ");
        }
    }

    private static Map<String, List<String>> parseGraph(String input) {
        Map<String, List<String>> graph = new HashMap<>();
        Map<String, Integer> inDegree = new HashMap<>();

        String[] edges = input.split(", ");
        for (String edge : edges) {
            String[] nodes = edge.split(" -> ");
            String from = nodes[0];
            String to = nodes[1];

            graph.computeIfAbsent(from, k -> new ArrayList<>()).add(to);
            inDegree.put(to, inDegree.getOrDefault(to, 0) + 1);
            inDegree.putIfAbsent(from, 0);
        }

        for (Map.Entry<String, Integer> entry : inDegree.entrySet()) {
            if (entry.getValue() == 0) {
                graph.putIfAbsent(entry.getKey(), new ArrayList<>());
            }
        }

        return graph;
    }

    private static List<String> topologicalSort(Map<String, List<String>> graph) {
        Map<String, Integer> inDegree = new HashMap<>();
        List<String> sortedOrder = new ArrayList<>();
        PriorityQueue<String> queue = new PriorityQueue<>();

        for (Map.Entry<String, List<String>> entry : graph.entrySet()) {
            String node = entry.getKey();
            inDegree.put(node, 0);
            for (String neighbor : entry.getValue()) {
                inDegree.put(neighbor, inDegree.getOrDefault(neighbor, 0) + 1);
            }
        }

        for (Map.Entry<String, Integer> entry : inDegree.entrySet()) {
            if (entry.getValue() == 0) {
                queue.add(entry.getKey());
            }
        }

        while (!queue.isEmpty()) {
            String current = queue.poll();
            sortedOrder.add(current);

            for (String neighbor : graph.getOrDefault(current, new ArrayList<>())) {
                inDegree.put(neighbor, inDegree.get(neighbor) - 1);
                if (inDegree.get(neighbor) == 0) {
                    queue.add(neighbor);
                }
            }
        }

        if (sortedOrder.size() != inDegree.size()) {
            throw new RuntimeException("Граф содержит цикл");
        }

        return sortedOrder;
    }
}
