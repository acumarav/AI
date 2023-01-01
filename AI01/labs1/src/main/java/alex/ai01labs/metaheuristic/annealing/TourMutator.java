package alex.ai01labs.metaheuristic.annealing;

import java.util.ArrayList;
import java.util.Collections;

import static alex.ai01labs.metaheuristic.annealing.PostmanConstants.NUMBER_OF_CITIES;

public class TourMutator {

    private final Repository repo = new Repository();

    public TourMutator() {
        for (int index = 0; index < NUMBER_OF_CITIES; index++) {
            repo.add(new City());
        }
    }

    public SingleTour generateIndividual() {
        var tempCities = new ArrayList<City>(repo.getNumberOfCities());
        for (int cityIndex = 0; cityIndex < repo.getNumberOfCities(); cityIndex++) {
            tempCities.add(repo.get(cityIndex));
        }
        Collections.shuffle(tempCities);


        var newTour = new SingleTour(tempCities);
        return newTour;
    }

    public SingleTour generateNeighboringState(SingleTour actual){
        SingleTour newState = new SingleTour(actual.getTour());

        int randomIdx1 = (int) (Math.random()*newState.getTourSize());
        int randomIdx2 = (int) (Math.random()*newState.getTourSize());
        City city1 = newState.getCity(randomIdx1);
        City city2 = newState.getCity(randomIdx2);
        newState.setCity(randomIdx1, city2);
        newState.setCity(randomIdx2, city1);
        return newState;
    }
}
