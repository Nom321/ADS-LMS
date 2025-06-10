package by.it.group351052.nomerovskiy.lesson13;

import java.util.*;

public class GraphC {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите структуру орграфа:");
        String input = scanner.nextLine();

        Map<String, List<String>> graph = parseGraph(input);

        List<List<String>> scc = findStronglyConnectedComponents(graph);

        for (List<String> component : scc) {
            component.sort(Comparator.naturalOrder());
            for (String node : component) {
                System.out.print(node);
            }
            System.out.println();
        }
    }

    private static Map<String, List<String>> parseGraph(String input) {
        Map<String, List<String>> graph = new HashMap<>();

        String[] edges = input.split(", ");
        for (String edge : edges) {
            String[] nodes = edge.split("->");
            String from = nodes[0];
            String to = nodes[1];

            graph.computeIfAbsent(from, k -> new ArrayList<>()).add(to);
            graph.putIfAbsent(to, new ArrayList<>());
        }

        return graph;
    }

    private static List<List<String>> findStronglyConnectedComponents(Map<String, List<String>> graph) {
        Map<String, List<String>> reverseGraph = new HashMap<>();
        for (String node : graph.keySet()) {
            reverseGraph.putIfAbsent(node, new ArrayList<>());
            for (String neighbor : graph.get(node)) {
                reverseGraph.computeIfAbsent(neighbor, k -> new ArrayList<>()).add(node);
            }
        }

        List<String> topologicalOrder = new ArrayList<>();
        Set<String> visited = new HashSet<>();

        for (String node : graph.keySet()) {
            if (!visited.contains(node)) {
                dfs1(graph, node, visited, topologicalOrder);
            }
        }

        visited.clear();
        List<List<String>> scc = new ArrayList<>();

        while (!topologicalOrder.isEmpty()) {
            String node = topologicalOrder.remove(topologicalOrder.size() - 1);
            if (!visited.contains(node)) {
                List<String> component = new ArrayList<>();
                dfs2(reverseGraph, node, visited, component);
                scc.add(component);
            }
        }

        return scc;
    }

    private static void dfs1(Map<String, List<String>> graph, String node, Set<String> visited, List<String> topologicalOrder) {
        visited.add(node);
        for (String neighbor : graph.getOrDefault(node, new ArrayList<>())) {
            if (!visited.contains(neighbor)) {
                dfs1(graph, neighbor, visited, topologicalOrder);
            }
        }
        topologicalOrder.add(node);
    }

    private static void dfs2(Map<String, List<String>> graph, String node, Set<String> visited, List<String> component) {
        visited.add(node);
        component.add(node);
        for (String neighbor : graph.getOrDefault(node, new ArrayList<>())) {
            if (!visited.contains(neighbor)) {
                dfs2(graph, neighbor, visited, component);
            }
        }
    }
}
