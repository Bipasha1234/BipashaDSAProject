package Qn_4_Qn_5;
//Question 5(a)
import java.util.Random;

public class Ant {

    private int[][] distances; // Distance matrix
    private double[][] pheromones; // Pheromone matrix
    private double[][] probabilities; // Transition probabilities matrix
    private int numCities; // Number of cities
    private int numAnts; // Number of ants
    private int maxIterations; // Maximum number of iterations
    private double alpha; // Alpha parameter for balancing pheromone and heuristic information
    private double beta; // Beta parameter for balancing pheromone and heuristic information
    private double evaporationRate; // Rate at which pheromone evaporates
    private int[] bestTour; // Best tour found
    private int bestTourLength; // Length of the best tour found

    public Ant(int[][] distances, int numAnts, int maxIterations, double alpha, double beta, double evaporationRate) {
        this.distances = distances;
        this.numCities = distances.length;
        this.numAnts = numAnts;
        this.maxIterations = maxIterations;
        this.alpha = alpha;
        this.beta = beta;
        this.evaporationRate = evaporationRate;

        pheromones = new double[numCities][numCities];
        probabilities = new double[numCities][numCities];

        // Initialize pheromone levels
        for (int i = 0; i < numCities; i++) {
            for (int j = 0; j < numCities; j++) {
                pheromones[i][j] = 0.1; // Initial pheromone level
            }
        }
    }

    public void solve() {
        for (int iteration = 0; iteration < maxIterations; iteration++) {
            // Create ants and let them construct solutions
            for (int ant = 0; ant < numAnts; ant++) {
                int[] tour = TourCons();
                updatePheromones(tour);
                if (bestTour == null || tourLength(tour) < tourLength(bestTour)) {
                    bestTour = tour.clone();
                    bestTourLength = tourLength(tour);
                }
            }
            // Update pheromones globally
            updatePheromonesGlobal();
        }
    }

    private int[] TourCons() {
        int[] tour = new int[numCities];
        boolean[] visited = new boolean[numCities];
        Random random = new Random();

        // Choose a random starting city
        int startCity = random.nextInt(numCities);
        tour[0] = startCity;
        visited[startCity] = true;

        // Construct the rest of the tour
        for (int i = 1; i < numCities; i++) {
            int prevCity = tour[i - 1];
            calculateProbabilities(prevCity, visited);
            int nextCity = selectAnotherCity(probabilities[prevCity], random.nextDouble());
            tour[i] = nextCity;
            visited[nextCity] = true;
        }

        return tour;
    }

    private void calculateProbabilities(int city, boolean[] visited) {
        double total = 0;
        for (int i = 0; i < numCities; i++) {
            if (!visited[i]) {
                probabilities[city][i] = Math.pow(pheromones[city][i], alpha) * Math.pow(1.0 / distances[city][i], beta);
                total += probabilities[city][i];
            } else {
                probabilities[city][i] = 0;
            }
        }
        // Normalize probabilities
        for (int i = 0; i < numCities; i++) {
            probabilities[city][i] /= total;
        }
    }

    private int selectAnotherCity(double[] probabilities, double randomValue) {
        double cumulativeProbability = 0;
        for (int i = 0; i < probabilities.length; i++) {
            cumulativeProbability += probabilities[i];
            if (randomValue <= cumulativeProbability) {
                return i;
            }
        }
        return -1; // Should never reach here
    }

    private void updatePheromones(int[] tour) {
        // Compute the amount of pheromone to deposit
        double pheromoneDelta = 1.0 / tourLength(tour);

        // Deposit pheromone on the edges of the tour
        for (int i = 0; i < numCities - 1; i++) {
            pheromones[tour[i]][tour[i + 1]] += pheromoneDelta;
            pheromones[tour[i + 1]][tour[i]] += pheromoneDelta;
        }
        // Deposit pheromone on the edge between the last and first cities
        pheromones[tour[numCities - 1]][tour[0]] += pheromoneDelta;
        pheromones[tour[0]][tour[numCities - 1]] += pheromoneDelta;
    }

    private void updatePheromonesGlobal() {
        // Evaporate pheromone
        for (int i = 0; i < numCities; i++) {
            for (int j = 0; j < numCities; j++) {
                pheromones[i][j] *= (1.0 - evaporationRate);
            }
        }
    }

    private int tourLength(int[] tour) {
        int length = 0;
        for (int i = 0; i < tour.length - 1; i++) {
            length += distances[tour[i]][tour[i + 1]];
        }
        // Add distance from the last city back to the starting city
        length += distances[tour[numCities - 1]][tour[0]];
        return length;
    }

    public int[] getBestTour() {
        return bestTour;
    }

    public int getBestTourLength() {
        return bestTourLength;
    }

    public static void main(String[] args) {
        int[][] distances = {
            {0, 10, 15, 20},
            {10, 0, 35, 25},
            {15, 35, 0, 30},
            {20, 25, 30, 0}
        };
        int numAnts = 10;
        int maxIterations = 100;
        double alpha = 1.0;
        double beta = 2.0;
        double evaporationRate = 0.5;

        Ant aco = new Ant(distances, numAnts, maxIterations, alpha, beta, evaporationRate);
        aco.solve();

        System.out.println("Best tour is: " + java.util.Arrays.toString(aco.getBestTour()));
        System.out.println("Length is: " + aco.getBestTourLength());
    }
}
