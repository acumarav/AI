package alex.ai01labs.metaheuristic.annealing;

public class SimulatedAnnealing {
    private final TourMutator mutator = new TourMutator();
    private SingleTour actualState;
    private SingleTour nextState;
    private SingleTour bestState;

    public static void main(String[] args) {
        SimulatedAnnealing annealing = new SimulatedAnnealing();

        annealing.simulate();
    }

    public void simulate() {
        double temp = PostmanConstants.MAX_TEMPERATURE;
        actualState = mutator.generateIndividual();
        bestState = actualState;

        System.out.printf("\n\tInitial solution distance: %.2f\n\t%s", actualState.calcTourDistance(), actualState);

        while (temp > PostmanConstants.MIN_TEMPERATURE) {
            nextState = mutator.generateNeighboringState(actualState);
            double currEnergy = actualState.calcTourDistance();
            double neighborEnergy = nextState.calcTourDistance();

            if (acceptanceProbability(currEnergy, neighborEnergy, temp) > Math.random()) {
                temp *= 1 - PostmanConstants.COOLING_RATE;
                actualState = nextState;
                if (actualState.calcTourDistance() < bestState.calcTourDistance()) {
                    bestState = new SingleTour(actualState.getTour());
                    System.out.printf("\n\tBetter distance: %.2f", actualState.calcTourDistance());
                }
            }
        }

        System.out.printf("\n\tOptimized solution distance: %.2f\n\t%s", bestState.calcTourDistance(), bestState);

    }

    private double acceptanceProbability(double curEnergy, double neighborEnergy, double temp) {
        if (neighborEnergy < curEnergy) {
            return 1.0;
        }
        return Math.exp((curEnergy - neighborEnergy) / temp);
    }
}
