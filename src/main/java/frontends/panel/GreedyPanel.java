package frontends.panel;

import backends.City;
import backends.Config;
import com.google.common.collect.Lists;

import javax.swing.*;

import java.awt.*;
import java.text.DecimalFormat;
import java.util.List;


public class GreedyPanel extends JPanel implements Runnable{
    List<City> points= Lists.newArrayList();
    List<City> point=Lists.newArrayList();
    DecimalFormat df=new DecimalFormat("######0.00");
    int index_all=0;
    int index_min=0;
    double scale=1;
    int size=7;
    double distance_min=100000000;
    double distance_now=0;
    boolean p=true;
    boolean get=false;
    long Time_start=System.currentTimeMillis();
    long Time_total=0;
    long time=10;//最小刷新时间间隔10毫秒
    public Config psi=null;
    public GreedyPanel(List<City> p, Config psi){
        point=p;
        scale=psi.getscale();
        time*=psi.gettime();
        this.psi=psi;
    }
    public void paint(Graphics g)
    {
        super.paint(g);
        g.setColor(Color.black);
        g.fillRect(0, 0, 600, 600);
        g.setColor(Color.white);
        for (City p : point) {
            g.fillOval((int) (p.getX() * scale), (int) (p.getY() * scale), size, size);
        }


        if(index_all<point.size())
        {
            this.drawline(g,index_all);
            index_all++;
        }
        else
        {
            if(p) get=true;
            p=false;

            this.drawline(g,index_min);
        }





    }
    public void drawline(Graphics g,int n){
        points.addAll(point);
        int index=n;

        City point_01=points.get(index);
        if(get&& Config.flag)
        {
            Config.shortestPath.add(point_01.getN());
        }
        g.setColor(Color.red);
        g.fillOval((int)(point_01.getX()*scale-2),(int)(point_01.getY()*scale-2), 10, 10);
        g.setColor(Color.blue);
        while(true)
        {
            City point_first=points.get(index);
            points.remove(index);
            double distance=2000000000;

            for(int i=0;i<points.size();i++)
            {
                City point_now=points.get(i);
                if(distance>point_first.getdistence(point_now))
                {
                    distance=point_first.getdistence(point_now);
                    index=i;
                }
            }

            City point_near=points.get(index);
            if(get&& Config.flag)
            {
                Config.shortestPath.add(point_near.getN());
            }
            g.drawLine((int)(point_first.getX()*scale+3),(int)(point_first.getY()*scale+3),(int)(point_near.getX()*scale+3),(int)(point_near.getY()*scale+3));
            distance_now+=Math.sqrt(distance);
            if(points.size()==1)
            {
                break;
            }

        }
        City point_last=points.get(0);
        get=false;
        points.remove(0);
        distance_now+=Math.sqrt(point_01.getdistence(point_last));
        g.drawLine((int)(point_01.getY()*scale+3),(int)(point_01.getY()*scale+3),(int)(point_last.getX()*scale+3),(int)(point_last.getY()*scale+3));
        g.setColor(Color.cyan);
        String s=String.valueOf(df.format(distance_now));
        if(distance_min>distance_now){
            distance_min=distance_now;
            index_min=index_all;
        }
        g.drawString("时间仅供参考", 500, 20);
        g.drawString("红色圆点为起点", 500, 40);
        if(p){
            g.drawString("当前总距离:"+s, 0,520);
            s=String.valueOf(df.format(distance_min));
            g.drawString("当前最短距离:"+s, 450, 520);
            Time_total=(System.currentTimeMillis()-Time_start);
            s=String.valueOf(Time_total);
            g.drawString("总用时:"+s+"毫秒", 250, 520);
        }
        else{
            g.drawString("最短距离:"+s, 150,520);
            s=String.valueOf(Time_total);
            g.drawString("总用时:"+s+"毫秒",350, 520);
            Config.shortestDistance =distance_min;
            if(Config.state ==1)
            {
                Config.state++;
                Config.flag =false;
            }

        }

        distance_now=0;
    }
    @Override
    public void run() {
        // TODO Auto-generated method stub
        while(true)
        {
            try {
                Thread.sleep(time);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            this.repaint();
        }
    }
}