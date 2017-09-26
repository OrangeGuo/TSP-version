package tsp_00_beta440;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.text.DecimalFormat;
import java.util.Vector;

import javax.swing.JPanel;

class MyPanel extends JPanel implements Runnable{
    int time=6;

    
	public void paint(Graphics g)
	{
		super.paint(g);
		g.fillRect(0, 0, 600, 480);
		g.setColor(Color.yellow);
		Font font=new Font("宋体",Font.BOLD,100);
		this.setFont(font);
		this.paints(g);
	}
    public void paints(Graphics g){
    	if(time<4){
			g.drawString(String.valueOf(time), 250, 240);
		}
		else
		{
			g.drawString("TSP问题", 120, 240);
	
		}
    }
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(time>0)
		{
			try {
				Thread.sleep(1000);
				time--;
		
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				}
			this.repaint();
		
		}
	}
		
	
}

class DrawPanel extends JPanel implements Runnable{
	Vector<Point> points=new Vector<Point>();
	Vector<Point> point=new Vector<Point>();
	DecimalFormat df=new DecimalFormat("######0.00");
	int index_all=0;
	int index_min=0;
	double scale=1;
	int size=7;
	double distance_min=100000000;
	double distance_now=0;
	boolean p=true;
	boolean get=false;
    long Time_start=System.currentTimeMillis();
    long Time_total=0;
    long time=10;//最小刷新时间间隔10毫秒
    public PublicSettingIndex psi=null;
	public DrawPanel(Vector<Point> p,PublicSettingIndex psi){
		point=p;
        scale=psi.getscale();
		time*=psi.gettime();
		this.psi=psi;
	}
	public void paint(Graphics g)
	{
		super.paint(g);
		g.setColor(Color.black);
		g.fillRect(0, 0, 600, 600);
		g.setColor(Color.white);
		for(int i=0;i<point.size();i++)
		{
			Point p=point.get(i);
			g.fillOval((int)(p.getx()*scale),(int)(p.gety()*scale),size,size);
		}
	
	
		if(index_all<point.size())
		{
			this.drawline(g,index_all);
			index_all++;
		}
		else
		{
			if(p) get=true;
			p=false;
		
			this.drawline(g,index_min);
		}
		
	
		
		
		
	}
	public void drawline(Graphics g,int n){
		for(int i=0;i<point.size();i++)
		{
			points.add(point.get(i));
		}
		int index=n;
		
		Point point_01=points.get(index);
		if(get&&this.psi.flag)
		{
			this.psi.path_short.add(point_01.getn());
		}
		g.setColor(Color.red);
		g.fillOval((int)(point_01.getx()*scale-2),(int)(point_01.gety()*scale-2), 10, 10);
		g.setColor(Color.blue);
		while(true)
		{
		      Point point_first=points.get(index); 
		      points.remove(index);
		  	  double distance=2000000000;
		  	 
		      for(int i=0;i<points.size();i++)
		      {
			         Point point_now=points.get(i);
			         if(distance>point_first.getdistence(point_now))
			         {
				           distance=point_first.getdistence(point_now);
				           index=i;
			         }
		       }
		      
		       Point point_near=points.get(index);
		       if(get&&this.psi.flag)
		       {
		    	   this.psi.path_short.add(point_near.getn());
		       }
		       g.drawLine((int)(point_first.getx()*scale+3),(int)(point_first.gety()*scale+3),(int)(point_near.getx()*scale+3),(int)(point_near.gety()*scale+3));
		       distance_now+=Math.sqrt(distance);
		       if(points.size()==1)
			  	  {
			  		  break;
			  	  }
		     
		}
		Point point_last=points.get(0);
		get=false;
		points.remove(0);
		distance_now+=Math.sqrt(point_01.getdistence(point_last));
		g.drawLine((int)(point_01.getx()*scale+3),(int)(point_01.gety()*scale+3),(int)(point_last.getx()*scale+3),(int)(point_last.gety()*scale+3));
		g.setColor(Color.cyan);
		String s=String.valueOf(df.format(distance_now));
		if(distance_min>distance_now){
			distance_min=distance_now;
			index_min=index_all;
		}
		g.drawString("时间仅供参考", 500, 20);
		g.drawString("红色圆点为起点", 500, 40);
		if(p){
			 g.drawString("当前总距离:"+s, 0,520);
			 s=String.valueOf(df.format(distance_min));
			 g.drawString("当前最短距离:"+s, 450, 520);
			 Time_total=(System.currentTimeMillis()-Time_start);
			 s=String.valueOf(Time_total);
			 g.drawString("总用时:"+s+"毫秒", 250, 520);
		}
		else{
			 g.drawString("最短距离:"+s, 150,520);
			 s=String.valueOf(Time_total);
			 g.drawString("总用时:"+s+"毫秒",350, 520);
			 this.psi.distance=distance_min;
			 if(this.psi.state==1)
			 {
				 this.psi.state++;
				 this.psi.flag=false;
			 }
			 
		}
		
		distance_now=0;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true)
		{
			try {
				Thread.sleep(time);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				}
			this.repaint();
		}
	}
}

