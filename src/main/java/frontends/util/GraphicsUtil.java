package frontends.util;

import backends.model.City;
import backends.model.Solution;

import java.awt.*;
import java.util.Date;
import java.util.List;

public final class GraphicsUtil {
    static double scale;
    static int size = 7;

    public static void drawPath(Graphics g, Solution solution, Double edgeLength) {
        List<City> cities = solution.getBestPath();
        updateScaleOnMax(cities, edgeLength);
        g.setColor(Color.white);
        cities.forEach(city -> {
            drawCity(g, city);
        });
        City start = cities.get(0);
        g.setColor(Color.red);
        g.fillOval((int) (start.getX() * scale - 2), (int) (start.getY() * scale - 2), 10, 10);
        g.setColor(Color.blue);
        for (int i = 0; i < cities.size() - 1; i++) {
            City departCity = cities.get(i);
            City cityNext = cities.get(i + 1);
            g.drawLine((int) (departCity.getX() * scale + 3), (int) (departCity.getY() * scale + 3), (int) (cityNext.getX() * scale + 3), (int) (cityNext.getY() * scale + 3));
        }
        City end = cities.get(cities.size() - 1);
        g.drawLine((int) (start.getX() * scale + 3), (int) (start.getY() * scale + 3), (int) (end.getX() * scale + 3), (int) (end.getY() * scale + 3));
    }

    private static void updateScaleOnMax(List<City> cities, Double edgeLength) {
        double max = 0;
        for (City city : cities) {
            double temp = Math.max(city.getY(), city.getX());
            max = Math.max(temp, max);
        }
        scale = edgeLength / max;
    }

    private static void drawCity(Graphics g, City city) {
        g.fillOval((int) (city.getX() * scale), (int) (city.getY() * scale), size, size);
    }

}
