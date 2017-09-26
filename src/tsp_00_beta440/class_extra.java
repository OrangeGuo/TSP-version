
package tsp_00_beta440;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Vector;

import javax.swing.JPanel;







class PublicSettingIndex {
	private static int time_flush=1;//运行界面刷新时间间隔控制
	private static double scale=1;//控制Drawpanel绘图范围
	public static boolean flag=true;
	public static boolean suanfa_flag=true;
	public static Vector path_short=new Vector();//记录最短路径
	public static int N=100;//记录种群数量
	public static double distance=0;//记录最短距离
	public static int circle=200;//记录种群繁衍代数
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
class Point{
	private int x=0;
	private int y=0;
	private int n=0;
	public Point(){}
	public void setdata(int x,int y,int n){
		this.x=x;
		this.y=y;
		this.n=n;
	}
	public int getx(){
		return x;
	}
	public int gety(){
		return y;
	}
	public int getn(){
		return n;
	}
	public int getdistence(Point p){
		int dx=this.x-p.getx();
		int dy=this.y-p.gety();
		return dx*dx+dy*dy;
		}
}


//链表用以记录路径(每个路径视为一个个体)
class list{
	public int n=-1;
	public list next=null;
	
	public list(){
		
	}
	public int get(int i)
	{
		if(i==0)
		{
			return this.n;
		}
		else 
		{
			list list_now=this;
			for(int j=0;j<i;j++)
			{
				list_now=list_now.next;
			}
			return list_now.n;
		}
	}
	public void set(int i,int temp)
	{
		if(i==0)
		{
			this.n=temp;
		}
		else {
			list list_now=this;
			for(int j=0;j<i;j++)
			{
				list_now=list_now.next;
			}
			list_now.n=temp;
		}
	}
	public list setfirst(int n)
	{
		if(this.n==n)
		{
			return this;
		}
		else {
			list list_copy=this;
			list list_first=this;
			while(list_copy.next!=null)
			{
				list_copy=list_copy.next;
			}
			while(list_first.next!=null)
			{
				list_copy.next=new list();
				list_copy.next.n=list_first.n;
				list_copy=list_copy.next;
				list_first=list_first.next;
				if(list_first.n==n)
				{
					return list_first;
				}
				if(list_first.n==this.n)
				{
					break;
				}
			}
			return new list();
		}
	}
}
//适应度
class adapt{
	public int n=0;//个体编号
	public double distance=0;//该个体(路径)总距离
	public adapt(){
		
	}
}
