package alex.neuralnetwork.hopfield;

public class ActivationFunction {

    public static int activation(int x) {
        if (x < 0) {
            return -1;
        }
        return 1;
    }
}
