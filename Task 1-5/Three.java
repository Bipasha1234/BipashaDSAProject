
// Question no.3(a)
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
public class Three {        //ScoreTracker
 

    // Initialize a list to store assignment scores
    private List<Double> scores;

    // Constructor to initialize ScoreTracker object
    public Three() {
        scores = new ArrayList<>();
    }

    // Method to add a new assignment score to the data stream
    public void addScore(double score) {
        scores.add(score);
        // Sort the list after adding a new score
        Collections.sort(scores);
    }

    // Method to calculate and return the median of all assignment scores
    public double getMedianScore() {
        int size = scores.size();
        // Check if the number of scores is even
        if (size % 2 == 0) {
            // If even, return the average of the two middle scores
            int midIndex = size / 2;
            return (scores.get(midIndex - 1) + scores.get(midIndex)) / 2.0;
        } else {
            // If odd, return the middle score
            return scores.get(size / 2);
        }
    }

    public static void main(String[] args) {
        // Example usage
       Three scoreTracker = new Three();
        scoreTracker.addScore(85.5);
        scoreTracker.addScore(92.3);
        scoreTracker.addScore(77.8);
        scoreTracker.addScore(90.1);
        double median1 = scoreTracker.getMedianScore(); 
        System.out.println("Median 1: " + median1);
        scoreTracker.addScore(81.2);
        scoreTracker.addScore(88.7);
        double median2 = scoreTracker.getMedianScore(); 
        System.out.println("Median 2: " + median2);
    }
}

    
