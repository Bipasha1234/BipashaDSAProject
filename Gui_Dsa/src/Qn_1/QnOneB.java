package Qn_1;
//Question 1(b)
import java.util.Arrays;

public class QnOneB {

    // Method to calculate the minimum time needed to build all engines
    public static int minBuildTime(int[] engines, int splitCost) {
        // Sort the engines in descending order of build time
        Arrays.sort(engines);
        reverseArray(engines); // Reverse the sorted array to get descending order
        
        // Initialize total time and engineers count
        int totalTime = 0;
        int engineers = 1; // Initially, there is only one engineer
        
        // Iterate through each engine
        for (int engineTime : engines) {
            // Check if splitting engineers is beneficial
            if (engineers * splitCost < engineTime) {
                // Split the engineers and calculate the split time
                int splitTime = (engineers - 1) * splitCost;
                // Update the total time with split time plus current engine time
                totalTime += splitTime + engineTime;
                // Update the engineer count by doubling it
                engineers *= 2;
            } else {
                // If splitting engineers is not beneficial, simply add the current engine time
                totalTime += engineTime;
            }
        }
        
        return totalTime; // Return the total time needed to build all engines
    }
    
    // Helper method to reverse an array
    private static void reverseArray(int[] arr) {
        int start = 0;
        int end = arr.length - 1;
        // Swap elements from start and end until reaching the middle of the array
        while (start < end) {
            int temp = arr[start];
            arr[start] = arr[end];
            arr[end] = temp;
            start++;
            end--;
        }
    }

    // Main method to test the minBuildTime function
    public static void main(String[] args) {
        int[] engines = {1, 2, 3}; // Array representing the build time for each engine
        int splitCost = 1; // Cost of splitting engineers
        // Print the minimum time needed to build all engines
        System.out.println(minBuildTime(engines, splitCost));  
    }
}
