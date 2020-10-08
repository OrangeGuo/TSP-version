package frontends.panel;

import backends.util.Caches;
import backends.model.City;
import backends.util.Config;

import javax.swing.*;


import java.awt.*;
import java.text.DecimalFormat;


public class SolutionPanel extends JPanel {
    DecimalFormat df = new DecimalFormat("######0.00");
    double scale = 1;
    int size = 7;
    Caches caches=new Caches();
    public SolutionPanel() {
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
        g.setColor(Color.white);
        Config.cities.forEach(city -> {
            drawCity(g, city);
        });

        City start = caches.getCityByNo(Config.shortestPath.get(0));
        g.setColor(Color.red);
        g.fillOval((int) (start.getX() * scale - 2), (int) (start.getY() * scale - 2), 10, 10);
        g.setColor(Color.blue);
        for (int i = 0; i < Config.shortestPath.size() - 1; i++) {
            City departCity = caches.getCityByNo(Config.shortestPath.get(i));
            City cityNext = caches.getCityByNo(Config.shortestPath.get(i + 1));
            g.drawLine((int) (departCity.getX() * scale + 3), (int) (departCity.getY() * scale + 3), (int) (cityNext.getX() * scale + 3), (int) (cityNext.getY() * scale + 3));
        }
        City end = caches.getCityByNo(Config.shortestPath.get(Config.shortestPath.size() - 1));
        g.drawLine((int) (start.getX() * scale + 3), (int) (start.getY() * scale + 3), (int) (end.getX() * scale + 3), (int) (end.getY() * scale + 3));
        g.setColor(Color.cyan);
        g.drawString("时间仅供参考", 500, 20);
        g.drawString("红色圆点为起点", 500, 40);
        g.drawString("最短距离:" + df.format(Config.shortestDistance), 150, 520);
        g.drawString("总用时:" + Config.runTime + "毫秒", 350, 520);
    }
}