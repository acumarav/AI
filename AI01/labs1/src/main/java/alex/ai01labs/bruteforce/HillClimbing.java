package alex.ai01labs.bruteforce;

public class HillClimbing {

    public  static  void main(String[] args) {
        double dX=0.1;
        var algo = new HillClimbing();

        double actualX = -8;
        double maxX = actualX;
        while (algo.f(actualX + dX) >= algo.f(maxX)) {
            maxX = actualX + dX;
            System.out.printf("x: %f, f(x): %f \n", actualX, algo.f(actualX));
            actualX += dX;
        }
        System.out.printf("\n\tMax with hill climbing x=: %f, f(x): %f \n", actualX, algo.f(actualX));

    }

    public double f(double x) {
        return -0.09 * Math.pow(x - 0.1, 4) + 3 * Math.pow(x - 0.4, 2) + 12;
    }
}
