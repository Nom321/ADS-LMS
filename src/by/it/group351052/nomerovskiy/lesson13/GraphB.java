package by.it.group351052.nomerovskiy.lesson13;

import java.util.*;

public class GraphB {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите структуру орграфа:");
        String input = scanner.nextLine();

        Map<String, List<String>> graph = parseGraph(input);

        boolean hasCycle = detectCycle(graph);

        System.out.println(hasCycle ? "yes" : "no");
    }

    private static Map<String, List<String>> parseGraph(String input) {
        Map<String, List<String>> graph = new HashMap<>();

        String[] edges = input.split(", ");
        for (String edge : edges) {
            String[] nodes = edge.split(" -> ");
            String from = nodes[0];
            String to = nodes[1];

            graph.computeIfAbsent(from, k -> new ArrayList<>()).add(to);
            graph.putIfAbsent(to, new ArrayList<>());
        }

        return graph;
    }

    private static boolean detectCycle(Map<String, List<String>> graph) {
        Set<String> visited = new HashSet<>();
        Set<String> recStack = new HashSet<>();

        for (String node : graph.keySet()) {
            if (detectCycleUtil(graph, node, visited, recStack)) {
                return true;
            }
        }
        return false;
    }

    private static boolean detectCycleUtil(Map<String, List<String>> graph, String node, Set<String> visited, Set<String> recStack) {
        if (recStack.contains(node)) {
            return true;
        }

        if (visited.contains(node)) {
            return false;
        }

        visited.add(node);
        recStack.add(node);

        for (String neighbor : graph.getOrDefault(node, new ArrayList<>())) {
            if (detectCycleUtil(graph, neighbor, visited, recStack)) {
                return true;
            }
        }

        recStack.remove(node);
        return false;
    }
}

