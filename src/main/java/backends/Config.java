package backends;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;

public class Config {
    public static int refreshInterval =1;//���н���ˢ��ʱ��������
    public static double scale=1;//����Drawpanel��ͼ��Χ
    public static boolean flag=true;
    public static int algoType =1;
    public static List<Integer> shortestPath = new ArrayList<>();//��¼���·��
    public static int N=100;//��¼��Ⱥ����
    public static double shortestDistance =0;//��¼��̾���
    public static int generations =200;//��¼��Ⱥ���ܴ���
    public static int state=0;
    public static double copyRate =0.1;//���Ÿ��帴����
    public Config(){

    }
    public int gettime(){
        return refreshInterval;
    }
    public void settime(int time){
        this.refreshInterval =time;
    }
    public double getscale(){
        return scale;
    }
    public void setscale(double s){
        this.scale=s;
    }
    public void setflag(boolean p){
        this.flag=p;
    }
    public boolean getflag(){
        return this.flag;
    }
}

