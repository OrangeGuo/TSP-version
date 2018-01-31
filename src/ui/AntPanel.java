package ui;

import backends.Ant;
import backends.PathList;
import backends.Point;
import backends.PublicSettingIndex;

import javax.swing.*;

import java.awt.*;
import java.text.DecimalFormat;
import java.util.Vector;

public class AntPanel extends JPanel {
    DecimalFormat df=new DecimalFormat("######0.00");//最终结果保留两位小数
    Vector<Ant> ants=null;//蚁群
    Vector<Point> points=null;//城市信息记录
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
    PathList list_01;//记录最优解
    public AntPanel(Vector<Point> p, PublicSettingIndex psi){
        distance=new double[150][150];
        t=new double[150][150];
        points=p;
        this.scale=psi.getscale();
        for(int i=0;i<p.size();i++)
        {
            Point point=p.get(i);
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
            System.out.println(i+"   "+distance_min);
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
            Point p=points.get(x);
            g.setColor(Color.white);
            g.fillOval((int)(p.getx()*scale),(int)(p.gety()*scale),size,size);
            int y=list_01.get(i+1);
            Point point_first=p;
            Point point_near=points.get(y);
            g.setColor(Color.blue);
            g.drawLine((int)(point_first.getx()*scale+3),(int)(point_first.gety()*scale+3),(int)(point_near.getx()*scale+3),(int)(point_near.gety()*scale+3));

        }
        String string=String.valueOf(df.format(distance_min))+"      "+String.valueOf(time);
        g.setColor(Color.yellow);
        g.drawString(string, 100, 520);
    }
    public void ants_get()//初始化蚁群
    {
        ants=new Vector<Ant>();
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
            ant.distance+=this.betwwenpoints(ant.citynum, ant.start);

            if(distance_min>ant.distance)
            {
                distance_min=ant.distance;
                index=j;
                list_01=new PathList(ant.list);
                list_01.add(ant.start);
            }
            //System.out.print(Ant.distance+" ");
            ant.list.add(ant.start);
            for(int i=0;i<points.size();i++)
            {
                t[ant.list.get(i)][ant.list.get(i+1)]=t[ant.list.get(i)][ant.list.get(i+1)]*p+Q/ant.distance;
                t[ant.list.get(i+1)][ant.list.get(i)]=t[ant.list.get(i+1)][ant.list.get(i)]*p+Q/ant.distance;
            }
            //System.out.println();
        }



    }
    public void show(PathList l)
    {
        System.out.print(' ');
        PathList list=new PathList(l);
        while(list!=null)
        {
            System.out.print(list.n+" ");
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
        for(int i=0;i<a.city_left;i++)
        {
            double di=1/distance[a.citynum][a.list_allow.get(i)];//启发因子
            double ti=t[a.citynum][a.list_allow.get(i)];//信息素因子
            p[i]=ti*di*di*di*di*di;
            sump+=p[i];
        }
        for(int i=0;i<a.city_left;i++)
        {
            sum+=(p[i]/sump);
            if(random<=sum)
            {
                a.distance+=this.betwwenpoints(a.citynum, a.list_allow.get(i));
                a.citynum=a.list_allow.get(i);
                a.length++;
                a.list.add(a.citynum);
                a.list_allow=a.list_allow.remove(a.citynum);
                a.city_left--;
                break;
            }
        }
    }
    public double betwwenpoints(int m,int n)
    {
        Point point=points.get(m);
        return Math.sqrt(point.getdistence(points.get(n)));
    }
    public double getdistance(PathList li)
    {
        double distance=0;
        int m=li.n;
        while(li.next!=null)
        {

            distance+=this.betwwenpoints(li.n, li.next.n);
            li=li.next;
        }
        distance+=this.betwwenpoints(m, li.n);
        return distance;
    }



}
