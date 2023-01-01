package alex.ai01labs.metaheuristic.genetic;

import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Individual {

    private final int[] genes = new int[Constants.CHROMOSOME_LENGTH];
    private final Random random = new Random();
    private int fitness;

    public void generateIndividual() {
        for (int index = 0; index < genes.length; index++) {
            genes[index] = random.nextInt(Constants.CHROMOSOME_LENGTH);
        }
    }

    public int getGene(int index) {
        return genes[index];
    }

    public void setGene(int index, int value) {
        genes[index] = value;
        fitness = 0;
    }

    public int getFitness() {
        if (fitness == 0) {
            for (int idx=0; idx < genes.length; idx++) {
                if (genes[idx] == Constants.SOLUTION_SEQ[idx]) {
                    fitness++;
                }
            }
            return fitness;
        }
        return fitness;
    }

    @Override
    public String toString() {
        return String.join("-", IntStream.of(genes).mapToObj(Integer::toString).collect(Collectors.toList()));
    }
}
