package alex.neuralnetwork.backprop_perceptron;

import java.util.Arrays;

public class BackpropNeuralNetwork {

    private final Layer[] layers;

    public BackpropNeuralNetwork(int inputSize, int hiddenSize, int outputSize) {
        //2 layers - hidden and output, input is not here because it always equals to inputValues
        var hiddenLayer = new Layer(inputSize, hiddenSize);
        var outputLayer = new Layer(hiddenSize, outputSize);
        layers = new Layer[]{hiddenLayer, outputLayer};
    }

    public Layer getLayer(int index) {
        return layers[index];
    }

    public float[] run(float[] input) {
        var activations = Arrays.copyOf(input, input.length);
        for (int i = 0; i < layers.length; i++) {
            activations = layers[i].run(activations);
        }
        return activations;
    }

    public void train(float[] input, float[] targetOutput, float learningRate, float momentum) {

        var calculatedOutput = run(input);
        float[] error = new float[calculatedOutput.length];

        for (int i = 0; i < error.length; i++) {
            error[i] = targetOutput[i] - calculatedOutput[i];
        }

        for (int i = layers.length - 1; i >= 0; i--) {
            error = layers[i].train(error, learningRate, momentum);
        }

    }
}
