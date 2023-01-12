package alex.neuralnetwork.hopfield;

import static alex.neuralnetwork.hopfield.Utils.printMatrix;

public class HopfieldNetwork {

    private int[][] weightMatrix;

    public HopfieldNetwork(int dimension) {
        weightMatrix = new int[dimension][dimension];
    }

    public void train(int[] pattern) {
        //int[] bipolarPattern = Utils.retransform(pattern);
        int[] bipolarPattern = Utils.transform(pattern);
        //int[][] patternWeightMatrix = Matrix.createMatrix(pattern.length, pattern.length);
        int[][] patternWeightMatrix = Matrix.outerProduct(bipolarPattern);
        patternWeightMatrix = Matrix.clearDiagonal(patternWeightMatrix);
        weightMatrix = Matrix.addMatrix(weightMatrix, patternWeightMatrix);
    }

    public void recall(int[] pattern) {
        int[] bipolarPattern = Utils.transform(pattern);
        int[] result = Matrix.matrixVectorMultiplication(weightMatrix, bipolarPattern);
        //apply sign activation
        for (int i = 0; i < bipolarPattern.length; i++) {
            result[i] = ActivationFunction.activation(result[i]);
        }
        result = Utils.retransform(result);

        printMatrix(result);
    }
}
