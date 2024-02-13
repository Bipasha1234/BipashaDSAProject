
//Question no 2(a)
public class QnTwo {

    // Method to calculate the minimum moves required to equalize dresses in all machines
    public static int minMovesToEqualize(int[] machines) {
        // Initialize variables to store total dresses, number of machines, moves, and balance
        int totalDresses = 0;
        int numMachines = machines.length;
        int moves = 0;
        int balance = 0;

        // Calculate the total number of dresses in all machines
        for (int machine : machines) {
            totalDresses += machine;
        }

        // Check if it's possible to equalize the number of dresses
        if (totalDresses % numMachines != 0) {
            return -1; // Return -1 if not possible
        }

        // Calculate the target number of dresses per machine
        int target = totalDresses / numMachines;

        // Iterate through machines to calculate moves required
        for (int dresses : machines) {
            // Calculate the balance of dresses (actual dresses - target dresses)
            balance += dresses - target;
            // Calculate the absolute value of balance as moves required
            moves = Math.max(moves, Math.abs(balance));
        }

        return moves; // Return the minimum moves required
    }

    // Main method to test the minMovesToEqualize function
    public static void main(String[] args) {
        int[] machines = {1, 0, 5}; // Array representing the number of dresses in each machine
        System.out.println(minMovesToEqualize(machines)); // Output: 3
    }
}


// public class QnTwo {

//     public static int equalizeDresses(int[] machines) {
//         int n = machines.length;
//         int total = 0;
//         for (int dress : machines) {
//             total += dress;
//         }

//         if (total % n != 0) {
//             return -1; // Not possible to equalize
//         }

//         int target = total / n;
//         int moves = 0;

//         for (int i = 0; i < n; i++) {
//             int diff = target - machines[i];
//             if (diff > 0 && i < n - 1) {
//                 // Pass dresses to the right
//                 moves += Math.min(diff, machines[i + 1]);
//                 machines[i + 1] -= Math.min(diff, machines[i + 1]);
//             } else if (diff < 0 && i > 0) {
//                 // Pass dresses to the left
//                 moves += Math.min(-diff, machines[i - 1]);
//                 machines[i - 1] -= Math.min(-diff, machines[i - 1]);
//             }
//         }

//         return moves;
//     }

//     public static void main(String[] args) {
//         int[] machines = {1, 0, 5};
//         int moves = equalizeDresses(machines);
//         System.out.println("Minimum moves required: " + moves);
//     }
// }
