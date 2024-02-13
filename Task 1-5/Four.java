import java.util.ArrayDeque;
import java.util.Queue;
//questipn 4 (A)
public class   Four {

    // Directions: Up, Down, Left, Right
    static final int[][] DIRECTIONS = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

    // Method to find the minimum number of steps to collect all keys in the maze
    public static int minStepsToCollectAllKeys(char[][] grid) {
        int m = grid.length; // Number of rows in the grid
        int n = grid[0].length; // Number of columns in the grid

        // Initialize variables to track visited positions and collected keys
        boolean[][][] visited = new boolean[m][n][64]; // 64 represents all possible combinations of keys (2^6)
        int keys = 0; // Variable to keep track of collected keys

        // Initialize starting position and queue for BFS
        int startX = -1, startY = -1; // Starting position coordinates
        Queue<int[]> queue = new ArrayDeque<>(); // Queue for BFS traversal

        // Find the starting position marked as 'S' in the grid
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 'S') {
                    startX = i;
                    startY = j;
                }
            }
        }
        
        // Add the starting position to the queue along with the initial collected keys
        queue.offer(new int[]{startX, startY, 0});
        visited[startX][startY][0] = true; // Mark the starting position as visited

        // Perform BFS traversal to explore the maze
        int steps = 0; // Variable to count the number of steps taken
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                int[] curr = queue.poll(); // Get the current position from the queue
                int x = curr[0], y = curr[1], collectedKeys = curr[2]; // Extract coordinates and collected keys

                // Check if all keys are collected
                if (collectedKeys == (1 << 6) - 1) { // If all 6 keys are collected
                    return steps; // Return the number of steps taken
                }

                // Explore all four directions from the current position
                for (int[] dir : DIRECTIONS) {
                    int nx = x + dir[0]; // New x-coordinate after moving in the current direction
                    int ny = y + dir[1]; // New y-coordinate after moving in the current direction
                    if (nx >= 0 && nx < m && ny >= 0 && ny < n && grid[nx][ny] != 'W') { // Check if the new position is within the grid and not a wall
                        char c = grid[nx][ny]; // Get the character at the new position
                        if (c == '.' || c == 'S' || Character.isLowerCase(c) || (Character.isUpperCase(c) && (collectedKeys & (1 << (c - 'A'))) != 0)) { // Check if the new position is a valid move
                            int newCollectedKeys = collectedKeys; // Update the collected keys if a new key is found
                            if (Character.isLowerCase(c)) { // If the new position contains a lowercase letter (key)
                                newCollectedKeys |= (1 << (c - 'a')); // Update collected keys to indicate the key is collected
                            }
                            if (!visited[nx][ny][newCollectedKeys]) { // Check if the new position with the updated collected keys is not visited
                                visited[nx][ny][newCollectedKeys] = true; // Mark the new position with the updated collected keys as visited
                                queue.offer(new int[]{nx, ny, newCollectedKeys}); // Add the new position with the updated collected keys to the queue for further exploration
                            }
                        }
                    }
                }
            }
            steps++; // Increment the number of steps taken
        }

        // If all keys cannot be collected and the exit cannot be reached
        return -1; // Return -1 to indicate it's impossible to collect all keys and reach the exit
    }

    // Main method to test the minStepsToCollectAllKeys function
    public static void main(String[] args) {
        // Example maze represented as a 2D grid
        char[][] grid = {
            {'S', 'P', 'q', 'P', 'P'},
            {'W', 'W', 'W', 'P', 'W'},
            {'r', 'P', 'Q', 'P', 'R'}
        };

        // Call the minStepsToCollectAllKeys function and print the output
        System.out.println(minStepsToCollectAllKeys(grid)); // Output: -1 (Impossible to collect all keys and reach the exit)
    }
}
