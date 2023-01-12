package alex.neuralnetwork.hopfield;

public class Matrix {

    public static int[] matrixVectorMultiplication(int[][] matrix, int[] vector) {
        int[] result = new int[vector.length];
        for (int i = 0; i < matrix.length; i++) {
            int sum = 0;
            for (int j = 0; j < vector.length; j++) {
                sum += matrix[i][j] * vector[j];
            }
            result[i] = sum;
        }
        return result;
    }

    public static int[][] createMatrix(int numRows, int numCols) {
        return new int[numRows][numCols];
    }

    public static int[][] outerProduct(int[] vector) {
        int[][] result = new int[vector.length][vector.length];

        for (int i = 0; i < vector.length; i++) {
            for (int j = 0; j < vector.length; j++) {
                result[i][j] = vector[i] * vector[j];
            }
        }
        return result;
    }

    public static int[][] clearDiagonal(int[][] matrix){
        for(int i=0;i<matrix.length;i++){
            matrix[i][i]=0;
        }
        return matrix;
    }

    public static int[][] addMatrix(int[][] matrix1, int[][] matrix2){
        int[][] result = new int[matrix1.length][matrix1.length];

        for (int i = 0; i < matrix1.length; i++) {
            for (int j = 0; j < matrix1.length; j++) {
                result[i][j] = matrix1[i][j] + matrix2[i][j];
            }
        }
        return result;

    }
}
