package frontends.panel;

import backends.model.Solution;
import backends.util.Caches;
import backends.model.City;
import backends.util.Config;
import com.google.common.collect.Lists;
import frontends.util.GraphicsUtil;
import lombok.Setter;

import javax.swing.*;


import java.awt.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;



public class SolutionPanel extends JPanel {
    DecimalFormat df = new DecimalFormat("######0.00");
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

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

    private void drawPath(Graphics g, Solution solution) {
        GraphicsUtil.drawPath(g, solution, 500.);
        g.drawString("时间仅供参考", 500, 20);
        g.drawString("红色圆点为起点", 500, 40);
        g.drawString(simpleDateFormat.format(new Date(solution.getTime())), 500, 60);
        g.drawString(solution.getAlgorithm(), 500, 80);
        g.drawString("最短距离:" + df.format(solution.getDistance()), 150, 520);
        g.drawString("总用时:" + solution.getRunTime() + "毫秒", 350, 520);
    }

}