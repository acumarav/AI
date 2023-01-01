package alex.ai01labs.metaheuristic.genetic;

public class Population {

    private final Individual[] individuals;

    public Population(int populationSize) {
        individuals = new Individual[populationSize];
    }

    public void init() {
        for (int idx = 0; idx < individuals.length; idx++) {
            Individual individual = new Individual();
            individual.generateIndividual();
            saveIndividual(idx, individual);
        }
    }

    public Individual getIndividual(int index) {
        return individuals[index];
    }

    public int size() {
        return individuals.length;
    }

    public void saveIndividual(int idx, Individual individual) {
        individuals[idx] = individual;
    }

    /**
     * The most fit individual
     */
    public Individual getFittest() {
        var fittest = individuals[0];
        for (int idx = 1; idx < individuals.length; idx++) {
            if (individuals[idx].getFitness() >= fittest.getFitness()) {
                fittest = individuals[idx];
            }
        }
        return fittest;
    }
}
