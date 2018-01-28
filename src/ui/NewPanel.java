package ui;



import backends.Point;
import backends.PublicSettingIndex;
import backends.adapt;
import backends.list;

import javax.swing.*;

import java.awt.*;
import java.text.DecimalFormat;
import java.util.Vector;

public class NewPanel extends JPanel implements Runnable{
    DecimalFormat df=new DecimalFormat("######0.00");//���ս��������λС��
    Vector<Point> points=new Vector<Point>();//��ʼ��������
    PublicSettingIndex psi=null;//�������ò���
    double distance_max=0;//��¼��֮��������
    double distance_min=0;//��¼���Ž�
    double distance_now=0;//��¼ÿ�����Ž�
    list list_shortest=null;//��¼ÿ�����Ÿ���
    list list_01=null;
    adapt bestAdapt=null;//��¼���Ÿ���
    int N=0;//��Ⱥ������
    double rate_best=0.1;//���Ÿ��帴����
    double rate_mix=0;//������
    int best_index=0;//���Ÿ�����
    int time_all=200;//ѭ������
    int time_now=0;//��ǰ����
    double rate_change=0.02;//���������
    Vector<list> all=null;//��Ⱥ
    Vector<adapt> allAdapts=null;//��¼��Ⱥ��Ӧ��

    int scale=0;

    double distance_all=0;//
    double sum_same=0;//
    public NewPanel(Vector<Point> p, PublicSettingIndex psi){
        this.points=p;
        this.psi=psi;
        this.N=psi.N;
        this.time_all=psi.circle;
        list_shortest=new list();
        bestAdapt=new adapt();
        all=new Vector<list>();
        allAdapts=new Vector<adapt>();
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
        this.change();
        g.setColor(Color.yellow);
        this.show(list_shortest,g);
        g.setColor(Color.black);
        String str="��"+String.valueOf(time_now)+"��"+"���Ž�";
        String string=String.valueOf(df.format(distance_now));

        g.drawString(str, 505, 200);
        g.drawString(string, 505, 220);
        str="��ʷ���Ž�";
        string=String.valueOf(df.format(distance_min));
        g.drawString(str, 505, 400);
        g.drawString(string, 505, 420);
        str="��Ⱥ����:"+String.valueOf(N);
        string="���ܴ���:"+String.valueOf(time_all);
        g.drawString(str, 505, 300);
        g.drawString(string, 505, 320);
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
    public void show(list li,Graphics g){
        g.setColor(Color.yellow);

        g.drawString("��ʾ��������", 30, 520);
        g.setColor(Color.white);
        while(li.next!=null)
        {
            g.fillOval(li.n*scale, li.next.n*scale, scale, scale);
            li=li.next;
        }
        g.fillOval(20, 510, scale, scale);
    }
    //���֮��������
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
    //��Ⱥ��ʼ��
    public void get_group(int num){

        int size=this.points.size();
        int k=0;
        for(int i=0;i<num;i++)
        {
            list li=new list();//����ͷ
            list li_copy=li;
            k=0;
            while(k<size)
            {
                li_copy=li;
                int n=(int)(Math.random()*size);//����0��size-1�������
                boolean p=true;//��ֹ����ظ��ĵ����
                for(int j=0;j<k;j++)
                {
                    if(li_copy.n==n)
                    {
                        p=false;
                        break;
                    }
                    if(li_copy.next==null)
                        li_copy.next=new list();
                    li_copy=li_copy.next;
                }
                li_copy=li;
                for(int j=0;j<k;j++)
                {
                    if(li_copy.next==null)
                        li_copy.next=new list();
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
    //������Ⱥ��Ӧ��
    public void get_adapt(){
        distance_all=0;
        sum_same=0;

        if(allAdapts.size()!=0)
        {
            allAdapts.removeAllElements();
        }

        for(int i=0;i<all.size();i++)
        {
            //�ֱ�ȡ��ÿ�����������Ӧ��
            double distance_adapt=this.getdistance(all.get(i));
            adapt newAdapt=new adapt();
            distance_all+=distance_adapt;

            newAdapt.distance=distance_adapt;
            newAdapt.n=i;
            allAdapts.add(newAdapt);
        }
    }
    //����
    public void copy(){

        best_index=0;
        double copy_max=points.size()*distance_max;
        //copy_min=1;//�������
        double rate=0;//��¼���帴������ֵ
        double rate_sum=0;
        Vector<list>  new_all=new Vector<list>();//��һ����Ⱥ
        for(int i=0;i<allAdapts.size();i++)
        {
            adapt newAdapt=allAdapts.get(i);
            if(copy_max>newAdapt.distance)//��¼�������������ֵ���ø�����
            {
                copy_max=newAdapt.distance;
                best_index=i;
            }

        }
        //���ȸ������Ÿ���
        list_shortest=all.get(best_index);
        adapt newAdapts=allAdapts.get(best_index);
        if(newAdapts.distance<bestAdapt.distance||bestAdapt.distance==0)
        {
            bestAdapt=newAdapts;
            list_01=list_shortest;
        }
        distance_now=newAdapts.distance;
        if(distance_min>distance_now)
        {
            distance_min=distance_now;
        }
        for(int i=0;i<N*rate_best;i++)
        {
            new_all.add(all.get(best_index));

        }

        all.remove(best_index);
        allAdapts.remove(best_index);
        int num=0;
        while(num<N*0.9)
        {
            double min=distance_max*points.size();
            int index=0;
            for(int i=0;i<all.size();i++)
            {
                adapt nAdapt=allAdapts.get(i);
                if(min>nAdapt.distance)
                {
                    min=nAdapt.distance;
                    index=i;
                }
            }
            new_all.add(all.get(index));
            all.remove(index);
            allAdapts.remove(index);
            num++;
        }

        all=new_all;//������Ͻ���
        N=all.size();
    }
    //����
    public void mix(){

        rate_mix=0.1-0.1*(time_now-1)/(time_all-1);
        int pairs=(int)(N*rate_mix/2);//�ܽ������
        int pairs_now=0;//��ǰ��������Ķ���
        int size=points.size();//���������������
        int step=size/3;//���沽��
        while(pairs_now!=pairs)
        {
            pairs_now++;
            //��������������
            int a=(int)(Math.random()*N);
            int b=(int)(Math.random()*N);
            while(a==b)
            {
                b=(int)(Math.random()*N);
            }
            list li1=all.get(a);
            list li2=all.get(b);
            int start=(int)(size*Math.random());
            //�����λ��
            int mix_index1=0;
            int mix_index2=0;
            for(int i=start;i<size;i=i+step)
            {
                //�ֱ��ҵ�������
                for(int j=0;j<size;j++)
                {
                    if(li1.get(i)==li2.get(j))
                        mix_index1=j;
                    break;
                }
                for(int j=0;j<size;j++)
                {
                    if(li2.get(i)==li1.get(j))
                        mix_index2=j;
                    break;
                }
                //��ʼ����
                int temp=li1.get(i);
                li1.set(i, li1.get(mix_index2));
                li1.set(mix_index2, temp);
                temp=li2.get(i);
                li2.set(i, li2.get(mix_index1));
                li2.set(mix_index1, temp);
            }
            all.remove(a);
            all.add(a,li1);
            all.remove(b);
            all.add(b,li2);

        }
    }
    //����

    public void change() {

        for(int i=0;i<N*rate_change;i++)
        {
            //����������������

            int x=(int)(Math.random()*N);
            list lix=all.get(x);

            //��������������Ƭ��
            int a=(int)(Math.random()*points.size());
            int b=(int)(Math.random()*points.size());

            //��ʼ����
            int temp=lix.get(a);
            lix.set(a, lix.get(b));
            lix.set(b, temp);

            all.remove(x);
            all.add(x,lix);

        }

    }
    public double betwwenpoints(int m,int n)
    {
        Point point=points.get(m);
        return Math.sqrt(point.getdistence(points.get(n)));
    }
    public double getdistance(list li)
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

