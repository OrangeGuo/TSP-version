package frontends.panel;

import backends.model.Solution;
import backends.util.Caches;
import backends.model.City;
import backends.util.Config;
import com.google.common.collect.Lists;
import lombok.Setter;

import javax.swing.*;


import java.awt.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


public class SolutionPanel extends JPanel {
    DecimalFormat df = new DecimalFormat("######0.00");
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    double scale;
    int size = 7;
    @Setter
    Solution solution;


    public SolutionPanel(Solution solution) {
        this.solution = solution;
    }

    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.black);
        g.fillRect(0, 0, 630, 600);
        drawPath(g, solution);
    }

    private void drawCity(Graphics g, City city) {
        g.fillOval((int) (city.getX() * scale), (int) (city.getY() * scale), size, size);
    }

    private void drawPath(Graphics g, Solution solution) {
        List<City> cities = solution.getBestPath();
        updateScaleOnMax(cities);
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
        g.setColor(Color.cyan);
        g.drawString("时间仅供参考", 500, 20);
        g.drawString("红色圆点为起点", 500, 40);
        g.drawString(simpleDateFormat.format(new Date(solution.getTime())), 500, 60);
        g.drawString(solution.getAlgorithm(), 500, 80);
        g.drawString("最短距离:" + df.format(solution.getDistance()), 150, 520);
        g.drawString("总用时:" + solution.getRunTime() + "毫秒", 350, 520);
    }

    private void updateScaleOnMax(List<City> cities) {
        double max = 0;
        for (City city : cities) {
            double temp = Math.max(city.getY(), city.getX());
            max = Math.max(temp, max);
        }
        scale = 500 / max;
    }
}