package alex.neuralnetwork.hopfield;

public class Utils {

    public static int[] transform(int[] pattern) {
        for (int i = 0; i < pattern.length; i++) {
            if (pattern[i] == 0) {
                pattern[i] = -1;
            }
        }
        return pattern;
    }

    public static int[] retransform(int[] pattern) {
        for (int i = 0; i < pattern.length; i++) {
            if (pattern[i] == -1) {
                pattern[i] = 0;
            }
        }
        return pattern;
    }

    public static void printMatrix(int[] result) {
        int dimension = (int) Math.sqrt(result.length);
        for (int i = 0; i < result.length; i++) {
            System.out.printf("%d ", result[i]);
            if ((i + 1) % dimension == 0) {
                System.out.println();
            }
        }
        System.out.println("----------------------");
    }
}
