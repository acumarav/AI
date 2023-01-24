package alex.neuralnetwork.perceptron;

/**
 * Simple 2 layers perceptron with no hidden layers. Not capable to solve non-linear tasks.
 */
public class SimplePerceptronApp {
    public static void main(String[] args) {
        float[][] inputFeatures = {{0, 0}, {0, 1}, {1, 0}, {1, 1}};
        float[] andLabels = {0, 0, 0, 1};
        float[] xorLabels = {0, 1, 1, 0};//XOR does not work because no hidden layers in the given implementation

        SimpleNetwork network = new SimpleNetwork(inputFeatures, andLabels);
        network.train(0.2f);

        System.out.println("After the training test:");
        System.out.printf("Test [0, 0]= %f\n", network.calculateOutput(new float[]{0, 0}));
        System.out.printf("Test [0, 1]= %f\n", network.calculateOutput(new float[]{0, 1}));
        System.out.printf("Test [1, 0]= %f\n", network.calculateOutput(new float[]{1, 0}));
        System.out.printf("Test [1, 1]= %f\n", network.calculateOutput(new float[]{1, 1}));
    }
}
