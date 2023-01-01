package alex.ai01labs.metaheuristic.annealing;

import java.util.ArrayList;
import java.util.List;

public class Repository {
    private final List<City> cities = new ArrayList<>();

    public void add(City city) {
        this.cities.add(city);
    }

    public City get(int index) {
        return this.cities.get(index);
    }

    public int getNumberOfCities(){
        return cities.size();
    }
}
