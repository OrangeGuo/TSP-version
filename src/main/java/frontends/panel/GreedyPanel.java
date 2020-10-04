package frontends.panel;

import backends.City;
import backends.Config;
import backends.DistancesCache;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import javax.swing.*;


import java.awt.*;
import java.text.DecimalFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


public class GreedyPanel extends JPanel {
    DecimalFormat df = new DecimalFormat("######0.00");
    double scale = 1;
    int size = 7;
    long solveTime;
    DistancesCache cache = new DistancesCache();

    public GreedyPanel() {
        scale = Config.scale;
    }

    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.black);
        g.fillRect(0, 0, 600, 600);
        drawPath(g);
    }

    public void drawCity(Graphics g, City city) {
        g.fillOval((int) (city.getX() * scale), (int) (city.getY() * scale), size, size);
    }

    public void drawPath(Graphics g) {
        long startTime = System.currentTimeMillis();
        greedySolve();
        solveTime = System.currentTimeMillis() - startTime;
        g.setColor(Color.white);
        Config.cities.forEach(city -> {
            drawCity(g, city);
        });
        Map<Integer, City> cityMap = Config.cities.stream().collect(Collectors.toMap(City::getNo, city -> city));
        City start = cityMap.get(Config.shortestPath.get(0));
        g.setColor(Color.red);
        g.fillOval((int) (start.getX() * scale - 2), (int) (start.getY() * scale - 2), 10, 10);
        g.setColor(Color.blue);
        for (int i = 0; i < Config.shortestPath.size() - 1; i++) {
            City departCity = cityMap.get(Config.shortestPath.get(i));
            City cityNext = cityMap.get(Config.shortestPath.get(i + 1));
            g.drawLine((int) (departCity.getX() * scale + 3), (int) (departCity.getY() * scale + 3), (int) (cityNext.getX() * scale + 3), (int) (cityNext.getY() * scale + 3));
        }
        City end = cityMap.get(Config.shortestPath.get(Config.shortestPath.size() - 1));
        g.drawLine((int) (start.getX() * scale + 3), (int) (start.getY() * scale + 3), (int) (end.getX() * scale + 3), (int) (end.getY() * scale + 3));
        g.setColor(Color.cyan);
        g.drawString("时间仅供参考", 500, 20);
        g.drawString("红色圆点为起点", 500, 40);
        g.drawString("最短距离:" + df.format(Config.shortestDistance), 150, 520);
        g.drawString("总用时:" + solveTime + "毫秒", 350, 520);
    }

    private void greedySolve() {
        Config.shortestDistance=Double.MAX_VALUE;
        Config.cities.forEach(this::checkAndUpdatePath);
        if (Config.state == 1) {
            Config.state++;
            Config.flag = false;
        }
    }

    private void checkAndUpdatePath(final City depCity) {
        Set<City> allCities = Sets.newHashSet(Config.cities);
        City start = depCity;
        List<Integer> currentPath = Lists.newArrayList(start.getNo());
        Double currentDistance = 0.;
        City departCity = start;
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
        currentPath.add(end.getNo());
        currentDistance += Math.sqrt(cache.getDistanceByCity(start, end));
        if (currentDistance < Config.shortestDistance) {
            Config.shortestDistance = currentDistance;
            Config.shortestPath.clear();
            Config.shortestPath.addAll(currentPath);
        }
    }
}