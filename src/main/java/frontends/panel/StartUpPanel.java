package frontends.panel;

import backends.model.Solution;
import backends.util.SolutionUtil;
import com.google.common.collect.Lists;
import frontends.util.GraphicsUtil;

import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;


public class StartUpPanel extends JPanel implements Runnable {
    DecimalFormat df = new DecimalFormat("######0.00");
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    int startTime;
    Solution solution;

    public StartUpPanel() {
        ArrayList<Solution> solutions = SolutionUtil.loadFromFile().orElse(Lists.newArrayList());
        Collections.shuffle(solutions);
        solution = solutions.size() > 0 ? solutions.get(0) : null;
        startTime = solution == null ? 3 : 6;
    }

    public void paint(Graphics g) {
        super.paint(g);
        g.fillRect(0, 0, 600, 480);
        this.paints(g);
    }

    public void paints(Graphics g) {
        if (startTime < 6 && solution != null) {
            this.setFont(null);
            GraphicsUtil.drawPath(g, solution, 450.);
            g.setColor(Color.yellow);
            g.drawString("时间仅供参考", 470, 20);
            g.drawString("红色圆点为起点", 470, 40);
            g.drawString(simpleDateFormat.format(new Date(solution.getTime())), 470, 60);
            g.drawString(solution.getAlgorithm(), 470, 80);
            g.drawString("最短距离:" + df.format(solution.getDistance()), 470, 220);
            g.drawString("总用时:" + solution.getRunTime() + "毫秒", 470, 240);
        } else {
            g.setColor(Color.yellow);
            Font font = new Font("宋体", Font.BOLD, 100);
            this.setFont(font);
            g.drawString("TSP问题", 120, 240);
        }
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        while (startTime > 0) {
            try {
                Thread.sleep(1000);
                startTime--;

            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            this.repaint();

        }
    }


}