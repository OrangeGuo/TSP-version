package backends;

import java.util.Vector;

public class Config {
    private static int time_flush=1;//运行界面刷新时间间隔控制
    private static double scale=1;//控制Drawpanel绘图范围
    public static boolean flag=true;
    public static int suanfa_flag=1;
    public static Vector path_short=new Vector();//记录最短路径
    public static int N=100;//记录种群数量
    public static double distance=0;//记录最短距离
    public static int circle=200;//记录种群繁衍代数
    public static int state=0;
    public static double rate_copy=0.1;//最优个体复制率
    public Config(){

    }
    public int gettime(){
        return time_flush;
    }
    public void settime(int time){
        this.time_flush=time;
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

