package frontends.frame;

import backends.Path;
import backends.City;
import backends.Config;
import backends.Adapt;
import com.google.common.collect.Lists;

import javax.swing.*;


import java.awt.*;
import java.text.DecimalFormat;
import java.util.List;

public class GeneticPanel extends JPanel implements Runnable{
    DecimalFormat df=new DecimalFormat("######0.00");//���ս��������λС��
    List<City> points=Lists.newArrayList();
    Config psi=null;//�������ò���
    double distance_max=0;//��¼��֮��������
    double distance_min=0;//��¼���Ž�
    double distance_now=0;//��¼ÿ�����Ž�
    Path list_shortest=null;//��¼ÿ�����Ÿ���
    Path list_01=null;
    Adapt bestAdapt=null;//��¼���Ÿ���
    int N=0;//��Ⱥ������
    double rate_best=0.1;//���Ÿ��帴����
    double rate_mix=0;//������
    int best_index=0;//���Ÿ�����
    int time_all=20;//ѭ������
    int time_now=0;//��ǰ����
    double rate_change=0.05;//���������
    List<Path> all=null;//��Ⱥ
    List<Adapt> allAdapts=Lists.newArrayList();//��¼��Ⱥ��Ӧ��

    int scale=0;

    double distance_all=0;//
    double sum_same=0;//
    public GeneticPanel(List<City> p, Config psi){
        this.points=p;
        this.psi=psi;
        this.N= Config.N;
        this.rate_best= Config.copyRate;
        this.time_all= Config.generations;

        list_shortest=new Path();
        bestAdapt=new Adapt();
        all=Lists.newArrayList();
        allAdapts=Lists.newArrayList();
        this.get_max();
        this.get_group(N);
        scale=500/p.size();
    }
    public void paint(Graphics g)
    {
        //���ظ��෽��
        super.paint(g);
        g.fillRect(0, 0, 500, 600);
        this.start(g);
    }
    //�����㷨����ͼ
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
        String str="��"+String.valueOf(time_now)+"��"+"���Ž�";
        String string=String.valueOf(df.format(distance_now));

        g.drawString(str, 505, 360);
        g.drawString(string, 505, 380);
        str="��ʷ���Ž�";
        string=String.valueOf(df.format(distance_min));
        g.drawString(str, 505, 400);
        g.drawString(string, 505, 420);
        str="��Ⱥ����:"+String.valueOf(N);
        string="���ܴ���:"+String.valueOf(time_all);
        g.drawString(str, 505, 130);
        g.drawString(string, 505, 150);
        string="���Ÿ�����:"+String.valueOf(df.format(rate_best));
        g.drawString(string, 505, 170);
        if(time_now==time_all)
        {
            Config.shortestPath.clear();
            while(list_01!=null){
                Config.shortestPath.add(list_01.no);
                list_01=list_01.next;
            }
            Config.shortestDistance =distance_min;
            Config.state++;
        }
    }
    public void show(Path li, Graphics g){
        g.setColor(Color.yellow);

        g.drawString("��ʾ����", 30, 520);
        g.setColor(Color.white);
        while(li.next!=null)
        {
            g.fillOval(li.no *scale, li.next.no *scale, scale, scale);
            li=li.next;
        }
        g.fillOval(20, 510, 10, 10);
    }
    //���֮��������
    public void get_max(){

        for(int i=0;i<points.size()-1;i++)
        {
            City point_start=points.get(i);
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
    //��Ⱥ��ʼ��
    public void get_group(int num){

        int size=this.points.size();
        int k=0;
        for(int i=0;i<num;i++)
        {
            Path li=new Path();//����ͷ
            Path li_copy=li;
            k=0;
            while(k<size)
            {
                li_copy=li;
                int n=(int)(Math.random()*size);//����0��size-1�������
                boolean p=true;//��ֹ����ظ��ĵ����
                for(int j=0;j<k;j++)
                {
                    if(li_copy.no ==n)
                    {
                        p=false;
                        break;
                    }
                    if(li_copy.next==null)
                        li_copy.next=new Path();
                    li_copy=li_copy.next;
                }
                li_copy=li;
                for(int j=0;j<k;j++)
                {
                    if(li_copy.next==null)
                        li_copy.next=new Path();
                    li_copy=li_copy.next;

                }
                if(p)
                {
                    li_copy.no =n;
                    k++;
                }
            }
            all.add(li);
        }

    }
    //������Ⱥ��Ӧ��
    public void get_adapt(){


        if(allAdapts.size()!=0)
        {
            allAdapts= Lists.newArrayList();
        }

        for(int i=0;i<all.size();i++)
        {
            //�ֱ�ȡ��ÿ�����������Ӧ��
            double distance_adapt=this.getdistance(all.get(i));

            Adapt newAdapt=new Adapt();
            newAdapt.setDistance(distance_adapt);
            newAdapt.setNo(i);
            allAdapts.add(newAdapt);
        }

    }
    public void show(Path l)
    {
        System.out.print(' ');
        for(int i=0;i<points.size();i++)
        {
            System.out.print(l.get(i)+" ");
        }
        System.out.println();
    }
    //����
    public void copy(){

        best_index=0;
        double copy_max=2000000000;


        for(int i=0;i<allAdapts.size();i++)
        {
            Adapt newAdapt=allAdapts.get(i);
            if(copy_max>newAdapt.getDistance())//��¼�������������ֵ���ø�����
            {
                copy_max=newAdapt.getDistance();
                best_index=i;
            }

        }


        list_shortest=all.get(best_index);
        Adapt newAdapts=allAdapts.get(best_index);
        if(newAdapts.getDistance()<bestAdapt.getDistance()||bestAdapt.getDistance()==0)
        {
            bestAdapt=newAdapts;
            list_01=new Path(list_shortest);
        }

        distance_now=newAdapts.getDistance();
        if(distance_min>distance_now)
        {
            distance_min=distance_now;
        }

        for(int i=0;i<N*(rate_best);i++)
        {
            Path newlList=new Path(list_01);
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

                if(max<nAdapt.getDistance())
                {
                    max=nAdapt.getDistance();

                    index=i;

                }

            }
            all.remove(index);
            allAdapts.remove(index);
        }


        N=all.size();
    }
    //����
    public void mix(){
        int size=points.size();//���������������
        int a=-2,b=-1;//���沽��
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
                Path li1=all.get(a);
                Path li2=all.get(b);
                //PathList w1=new PathList(li1);
                //PathList w2=new PathList(li2);
                //all.add(w1);
                //all.add(w2);
                //�����λ

                //��ʼ����
                int temp=li1.get(x);
                li1.set(x, li1.get(y));
                li1.set(y, temp);
                temp=li2.get(x);
                li2.set(x, li2.get(y));
                li2.set(y, temp);

            }


        }

    }
    //����

    public void change() {
        for(int i=0;i<N;i++)
        {

            Path lix=all.get(i);
            //PathList wList=new PathList(lix);
            //all.add(wList);
            //��������������Ƭ��
            int a=(int)(Math.random()*points.size());
            if(a==points.size()-1) a--;

            //��ʼ����
            int temp=lix.get(a);
            lix.set(a, lix.get(a+1));
            lix.set(a+1, temp);
        }
    }
    public void explode(){
        for(int i=0;i<N;i++)
        {

            Path lix=all.get(i);
            //PathList wList=new PathList(lix);
            //all.add(wList);
            //��������������Ƭ��
            double di=allAdapts.get(i).getDistance()/points.size();
            for(int j=0;j<points.size()-1;j++)
            {
                if(this.betwwenpoints(j, j+1)>di)
                {
                    //��ʼ����
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
