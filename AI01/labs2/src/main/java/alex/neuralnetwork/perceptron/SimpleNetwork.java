package alex.neuralnetwork.perceptron;

/**
 * Simple 2 layers perceptron with no hidden layers. Not capable to solve non-linear tasks.
 */
public class SimpleNetwork {

    private final float[] weights;
    private final int numOfWeights;
    private final float[][] inputFeatures;
    private final float[] inputLabels;

    public SimpleNetwork(float[][] inputFeatures, float[] inputLabels) {
        this.inputFeatures = inputFeatures;
        this.inputLabels = inputLabels;
        this.numOfWeights = inputFeatures[0].length;
        this.weights = new float[numOfWeights];
        initializeWeights();
    }

    private void initializeWeights() {
        //init weights randomly
        for (int i = 0; i < numOfWeights; i++) {
            weights[i] = (float) (Math.random() - 0.5);
        }
    }

    public float calculateOutput(float[] input) {
        float netInput = 0f;
        for (int i = 0; i < input.length; i++) {
            netInput = netInput + input[i] * weights[i];
        }
        return ActivationFunction.apply(netInput);
    }

    public void train(float learningRate) {
        float totalError = -1f;
        while (totalError != 0) {
            totalError = 0;
            for (int i = 0; i < inputLabels.length; i++) {
                float calculatedOutput = calculateOutput(inputFeatures[i]);
                float error = inputLabels[i] - calculatedOutput;
                totalError += error;

                //Update weights base of error
                for (int j = 0; j < numOfWeights; j++) {
                    weights[j] = weights[j] + learningRate * error * inputFeatures[i][j];
                }
            }
            System.out.printf("\t Keep on training the network, error is: %f \n", totalError);
        }
    }
}
