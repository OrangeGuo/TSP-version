package frontends.frame;

import backends.model.City;
import backends.model.Solution;
import backends.util.Caches;
import backends.util.Config;
import backends.util.Icons;
import backends.util.SolutionUtil;
import com.google.common.collect.Lists;
import frontends.panel.SolutionPanel;
import sun.font.DelegatingShape;

import javax.swing.*;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

//求解时的运行窗口(弹出)
public class WorkFrame extends JDialog implements MouseListener {

    JPanel buttonPanel;
    SolutionPanel dp;
    JButton exitButton;
    JButton switchButton;
    Caches caches=new Caches();
    ArrayList<Solution> solutions;
    public WorkFrame() {

        buttonPanel = new JPanel();
        exitButton = new JButton("退出");
        exitButton.addMouseListener(this);

        switchButton = new JButton("历史优解");
        switchButton.addMouseListener(this);

        buttonPanel.setBackground(Color.CYAN);
        buttonPanel.add(exitButton);

        buttonPanel.add(switchButton);
        Config.abstractSolver.solve();
        Solution solution=Solution.builder().bestPath((ArrayList<City>) Config.shortestPath.stream().map(cityNo -> caches.getCityByNo(cityNo)).collect(Collectors.toList()))
                .runTime(Config.runTime)
                .distance(Config.shortestDistance)
                .algorithm(Config.abstractSolver.getName())
                .time(System.currentTimeMillis())
                .build();

        dp = new SolutionPanel(solution);
        this.add(dp);
        Optional<ArrayList<Solution>> optionalSolutions=SolutionUtil.loadFromFile();
        if(optionalSolutions.isPresent())
            solutions=optionalSolutions.get();
        else {
            solutions= Lists.newArrayList(solution);
            SolutionUtil.saveToFile(solutions);
        }


        this.add(buttonPanel, BorderLayout.SOUTH);
        this.setIconImage(Icons.WorkIcon.getImage());
        this.setSize(600, 600);
        this.setTitle("Find the shortest path");
        this.setLocationRelativeTo(null);
        this.setModal(true);
        this.setResizable(false);
        this.dispose();
        this.setVisible(true);

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub
        if (e.getSource().equals(exitButton)) {
            this.dispose();
        }
        else {
           dp.setSolution(solutions.get(0));
           dp.repaint();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub

    }
}

