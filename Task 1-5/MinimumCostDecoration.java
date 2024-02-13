
//question no.1 (a)
public class MinimumCostDecoration {
    public static int minCostToDecorate(int[][] costs) {
        if (costs == null || costs.length == 0 || costs[0].length == 0) return 0;

        int n = costs.length;
        int k = costs[0].length;

        // Initialize dp table to store minimum costs
        int[][] dp = new int[n][k];

        // Copy the first row as it is from costs matrix
        for (int j = 0; j < k; j++) {
            dp[0][j] = costs[0][j];
        }

        // Iterate through the venues starting from the second one
        for (int i = 1; i < n; i++) {
            // Iterate through the available themes
            for (int j = 0; j < k; j++) {
                // Initialize min_cost for the current venue and theme
                int minCost = Integer.MAX_VALUE;
                // Iterate through the available themes for the previous venue
                for (int p = 0; p < k; p++) {
                    // Skip if the previous venue had the same theme
                    if (j == p) continue;
                    // Update minCost with the minimum of current minCost and previous venue's cost
                    minCost = Math.min(minCost, dp[i - 1][p]);
                }
                // Update the current venue's minimum cost
                dp[i][j] = costs[i][j] + minCost;
            }
        }

        // Find the minimum cost from the last row
        int minCost = Integer.MAX_VALUE;
        for (int j = 0; j < k; j++) {
            minCost = Math.min(minCost, dp[n - 1][j]);
        }

        return minCost;
    }

    public static void main(String[] args) {
        int[][] costs = {{1, 3, 2}, {4, 6, 8}, {3, 1, 5}};
        System.out.println("Minimum Cost: " + minCostToDecorate(costs)); // Output: 7
    }
}
