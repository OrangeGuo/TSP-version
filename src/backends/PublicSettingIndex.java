package backends;

import java.util.Vector;

public class PublicSettingIndex {
    private static int time_flush=1;//���н���ˢ��ʱ��������
    private static double scale=1;//����Drawpanel��ͼ��Χ
    public static boolean flag=true;
    public static boolean suanfa_flag=true;
    public static Vector path_short=new Vector();//��¼���·��
    public static int N=100;//��¼��Ⱥ����
    public static double distance=0;//��¼��̾���
    public static int circle=200;//��¼��Ⱥ���ܴ���
    public static int state=0;
    public PublicSettingIndex(){

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

