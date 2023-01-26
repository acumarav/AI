package alex.neuralnetwork.backprop_perceptron;

import java.util.Arrays;
import java.util.Random;

public class Layer {

    private final float[] inputs;
    private final float[] outputs;
    private final Random random = new Random();
    private final float[] weights;
    private final float[] dWeights;

    public Layer(int inputSize, int outputSize) {
        this.outputs = new float[outputSize];
        this.inputs = new float[inputSize + 1];
        this.weights = new float[(inputSize + 1) * outputSize];
        this.dWeights = new float[weights.length];
        initWeights();
    }

    private void initWeights() {
        for (int i = 0; i < weights.length; i++) {
            weights[i] = (random.nextFloat() - 0.5f) * 4f;//[-2,2]
        }

    }

    public float[] run(float[] inputArray) {
        System.arraycopy(inputArray, 0, inputs, 0, inputArray.length);
        inputs[inputs.length - 1] = 1;
        int offset = 0;

        for (int i = 0; i < outputs.length; i++) {
            for (int j = 0; j < inputs.length; j++) {
                outputs[i] += weights[offset + j] * inputs[j];

            }
            outputs[i] = ActivationFunc.sigmoid(outputs[i]);
            offset += inputs.length;
        }
        return Arrays.copyOf(outputs, outputs.length);
    }

    public float[] train(float[] error, float learningRate, float momentum) {
        int offset = 0;
        float[] nextError = new float[inputs.length];

        for (int i = 0; i < outputs.length; i++) {
            float delta = error[i] * ActivationFunc.dSigmoid(outputs[i]);

            for (int j = 0; j < inputs.length; j++) {
                int weightIndex = offset + j;
                nextError[j] = nextError[j] + weights[weightIndex] * delta;
                float dw = inputs[j] * delta * learningRate;
                weights[weightIndex] += dWeights[weightIndex] * momentum + dw;
                dWeights[weightIndex] = dw;
            }
            offset += inputs.length;
        }
        return nextError;
    }
}
