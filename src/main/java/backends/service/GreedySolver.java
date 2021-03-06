package backends.service;

import backends.model.City;
import backends.util.Config;
import backends.util.Caches;
import backends.model.AbstractSolver;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import java.util.List;
import java.util.Set;

public class GreedySolver extends AbstractSolver {
    private Caches cache;

    public GreedySolver(String name) {
        super(name);
    }

    @Override
    public void process() {
        cache = new Caches();
        Config.shortestDistance = Double.MAX_VALUE;
        Config.cities.forEach(this::checkAndUpdatePath);
        if (Config.state == 1) {
            Config.state++;
            Config.flag = false;
        }
    }

    private void checkAndUpdatePath(final City depCity) {
        Set<City> allCities = Sets.newHashSet(Config.cities);
        List<Integer> currentPath = Lists.newArrayList(depCity.getNo());
        double currentDistance = 0.;
        City departCity = depCity;
        while (allCities.size() > 1) {
            allCities.remove(departCity);
            double distance = Double.MAX_VALUE;
            City cityNext = null;
            for (City desCity : allCities) {
                if (distance > cache.getDistanceByCity(departCity, desCity)) {
                    distance = cache.getDistanceByCity(departCity, desCity);
                    cityNext = desCity;
                }
            }
            currentPath.add(cityNext.getNo());
            currentDistance += Math.sqrt(distance);
            departCity = cityNext;
        }
        City end = allCities.stream().findFirst().get();
        currentDistance += Math.sqrt(cache.getDistanceByCity(depCity, end));
        if (currentDistance < Config.shortestDistance) {
            Config.shortestDistance = currentDistance;
            Config.shortestPath.clear();
            Config.shortestPath.addAll(currentPath);
        }
    }
}
