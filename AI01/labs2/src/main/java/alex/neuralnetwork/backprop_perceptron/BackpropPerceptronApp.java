package alex.neuralnetwork.backprop_perceptron;

/**
 * Backpropagation Perceptron network examples OR, AND, XOR functions
 */
public class BackpropPerceptronApp {

    private static final float LEARNING_RATE = 0.3f;
    private static final float MOMENTUM = 0.6f;
    private static final int ITERATIONS = 100_000;

    private static float[][] trainingResultsAND = new float[][]{
            new float[]{0},
            new float[]{0},
            new float[]{0},
            new float[]{1}
    };

    private static float[][] trainingResultsXOR = new float[][]{
            new float[]{0},
            new float[]{1},
            new float[]{1},
            new float[]{0}
    };

    public static void main(String[] args) {
        float[][] trainData = {{0, 0}, {0, 1}, {1, 0}, {1, 1}};

        var trainingResults = trainingResultsXOR;

        //Run training
        BackpropNeuralNetwork network = new BackpropNeuralNetwork(2, 3, 1);

        for (int iteration = 1; iteration <= ITERATIONS; iteration++) {


            if (iteration < 10 || iteration % 50 == 0) {
                System.out.println("\nIteration: " + iteration);
                for (int i = 0; i < trainingResults.length; i++) {
                    float[] t = trainData[i];
                    System.out.printf("%.1f, %.1f --> %.3f\n ", t[0], t[1], network.run(t)[0]);
                }
            }

            for (int i = 0; i < trainingResults.length; i++) {
                network.train(trainData[i], trainingResults[i], LEARNING_RATE, MOMENTUM);
            }
        }
    }
}
