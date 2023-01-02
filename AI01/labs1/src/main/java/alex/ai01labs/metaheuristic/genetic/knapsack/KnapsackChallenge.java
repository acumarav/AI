package alex.ai01labs.metaheuristic.genetic.knapsack;

import org.apache.commons.lang3.tuple.Pair;

import java.util.Arrays;
import java.util.Random;

import static alex.ai01labs.metaheuristic.genetic.knapsack.KnapsackChallenge.*;


public class KnapsackChallenge {
    public static final double CROSSOVER_RATE = 0.02;
    public static final double MUTATION_RATE = 0.001;
    public static long M = 10;
    public static int CHROMOSOME_LENGTH = 6;
    public static int TOURNAMENT_SIZE = 5;
    public static Pair<Long, Double>[] weights = new Pair[]{
            Pair.of(1L, 10.0),
            Pair.of(4L, 14.0),
            Pair.of(2L, 5.0),
            Pair.of(5L, 18.0)};

    public static void main(String[] args) {

        LayoutGeneration gen = new LayoutGeneration(1000);
        gen.init(0);
        //gen.init(787);
        //gen.init(2);
        runAlgo(gen);
    }

    private static void runAlgo(LayoutGeneration gen) {
        System.out.printf("\tInitial fitness: %f for %s\n", gen.getFittest().getFitness(), gen.getFittest());
        GeneticAlgo algo = new GeneticAlgo();
        int noImproveCycles = 0;

        while (noImproveCycles < 10) {
            var newGen = algo.evolveGeneration(gen);
            if (newGen.getFittest().getFitness() <= gen.getFittest().getFitness()) {
                noImproveCycles++;
            } else {
                noImproveCycles = 0;
            }
            gen = newGen;
            System.out.printf("\tGen fitness: %f for %s\n", gen.getFittest().getFitness(), gen.getFittest());
        }

        System.out.println("Evolution stopped!");
        System.out.printf("\tFinal fitness: %f for %s\n", gen.getFittest().getFitness(), gen.getFittest());
    }
}

class GeneticAlgo {
    private final Random random = new Random();

    public Layout crossover(Layout p1, Layout p2) {
        var child = new Layout();
        for (int i = 0; i < CHROMOSOME_LENGTH; i++) {
            if (Math.random() > CROSSOVER_RATE) {
                child.setValue(i, p1.getValue(i));
            } else {
                child.setValue(i, p2.getValue(i));
            }
        }
        return child;
    }

    public void mutate(Layout layout) {
        for (int i = 0; i < CHROMOSOME_LENGTH; i++) {
            if (Math.random() <= MUTATION_RATE) {
                layout.setValue(i, ((int) (Math.random() * 100)) % 2 == 0);
            }
        }
    }

    //return relatively better for random pick
    public Layout randomSelection(LayoutGeneration generation) {
        var tempGen = new LayoutGeneration(TOURNAMENT_SIZE);
        for (int i = 0; i < TOURNAMENT_SIZE; i++) {
            tempGen.setLayout(i, generation.getLayout(random.nextInt(generation.getSize())));
        }
        return tempGen.getFittest();
    }

    public LayoutGeneration evolveGeneration(LayoutGeneration generation) {
        var newGeneration = new LayoutGeneration(generation.getSize());
        for (int i = 0; i < generation.getSize(); i++) {
            var p1 = randomSelection(generation);
            var p2 = randomSelection(generation);
            var child = crossover(p1, p2);
            mutate(child);
            newGeneration.setLayout(i, child);
        }
        return newGeneration;
    }

}


class LayoutGeneration {
    private final int size;
    private final Layout[] layouts;

    public LayoutGeneration(int size) {
        this.size = size;
        layouts = new Layout[size];
    }

    public Layout getLayout(int index) {
        return layouts[index];
    }

    public void setLayout(int index, Layout layout) {
        layouts[index] = layout;
    }

    public Layout getFittest() {
        var fittest = layouts[0];
        for (int i = 0; i < layouts.length; i++) {
            if (fittest.getFitness() <= layouts[i].getFitness()) {
                fittest = layouts[i];
            }
        }
        return fittest;
    }

    public int getSize() {
        return size;
    }

    public void init(int initValue) {
        for (int i = 0; i < size; i++) {
            layouts[i] = new Layout(initValue);

        }

    }
}

class Layout {

    // 0 - No, 1 - Yes, >1 - bad value
    private final int[] chromosome = new int[CHROMOSOME_LENGTH];

    public Layout() {
    }

    public Layout(int initValue) {
        for (int i = 0; i < CHROMOSOME_LENGTH; i++) {
            chromosome[i] = initValue;
        }
    }


    //Fitness - total value, if weight <= M. Bad weight  -10*kgs;
    public double getFitness() {
        long weight = 0;
        double value = 0.0;
        for (int i = 0; i < CHROMOSOME_LENGTH; i++) {
            var ch = chromosome[i];
            if (ch == 0) {
                continue;
            }
            if (ch == 1 && i >= weights.length) {
                value -= 15;
            }
            if (ch == 1 && i < weights.length) {
                weight += weights[i].getLeft();
                value += weights[i].getRight();
            }
            if(ch>1){
                value -=(ch-1);
            }
        }
        if (weight > M) {
            value -= (weight - M) * 15;
        }
        return value;
    }

    public void setValue(int index, boolean val) {
        if (index >= CHROMOSOME_LENGTH) {
            return;
        } else {
            chromosome[index] = val ? 1 : 0;
        }
    }

    public void setValue(int index, int val) {
        if (index >= CHROMOSOME_LENGTH) {
            return;
        } else {
            chromosome[index] = val;
        }
    }

    public int getValue(int index) {
        if (index < CHROMOSOME_LENGTH) {
            return chromosome[index];
        }
        throw new IndexOutOfBoundsException();
    }

    @Override
    public String toString() {
        return String.join("-", Arrays.stream(chromosome).mapToObj(Integer::toString).toArray(String[]::new));
    }
}
//class A
