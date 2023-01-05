package alex.ai01labs.metaheuristic.swarm;

import static alex.ai01labs.metaheuristic.swarm.Constants.NUM_DIMENSIONS;
import static java.util.Arrays.copyOf;

public class Particle {

    private final double[] position; // xi -> (x,y)
    private final double[] velocity;
    private double[] bestPosition;

    public Particle(double[] position, double[] velocity) {
        this.position = copyOf(position, NUM_DIMENSIONS);
        this.velocity = copyOf(velocity, NUM_DIMENSIONS);
        this.bestPosition = new double[NUM_DIMENSIONS];
    }

    public double[] getPosition() {
        return position;
    }

    public double[] getVelocity() {
        return velocity;
    }

    public double[] getBestPosition() {
        return bestPosition;
    }

    public void setBestPosition(double[] newBestPosition) {
        bestPosition = copyOf(newBestPosition, NUM_DIMENSIONS);
    }

    public void checkBestSolution(double[] globalBestSolution) {
        if (Constants.f(this.bestPosition) < Constants.f(globalBestSolution)) {
            globalBestSolution = this.bestPosition;
        }
    }

    @Override
    public String toString() {
        return String.format("Best position so fat: %f - %f", bestPosition[0], bestPosition[1]);
    }
}
