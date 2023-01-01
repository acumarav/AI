package alex.ai01labs.metaheuristic.genetic;

public class GeneticApp {

    public static void main(String[] args){
        GeneticAlgorithm algo = new GeneticAlgorithm();

        Population population = new Population(100);
        population.init();

        int generationCounter = 0;
         while (population.getFittest().getFitness() != Constants.MAX_FITNESS){
             generationCounter++;
             System.out.printf("\tGeneration: %d, fittest is: %d\n", generationCounter, population.getFittest().getFitness());
             System.out.println(population.getFittest());
             population = algo.evolvePopulation(population);
         }

         System.out.println("\nSolution found!!!");
         System.out.println(population.getFittest());

    }
}
