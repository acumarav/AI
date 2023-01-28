package alex.neuralnetwork.backprop_perceptron;


import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Random;

import static java.lang.Math.round;

public class OCRApp {

    private static final Random random = new Random();
    private static final float LEARNING_RATE = 0.3f;
    private static final float MOMENTUM = 0.6f;
    private static final int ITERATIONS = 100_000;
    private static final float[][] trainingResults = new float[][]{
            new float[]{1, 0, 0, 0, 0, 0, 0, 0, 0, 0}, // "0"
            new float[]{0, 1, 0, 0, 0, 0, 0, 0, 0, 0}, // "1"
            new float[]{0, 0, 1, 0, 0, 0, 0, 0, 0, 0}, // "2"
            new float[]{0, 0, 0, 1, 0, 0, 0, 0, 0, 0}, // "3"
            new float[]{0, 0, 0, 0, 1, 0, 0, 0, 0, 0}, // "4"
            new float[]{0, 0, 0, 0, 0, 1, 0, 0, 0, 0}, // "5"
            new float[]{0, 0, 0, 0, 0, 0, 1, 0, 0, 0}, // "6"
            new float[]{0, 0, 0, 0, 0, 0, 0, 1, 0, 0}, // "7"
            new float[]{0, 0, 0, 0, 0, 0, 0, 0, 1, 0}, // "8"
            new float[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 1}  // "9"
    };


    public static void main(String[] args) {
        CharReader charReader = new CharReader();
        float[] flat0 = flattenArray(charReader.readImage('0', true), true);
        float[] flat1 = flattenArray(charReader.readImage('1', false), false);
        float[] flat2 = flattenArray(charReader.readImage('2', false), false);
        float[] flat3 = flattenArray(charReader.readImage('3', false), false);
        float[] flat4 = flattenArray(charReader.readImage('4', false), false);
        float[] flat5 = flattenArray(charReader.readImage('5', false), false);
        float[] flat6 = flattenArray(charReader.readImage('6', false), false);
        float[] flat7 = flattenArray(charReader.readImage('7', false), false);
        float[] flat8 = flattenArray(charReader.readImage('8', false), false);
        float[] flat9 = flattenArray(charReader.readImage('9', false), false);

        var trainingData = Arrays.asList(flat0, flat1, flat2, flat3, flat4, flat5, flat6, flat7, flat8, flat9);

        BackpropNeuralNetwork network = new BackpropNeuralNetwork(64, 15, 10);

        final boolean echoTrain = false;
        final boolean echoTest = true;
        PrintStream echoStream = echoTrain ? System.out : new PrintStream(OutputStream.nullOutputStream());
        //Training part
        for (int iteration = 1; iteration <= ITERATIONS; iteration++) {
            for (int i = 0; i < trainingResults.length; i++) {
                network.train(trainingData.get(i), trainingResults[i], LEARNING_RATE, MOMENTUM);
            }

            if (iteration % 5000 == 0 || iteration == 1 || iteration == 10) {
                echoStream.printf("\nIteration: %d\n", iteration);
                for (int i = 0; i < trainingResults.length; i++) {
                    float[] data = trainingData.get(i);
                    float[] out = network.run(data);
                    echoStream.println(round(out[0]) + " " + round(out[1]) + " " + round(out[2]) + " " + round(out[3]) + " " + round(out[4]) + " " + round(out[5]) + " " + round(out[6]) + " " + round(out[7]) + " " + round(out[8]) + " " + round(out[9]));
                }
            }
        }

        //Test with data modification. Looks like more than 7 pixes can cause problems
        int uglifyPixels = 8;
        echoStream = echoTest ? System.out : new PrintStream(OutputStream.nullOutputStream());
        echoStream.printf("\n\n Test with uglify factor: %d\n", uglifyPixels);
        for (int i = 0; i < trainingResults.length; i++) {
            float[] modifiedData = uglify(trainingData.get(i), uglifyPixels);
            float[] out = network.run(modifiedData);
            echoStream.printf("\t %.1f %.1f %.1f %.1f %.1f %.1f %.1f %.1f %.1f %.1f \n", out[0], out[1], out[2], out[3], out[4], out[5], out[6], out[7], out[8], out[9]);
        }
    }

    private static float[] uglify(float[] data, int uglifyPixels) {
        var modifiedData = Arrays.copyOf(data, data.length);
        for (int i = 0; i < uglifyPixels; i++) {
            int badPixel = random.nextInt(data.length);
            modifiedData[badPixel] = (float) Math.abs(modifiedData[badPixel] - 1.0);

        }
        return modifiedData;
    }

    private static float[] flattenArray(byte[][] charMatrix, boolean echo) {
        var flatArray = new float[charMatrix.length * charMatrix.length];
        for (int i = 0; i < charMatrix.length; i++) {
            for (int j = 0; j < charMatrix.length; j++) {
                flatArray[i * charMatrix.length + j] = charMatrix[i][j];
            }
        }
        if (echo) {
            System.out.println("\nnew int[] {");
            for (int i = 0; i < flatArray.length; i++) {
                System.out.print(flatArray[i] + ",");
            }
            System.out.println("}");
        }
        return flatArray;
    }
}
