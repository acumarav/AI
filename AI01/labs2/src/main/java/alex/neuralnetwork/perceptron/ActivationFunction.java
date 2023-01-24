package alex.neuralnetwork.perceptron;

//@FunctionalInterface
public class ActivationFunction {

    public static float apply(float x) {
        if (x < 1) {
            return 0;
        }
        return 1;
    }
}
