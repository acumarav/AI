package alex.neuralnetwork.backprop_perceptron;

public class ActivationFunc {

    public static float sigmoid(float x) {
        return (float) (1 / (1 + Math.exp(-x)));
    }

    /**
     * Simplified defivative calculation
     *
     * @param sigmoidValue - expected to be value of {@link #sigmoid(float x)}
     */
    public static float dSigmoid(float sigmoidValue) {
        return sigmoidValue * (1 - sigmoidValue);
    }
}
