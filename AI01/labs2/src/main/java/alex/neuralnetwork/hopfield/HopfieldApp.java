package alex.neuralnetwork.hopfield;

import static alex.neuralnetwork.hopfield.Utils.printMatrix;

public class HopfieldApp {

    private static final int[] PATTERN_C = new int[]{1, 1, 1, 1, 0, 0, 1, 1, 1};
    private static final int[] PATTERN_T = new int[]{1, 1, 1, 0, 1, 0, 0, 1, 0};

    public static void main(String[] args) {
        printMatrix(PATTERN_T);
        System.out.println("------------");

        HopfieldNetwork hopfieldNetwork = new HopfieldNetwork(9);

        hopfieldNetwork.train(PATTERN_C);
        hopfieldNetwork.train(PATTERN_T);

       var NOISY_C = new int[]{1, 0, 1, 1, 0, 0, 1, 1, 1};
        hopfieldNetwork.recall(NOISY_C);

        var NOISY_T = new int[]{0, 1, 1, 0, 1, 0, 0, 1, 1};
        hopfieldNetwork.recall(NOISY_T);
    }
}
