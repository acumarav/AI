package alex.ai01labs.metaheuristic.annealing;

import java.util.ArrayList;
import java.util.List;

public class SingleTour {

    private final List<City> tour = new ArrayList<>();
    //private int distance;

    public SingleTour(int size) {
        for (int index = 0; index < size; index++) {
            tour.add(null);
        }
    }

    public SingleTour(List<City> cities) {
        for (City city : cities) {
            tour.add(city);
        }
    }

    public List<City> getTour() {
        return this.tour;
    }

    public City getCity(int index) {
        return tour.get(index);
    }

    public void setCity(int index, City city) {
        tour.set(index, city);
    }

    public int getTourSize() {
        return tour.size();
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        for (City city : tour) {
            sb.append(city);
            sb.append(" - ");
        }
        return sb.toString();
    }

    public double calcTourDistance() {
        double total = 0;
        final int count = tour.size();
        for (int index = 1; index < count; index++) {
            City from = tour.get(index - 1);
            City to = tour.get(index);
            total += from.distanceTo(to);
        }
        total += tour.get(count - 1).distanceTo(tour.get(0));//add last segment back to start
        return total;
    }
}
