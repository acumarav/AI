package alex.ai01labs.metaheuristic.swarm;

import static alex.ai01labs.metaheuristic.swarm.Constants.*;
import static java.util.Arrays.copyOf;

/**
 * Solve particle swarm minimum for the function:
 * <p>
 * 3D Surface plotter
 * * @see <a href='https://academo.org/demos/3d-surface-plotter/?expression=exp(-x*x-y*y)*sin(x)&xRange=-2%2C2&yRange=-2%2C2&resolution=88'>Function example</a>
 * <a> </a>
 * </p>
 */
public class ParticleSwarmOptimization {

    private final Particle[] particleSwarm;
    private double[] globalBestSolutions;
    private int epochs;

    public ParticleSwarmOptimization() {
        this.globalBestSolutions = new double[NUM_DIMENSIONS];
        this.particleSwarm = new Particle[NUM_PARTICLES];
        generateRandomSolution();
    }

    public void solve() {
        //init
        for (int i = 0; i < NUM_PARTICLES; i++) {
            double[] locations = initializeLocation();
            double[] velocities = initializeVelocity();
            this.particleSwarm[i] = new Particle(locations, velocities);
        }

        while (epochs < MAX_ITERATIONS) {
            for (Particle actual : particleSwarm) {
                //update the velocities
                for (int i = 0; i < actual.getVelocity().length; i++) {
                    double rp = Math.random();
                    double rg = Math.random();
                    actual.getVelocity()[i] = w * actual.getVelocity()[i] +
                            c1 * rp * (actual.getBestPosition()[i] - actual.getPosition()[i])
                            + c2 * rg * (globalBestSolutions[i] - actual.getPosition()[i]);
                }

                //update the positions
                for (int i = 0; i < actual.getPosition().length; i++) {
                    actual.getPosition()[i] += actual.getVelocity()[i];

                    if (actual.getPosition()[i] < MIN) {
                        actual.getPosition()[i] = MIN;
                    } else if (actual.getPosition()[i] > MAX) {
                        actual.getPosition()[i] = MAX;
                    }
                }

                if (f(actual.getPosition()) < f(actual.getBestPosition())) {
                    actual.setBestPosition(actual.getPosition());
                }

                if (f(actual.getBestPosition()) < f(globalBestSolutions)) {
                    globalBestSolutions = copyOf(actual.getBestPosition(), NUM_DIMENSIONS);
                }
            }
            System.out.println("Epoch: " +epochs++);
        }
    }

    private void generateRandomSolution() {
        for (int i = 0; i < NUM_DIMENSIONS; i++) {
            double randomCoordinate = random(MIN, MAX);
            this.globalBestSolutions[i] = randomCoordinate;
        }
    }

    private double[] initializeLocation() {
        double x = random(MIN, MAX);
        double y = random(MIN, MAX);
        return new double[]{x, y};
    }

    private double[] initializeVelocity() {
        double vx = random(-MIN - MAX, MAX - MIN);
        double vy = random(-MIN - MAX, MAX - MIN);
        return new double[]{vx, vy};

    }

    public void showSolution() {
        System.out.printf("\n\t Solution for PSO problem: %f - %f", this.globalBestSolutions[0], this.globalBestSolutions[1]);
        System.out.printf("\n\t\t f(x,y) = %f", Constants.f(this.globalBestSolutions));
    }

    private double random(double min, double max) {
        return min + (max - min) * Math.random();
    }
}
