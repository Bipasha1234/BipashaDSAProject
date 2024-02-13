import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
//question 2(b)
// Secret Sharing
public class QnTwoB {
   
    // Method to find individuals who will eventually know the secret
    public static List<Integer> findKnownSecret(int n, int[][] intervals, int firstPerson) {
        // Set to store individuals who are known
        Set<Integer> known = new HashSet<>();
        // Queue to keep track of individuals to check
        Queue<Integer> queue = new LinkedList<>();
        
        // Add the first person to the queue
        queue.add(firstPerson);
        
        // Loop until the queue is empty
        while (!queue.isEmpty()) {
            // Get the person from the front of the queue
            int person = queue.poll();
            // Check if the person is already known, if yes, skip
            if (known.contains(person)) {
                continue;
            }
            // Mark the person as known
            known.add(person);
            
            // Iterate through intervals to find connected individuals
            for (int[] interval : intervals) {
                // Get the start and end of the interval
                int start = interval[0];
                int end = interval[1];
                // Check if the person falls within the interval
                if (start <= person && person <= end) {
                    // If yes, add all other individuals except the current person to the queue
                    for (int i = 0; i < n; i++) {
                        if (i != person) {
                            queue.add(i);
                        }
                    }
                }
            }
        }
        
        // Convert the set of known individuals to a sorted list
        List<Integer> result = new ArrayList<>(known);
        Collections.sort(result);
        return result;
    }

    public static void main(String[] args) {
        int n = 5; // Total number of individuals
        int[][] intervals = {{0, 2}, {1, 3}, {2, 4}}; // Intervals representing connections between individuals
        int firstPerson = 1; // Starting person
        
        // Call the method to find individuals who will eventually know the secret
        List<Integer> known = findKnownSecret(n, intervals, firstPerson);
        // Print the result
        System.out.println("Individuals who will eventually know the secret: " + known);
    }
}
