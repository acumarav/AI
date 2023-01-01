package alex.ai01labs.metaheuristic.annealing;

public class City {
    private final int x;
    private final int y;

    public City() {
        this.x = (int) (Math.random() * 100);
        this.y = (int) (Math.random() * 100);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return "{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    public double distanceTo(City anotherCity) {
        double distX = Math.abs(this.x - anotherCity.x);
        double distY = Math.abs(this.y - anotherCity.y);
        return Math.sqrt(distX * distX + distY * distY);
    }
}
