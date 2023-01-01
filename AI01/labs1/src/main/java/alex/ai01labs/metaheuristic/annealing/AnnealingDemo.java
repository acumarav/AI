package alex.ai01labs.metaheuristic.annealing;

import java.util.Random;

import static java.lang.Math.pow;


/**
 * Simulated annealing algorithm
 */
public class AnnealingDemo {
    public static final double MIN_COORDINATE_X = -2;
    public static final double MAX_COORDINATE_X = 2;
    public static final double MIN_TEMPERATURE = 1;
    public static final double MAX_TEMPERATURE = 100;
    public static final double COOLING_RATE = 0.02;
    private final Random randomGenerator = new Random();
    private double currentCoordinateX;
    private double nextCoordinateX;
    private double bestCoordinateX;

    public static void main(String[] args) {
        AnnealingDemo simulation = new AnnealingDemo();
        simulation.findOptimum();
        simulation.findOptimum();
        simulation.findOptimum();
    }

    public void findOptimum() {
        double temp = MAX_TEMPERATURE;
        while (temp > MIN_TEMPERATURE) {
            nextCoordinateX = getRandomX();//generate neighboring state
            double currentEnergy = getEnergy(currentCoordinateX);
            double newEnergy = getEnergy(nextCoordinateX);

            if (acceptanceProbability(currentEnergy, newEnergy, temp) > Math.random()) {
                currentCoordinateX = nextCoordinateX;
            }
            if (f(currentCoordinateX) < f(bestCoordinateX)) {
                bestCoordinateX = currentCoordinateX;
            }

            temp = temp * (1 - COOLING_RATE);
        }
        System.out.printf("\n\tGlobal extremum is: x = %f, f(x) = %f", bestCoordinateX, f(bestCoordinateX));
    }

    private double getRandomX() {
        //random value in [-2,2]
        return randomGenerator.nextDouble() * (MAX_COORDINATE_X - MIN_COORDINATE_X) + MIN_COORDINATE_X;
    }

    private double getEnergy(double x) {
        return f(x);
    }

    private double f(double x) {
        return pow(x - 0.3, 3) - 5 * x + x * x - 2;
    }

    //so-called metropolis-function
    private double acceptanceProbability(double actualEnergy, double newEnergy, double temp) {
        if (newEnergy < actualEnergy) {//new state is better  - accept it
            return 1.0;
        }

        return Math.exp((actualEnergy - newEnergy) / temp);
    }
}
