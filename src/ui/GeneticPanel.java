package ui;

import backends.PathList;
import backends.Point;
import backends.PublicSettingIndex;
import backends.Adapt;

import javax.swing.*;

import java.awt.*;
import java.text.DecimalFormat;
import java.util.Vector;

public class GeneticPanel extends JPanel implements Runnable{
    DecimalFormat df=new DecimalFormat("######0.00");//最终结果保留两位小数
    Vector<Point> points=new Vector<Point>();//初始化点向量
    PublicSettingIndex psi=null;//接收设置参数
    double distance_max=0;//记录点之间最大距离
    double distance_min=0;//记录最优解
    double distance_now=0;//记录每代最优解
    PathList list_shortest=null;//记录每代最优个体
    PathList list_01=null;
    Adapt bestAdapt=null;//记录最优个体
    int N=0;//种群个体数
    double rate_best=0.1;//最优个体复制率
    double rate_mix=0;//交叉率
    int best_index=0;//最优个体编号
    int time_all=20;//循环代数
    int time_now=0;//当前代数
    double rate_change=0.05;//个体变异率
    Vector<PathList> all=null;//种群
    Vector<Adapt> allAdapts=null;//记录种群适应度

    int scale=0;

    double distance_all=0;//
    double sum_same=0;//
    public GeneticPanel(Vector<Point> p, PublicSettingIndex psi){
        this.points=p;
        this.psi=psi;
        this.N=psi.N;
        this.rate_best=psi.rate_copy;
        this.time_all=psi.circle;

        list_shortest=new PathList();
        bestAdapt=new Adapt();
        all=new Vector<PathList>();
        allAdapts=new Vector<Adapt>();
        this.get_max();
        this.get_group(N);
        scale=500/p.size();
    }
    public void paint(Graphics g)
    {
        //重载父类方法
        super.paint(g);
        g.fillRect(0, 0, 500, 600);
        this.start(g);
    }
    //启动算法并绘图
    public void start(Graphics g)
    {
        g.setColor(Color.cyan);
        this.get_adapt();
        this.copy();
        this.mix();
        this.get_adapt();
        this.copy();
        this.change();

        g.setColor(Color.yellow);
        this.show(list_shortest,g);
        g.setColor(Color.black);
        String str="第"+String.valueOf(time_now)+"代"+"最优解";
        String string=String.valueOf(df.format(distance_now));

        g.drawString(str, 505, 360);
        g.drawString(string, 505, 380);
        str="历史最优解";
        string=String.valueOf(df.format(distance_min));
        g.drawString(str, 505, 400);
        g.drawString(string, 505, 420);
        str="种群数量:"+String.valueOf(N);
        string="繁衍代数:"+String.valueOf(time_all);
        g.drawString(str, 505, 130);
        g.drawString(string, 505, 150);
        string="最优复制率:"+String.valueOf(df.format(rate_best));
        g.drawString(string, 505, 170);
        if(time_now==time_all)
        {
            psi.path_short.removeAllElements();
            while(list_01!=null){
                psi.path_short.add(list_01.n);
                list_01=list_01.next;
            }
            psi.distance=distance_min;
            psi.state++;
        }
    }
    public void show(PathList li, Graphics g){
        g.setColor(Color.yellow);

        g.drawString("表示个体", 30, 520);
        g.setColor(Color.white);
        while(li.next!=null)
        {
            g.fillOval(li.n*scale, li.next.n*scale, scale, scale);
            li=li.next;
        }
        g.fillOval(20, 510, 10, 10);
    }
    //求点之间最大距离
    public void get_max(){

        for(int i=0;i<points.size()-1;i++)
        {
            Point point_start=points.get(i);
            for(int j=i+1;j<points.size();j++)
            {
                double distance=Math.sqrt(point_start.getdistence(points.get(j)));
                if(this.distance_max<distance)
                {
                    this.distance_max=distance;
                }
            }
        }
        distance_min=points.size()*distance_max;

    }
    //种群初始化
    public void get_group(int num){

        int size=this.points.size();
        int k=0;
        for(int i=0;i<num;i++)
        {
            PathList li=new PathList();//链表头
            PathList li_copy=li;
            k=0;
            while(k<size)
            {
                li_copy=li;
                int n=(int)(Math.random()*size);//生成0到size-1的随机数
                boolean p=true;//防止添加重复的点序号
                for(int j=0;j<k;j++)
                {
                    if(li_copy.n==n)
                    {
                        p=false;
                        break;
                    }
                    if(li_copy.next==null)
                        li_copy.next=new PathList();
                    li_copy=li_copy.next;
                }
                li_copy=li;
                for(int j=0;j<k;j++)
                {
                    if(li_copy.next==null)
                        li_copy.next=new PathList();
                    li_copy=li_copy.next;

                }
                if(p)
                {
                    li_copy.n=n;
                    k++;
                }
            }
            all.add(li);
        }

    }
    //计算种群适应度
    public void get_adapt(){


        if(allAdapts.size()!=0)
        {
            allAdapts=new Vector<Adapt>();
        }

        for(int i=0;i<all.size();i++)
        {
            //分别取出每个个体计算适应度
            double distance_adapt=this.getdistance(all.get(i));

            Adapt newAdapt=new Adapt();
            newAdapt.distance=distance_adapt;

            newAdapt.n=i;
            allAdapts.add(newAdapt);
        }

    }
    public void show(PathList l)
    {
        System.out.print(' ');
        for(int i=0;i<points.size();i++)
        {
            System.out.print(l.get(i)+" ");
        }
        System.out.println();
    }
    //复制
    public void copy(){

        best_index=0;
        double copy_max=2000000000;


        for(int i=0;i<allAdapts.size();i++)
        {
            Adapt newAdapt=allAdapts.get(i);
            if(copy_max>newAdapt.distance)//记录个体最大复制期望值及该个体编号
            {
                copy_max=newAdapt.distance;
                best_index=i;
            }

        }


        list_shortest=all.get(best_index);
        Adapt newAdapts=allAdapts.get(best_index);
        if(newAdapts.distance<bestAdapt.distance||bestAdapt.distance==0)
        {
            bestAdapt=newAdapts;
            list_01=new PathList(list_shortest);
        }

        distance_now=newAdapts.distance;
        if(distance_min>distance_now)
        {
            distance_min=distance_now;
        }

        for(int i=0;i<N*(rate_best);i++)
        {
            PathList newlList=new PathList(list_01);
            Adapt adapt=new Adapt();
            adapt=bestAdapt;
            all.add(0,newlList);
            allAdapts.add(0,adapt);
        }


        while(all.size()>N)
        {
            double max=0;
            int index=0;
            for(int i=0;i<allAdapts.size();i++)
            {
                Adapt nAdapt=allAdapts.get(i);

                if(max<nAdapt.distance)
                {
                    max=nAdapt.distance;

                    index=i;

                }

            }
            all.remove(index);
            allAdapts.remove(index);
        }


        N=all.size();
    }
    //交叉
    public void mix(){
        int size=points.size();//单个个体基因总数
        int a=-2,b=-1;//交叉步长
        while(b<N)
        {
            a+=2;
            b+=2;

            if(b>=N)break;
            if(true)
            {
                int x=(int)(Math.random()*size);
                int y=(int)(Math.random()*size);
                while(x==y)
                {
                    y=(int)(Math.random()*size);
                }
                PathList li1=all.get(a);
                PathList li2=all.get(b);
                //PathList w1=new PathList(li1);
                //PathList w2=new PathList(li2);
                //all.add(w1);
                //all.add(w2);
                //交叉点位

                //开始交叉
                int temp=li1.get(x);
                li1.set(x, li1.get(y));
                li1.set(y, temp);
                temp=li2.get(x);
                li2.set(x, li2.get(y));
                li2.set(y, temp);

            }


        }

    }
    //变异

    public void change() {
        for(int i=0;i<N;i++)
        {

            PathList lix=all.get(i);
            //PathList wList=new PathList(lix);
            //all.add(wList);
            //随机产生交叉基因片段
            int a=(int)(Math.random()*points.size());
            if(a==points.size()-1) a--;

            //开始变异
            int temp=lix.get(a);
            lix.set(a, lix.get(a+1));
            lix.set(a+1, temp);
        }
    }
    public void explode(){
        for(int i=0;i<N;i++)
        {

            PathList lix=all.get(i);
            //PathList wList=new PathList(lix);
            //all.add(wList);
            //随机产生交叉基因片段
            double di=allAdapts.get(i).distance/points.size();
            for(int j=0;j<points.size()-1;j++)
            {
                if(this.betwwenpoints(j, j+1)>di)
                {
                    //开始变异
                    int temp=lix.get(j);
                    lix.set(j, lix.get(j+1));
                    lix.set(j+1, temp);
                    break;
                }


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
    @Override
    public void run() {
        // TODO Auto-generated method stub
        while(time_now<time_all)
        {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            time_now++;
            this.repaint();
        }
    }
}
