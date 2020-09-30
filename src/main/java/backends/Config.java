package backends;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;

public class Config {
    public static int refreshInterval =1;//运行界面刷新时间间隔控制
    public static double scale=1;//控制Drawpanel绘图范围
    public static boolean flag=true;
    public static int algoType =1;
    public static List<Integer> shortestPath = new ArrayList<>();//记录最短路径
    public static int N=100;//记录种群数量
    public static double shortestDistance =0;//记录最短距离
    public static int generations =200;//记录种群繁衍代数
    public static int state=0;
    public static double copyRate =0.1;//最优个体复制率
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

