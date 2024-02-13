import java.util.PriorityQueue;
//QUESTION NO. 4(B)
// Represents a node in a binary search tree
class TreeNode {
    int val; // Value of the node
    TreeNode left, right; // References to the left and right child nodes

    // Constructor to initialize the node with a given value
    public TreeNode(int val) {
        this.val = val;
    }
}

// Class to find the x number of values that are closest to the target value in a binary search tree
public class BST {
    // Method to find the x closest values to the given target in the binary search tree
    public static int[] closestValues(TreeNode root, double target, int x) {
        // Initialize a max heap priority queue to keep track of the x closest values to the target
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>((a, b) -> Double.compare(Math.abs(b - target), Math.abs(a - target)));

        // Perform an in-order traversal of the tree to collect the closest values
        inorderTraversal(root, target, maxHeap, x);

        // Create an array to store the x closest values
        int[] result = new int[x];
        for (int i = 0; i < x; i++) {
            result[i] = maxHeap.poll(); // Retrieve the closest values from the max heap
        }

        return result; // Return the array of x closest values
    }

    // Method to perform in-order traversal of the binary search tree and collect the closest values
    private static void inorderTraversal(TreeNode root, double target, PriorityQueue<Integer> maxHeap, int x) {
        if (root == null) {
            return;
        }

        inorderTraversal(root.left, target, maxHeap, x); // Traverse the left subtree

        maxHeap.offer(root.val); // Add the current node's value to the max heap
        if (maxHeap.size() > x) {
            maxHeap.poll(); // Remove the value with the maximum absolute difference if the heap size exceeds x
        }

        inorderTraversal(root.right, target, maxHeap, x); // Traverse the right subtree
    }

    // Main method to test the closestValues function
    public static void main(String[] args) {
        // Construct the binary search tree
        TreeNode root = new TreeNode(4);
        root.left = new TreeNode(2);
        root.right = new TreeNode(5);
        root.left.left = new TreeNode(1);
        root.left.right = new TreeNode(3);

        // Input
        double k = 3.8; // Target value
        int x = 2; // Number of closest values to find

        // Find the x closest values to the target k
        int[] closestValues = closestValues(root, k, x);

        // Output the closest values to the target
        System.out.println("Closest values to " + k + " are: ");
        for (int value : closestValues) {
            System.out.print(value + " ");
        }
    }
}
