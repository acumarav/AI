package alex.ai01labs.metaheuristic.genetic;

import java.util.Random;

import static alex.ai01labs.metaheuristic.genetic.Constants.*;

public class GeneticAlgorithm {
    private final Random random = new Random();

    public Population evolvePopulation(Population population){
        var newPopulation = new Population(population.size());

        for(int i=0;i<population.size();i++){
            var first =randomSelection(population);
            var second =randomSelection(population);
            var child = crossover(first, second);
            mutate(child );
            newPopulation.saveIndividual(i, child);
        }

        return newPopulation;
    }

    public Individual crossover(Individual parent1, Individual parent2) {
        var child = new Individual();
        for (int i = 0; i < CHROMOSOME_LENGTH; i++) {
            if (Math.random() <= CROSSOVER_RATE) {
                child.setGene(i, parent1.getGene(i));
            } else {
                child.setGene(i, parent2.getGene(i));
            }
        }
        return child;
    }

    private void mutate(Individual individual) {
        for (int i = 0; i < CHROMOSOME_LENGTH; i++) {
            if (Math.random() <= MUTATION_RATE) {
                int gene = random.nextInt(CHROMOSOME_LENGTH);
                individual.setGene(i, gene);
            }
        }
    }

    private Individual randomSelection(Population population) {

        Population newPopulation = new Population(TOURNAMENT_SIZE);
        for (int idx = 0; idx < TOURNAMENT_SIZE; idx++) {
            int rndIndex = random.nextInt(population.size());
            newPopulation.saveIndividual(idx, population.getIndividual(rndIndex));
        }

        var fittest = newPopulation.getFittest();
        return fittest;
    }
}
