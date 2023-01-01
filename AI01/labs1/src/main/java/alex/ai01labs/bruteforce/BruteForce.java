package alex.ai01labs.bruteforce;

public class BruteForce {

    public static void main(String[] args) {
        simpleBruteForce();
    }

    private static void simpleBruteForce() {
        double startX = -2;
        double endX = 2;
        double dx = 0.1;

        double max = f(startX);
        for (double i = startX; i < endX; i += dx) {
            if(f(i)>max){
                max=f(i);
            }
        }
        System.out.printf("\nMaximum is: %.40f\n", max);
    }

    static double f(double x) {
        return -(x * x);
    }
}
