

//QUESTION 5(B)
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

public class Device {

    public static List<Integer> findImpactedDevices(int[][] edges, int targetDevice) {
        // Create a hashmap to store connections for each device
        Map<Integer, List<Integer>> connections = new HashMap<>();
        for (int[] edge : edges) {
            int device1 = edge[0];
            int device2 = edge[1];
            connections.putIfAbsent(device1, new ArrayList<>());
            connections.putIfAbsent(device2, new ArrayList<>());
            connections.get(device1).add(device2);
            connections.get(device2).add(device1);
        }

        // Perform Depth-First Search to find reachable devices
        Set<Integer> visited = new HashSet<>();
        Stack<Integer> stack = new Stack<>();
        stack.push(targetDevice);
        while (!stack.isEmpty()) {
            int device = stack.pop();
            if (!visited.contains(device)) {
                visited.add(device);
                // Only add neighbors that are not the target device itself
                for (int neighbor : connections.get(device)) {
                    if (neighbor != targetDevice) {
                        stack.push(neighbor);
                    }
                }
            }
        }

        // Return the list of impacted devices (excluding target device)
        List<Integer> impactedDevices = new ArrayList<>(visited);
        impactedDevices.remove(targetDevice);
        return impactedDevices;
    }

    public static void main(String[] args) {
        int[][] edges = {{0, 1}, {0, 2}, {1, 3}, {1, 6}, {2, 4}, {4, 6}, {4, 5}, {5, 7}};
        int targetDevice = 4;
        List<Integer> impactedDevices = findImpactedDevices(edges, targetDevice);
        System.out.println("Impacted devices: " + impactedDevices);
    }
}
