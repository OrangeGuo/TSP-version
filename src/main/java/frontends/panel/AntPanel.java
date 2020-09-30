package frontends.panel;

import backends.Ant;
import backends.Path;
import backends.City;
import backends.Config;
import com.google.common.collect.Lists;

import javax.swing.*;

import java.awt.*;
import java.text.DecimalFormat;
import java.util.List;

public class AntPanel extends JPanel {
    DecimalFormat df=new DecimalFormat("######0.00");//最终结果保留两位小数
    List<Ant> ants=null;//蚁群
    List<City> points=null;//城市信息记录
    double[][] distance;//城市之间距离矩阵
    double[][] t;//城市之间信息素矩阵
    double c=5;//初始信息素浓
    double Q=100;//每次蚂蚁走完所有城市所留下的信素
    double p=0.90;//信息素保留率
    int loop_max=100;
    double scale;
    int size=7;
    int time=0;
    int index;
    double distance_min=200000000;//记录最优解
    Path list_01;//记录最优解
    public AntPanel(List<City> p){
        distance=new double[150][150];
        t=new double[150][150];
        points=p;
        this.scale=Config.scale;
        for(int i=0;i<p.size();i++)
        {
            City point=p.get(i);
            for(int j=0;j<p.size();j++)
            {
                distance[i][j]=point.getdistence(p.get(j));
                t[i][j]=c;
            }
        }


    }
    public void paint(Graphics g)
    {
        super.paint(g);
        this.setBackground(Color.black);
        g.setColor(Color.black);
        for(int i=0;i<5000;i++)
        {
            this.ants_get();
            this.ants_run();
            //System.out.println(i+"   "+distance_min);
        }
        //System.out.println(distance_min);
        //this.show(list_01);
        //System.out.println(this.getdistance(list_01));
        //this.draw(g);
    }
    public void draw(Graphics g)
    {
        for(int i=0;i<points.size();i++)
        {
            int x=list_01.get(i);
            City p=points.get(x);
            g.setColor(Color.white);
            g.fillOval((int)(p.getX()*scale),(int)(p.getY()*scale),size,size);
            int y=list_01.get(i+1);
            City point_near=points.get(y);
            g.setColor(Color.blue);
            g.drawLine((int)(p.getX()*scale+3),(int)(p.getY()*scale+3),(int)(point_near.getX()*scale+3),(int)(point_near.getY()*scale+3));

        }
        String string=String.valueOf(df.format(distance_min))+"      "+String.valueOf(time);
        g.setColor(Color.yellow);
        g.drawString(string, 100, 520);
    }
    public void ants_get()//初始化蚁群
    {
        ants= Lists.newArrayList();
        for(int i=0;i<points.size();i++)
        {
            Ant an=new Ant(i,points.size());
            //this.show(an.list_allow);
            ants.add(an);
        }
    }
    public void ants_run()//单次循环：所有蚂蚁走完所有城市
    {
        for(int i=1;i<points.size();i++)
        {
            for(int j=0;j<points.size();j++)
            {
                this.choosecity(j);
            }
        }
        for(int j=0;j<points.size();j++)
        {
            Ant ant =ants.get(j);
            ant.setDistance(ant.getDistance()+this.betwwenpoints(ant.getCityNo(), ant.getDepartCityNo()));
            if(distance_min>ant.getDistance())
            {
                distance_min=ant.getDistance();
                index=j;
                list_01=new Path(ant.getWalkOverCities());
                list_01.add(ant.getDepartCityNo());
            }
            //System.out.print(Ant.distance+" ");
            ant.getWalkOverCities().add(ant.getDepartCityNo());
            for(int i=0;i<points.size();i++)
            {
                t[ant.getWalkOverCities().get(i)][ant.getWalkOverCities().get(i+1)]=t[ant.getWalkOverCities().get(i)][ant.getWalkOverCities().get(i+1)]*p+Q/ant.getDistance();
                t[ant.getWalkOverCities().get(i+1)][ant.getWalkOverCities().get(i)]=t[ant.getWalkOverCities().get(i+1)][ant.getWalkOverCities().get(i)]*p+Q/ant.getDistance();
            }
            //System.out.println();
        }
    }
    public void show(Path l)
    {
        System.out.print(' ');
        Path list=new Path(l);
        while(list!=null)
        {
            System.out.print(list.no +" ");
            list=list.next;
        }
        System.out.println();
    }
    //选择下一城市
    public void choosecity(int n)
    {
        Ant a=ants.get(n);
        double random=Math.random();
        double sump=0,sum=0;
        double p[]=new double[150];
        for(int i = 0; i<a.getCityNumLeft(); i++)
        {
            double di=1/distance[a.getCityNo()][a.getToGoCities().get(i)];//启发因子
            double ti=t[a.getCityNo()][a.getToGoCities().get(i)];//信息素因子
            p[i]=ti*di*di*di*di*di;
            sump+=p[i];
        }
        for(int i = 0; i<a.getCityNumLeft(); i++)
        {
            sum+=(p[i]/sump);
            if(random<=sum)
            {
                a.setDistance(a.getDistance()+this.betwwenpoints(a.getCityNo(), a.getToGoCities().get(i)));
                a.setCityNo(a.getToGoCities().get(i));
                a.setWalkOverCityNum(a.getWalkOverCityNum()+1);
                a.getWalkOverCities().add(a.getCityNo());
                a.setToGoCities(a.getToGoCities().remove(a.getCityNo()));
                a.setCityNumLeft(a.getCityNumLeft()-1);
                break;
            }
        }
    }
    public double betwwenpoints(int m,int n)
    {
        City point=points.get(m);
        return Math.sqrt(point.getdistence(points.get(n)));
    }
    public double getdistance(Path li)
    {
        double distance=0;
        int m=li.no;
        while(li.next!=null)
        {

            distance+=this.betwwenpoints(li.no, li.next.no);
            li=li.next;
        }
        distance+=this.betwwenpoints(m, li.no);
        return distance;
    }



}
