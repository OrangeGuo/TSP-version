package tsp_00_beta440;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Panel;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DecimalFormat;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


//求解时的运行窗口(弹出)
class FunctionView extends JDialog implements MouseListener{

	JPanel jp2=null;
	DrawPanel dp=null;
	NewPanel np=null;
	JButton jb=null;
	JTextField jtf1=null;
	JTextField jtf2=null;

	public FunctionView (Vector<Point> p,PublicSettingIndex psi){
        
		jp2=new JPanel();
		jb=new JButton("exit");
		jb.addMouseListener(this);

		jp2.setBackground(Color.CYAN);

		jp2.add(jb);
	
		if(psi.suanfa_flag){
			dp=new DrawPanel(p,psi);
			   Thread thread=new Thread(dp);
			   thread.start();
			this.add(dp);
		}
		else {
		np=new NewPanel(p,psi);
		Thread thread=new Thread(np);
		   thread.start();
		   this.add(np);
		}
		this.add(jp2,BorderLayout.SOUTH);
		Image a=Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/tu/背景图片.jpg"));
		this.setIconImage(a);
		this.setSize(600, 600);
		this.setTitle("Run for answer");
		this.setLocationRelativeTo(null);
	    this.setModal(true);
		this.setResizable(false);
		this.dispose();
		this.setVisible(true);
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource().equals(jb)){
			this.dispose();
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
class NewPanel extends JPanel implements Runnable{
	DecimalFormat df=new DecimalFormat("######0.00");//最终结果保留两位小数
	Vector<Point> points=new Vector<Point>();//初始化点向量
	PublicSettingIndex psi=null;//接收设置参数
	double distance_max=0;//记录点之间最大距离
	double distance_min=0;//记录最优解
	double distance_now=0;//记录每代最优解
	list list_shortest=null;//记录每代最优个体
	list list_01=null;
	adapt bestAdapt=null;//记录最优个体
	int N=0;//种群个体数
	double rate_best=0.1;//最优个体复制率
	double rate_mix=0;//交叉率
	int best_index=0;//最优个体编号
	int time_all=200;//循环代数
	int time_now=0;//当前代数
	double rate_change=0.02;//个体变异率
	Vector<list> all=null;//种群
	Vector<adapt> allAdapts=null;//记录种群适应度
	
	int scale=0;
	
	double distance_all=0;//
	double sum_same=0;//
	public NewPanel(Vector<Point> p,PublicSettingIndex psi){
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
			this.change();
			g.setColor(Color.yellow);
			this.show(list_shortest,g);
			g.setColor(Color.black);
			String str="第"+String.valueOf(time_now)+"代"+"最优解";
			String string=String.valueOf(df.format(distance_now));
			 
			g.drawString(str, 505, 200);
			g.drawString(string, 505, 220);
			str="历史最优解";
			string=String.valueOf(df.format(distance_min));
			g.drawString(str, 505, 400);
			g.drawString(string, 505, 420);
			str="种群数量:"+String.valueOf(N);
			string="繁衍代数:"+String.valueOf(time_all);
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
	
		g.drawString("表示单个个体", 30, 520);
		g.setColor(Color.white);
		while(li.next!=null)
		{
			g.fillOval(li.n*scale, li.next.n*scale, scale, scale);
			li=li.next;
		}
		g.fillOval(20, 510, scale, scale);
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
            list li=new list();//链表头
            list li_copy=li;
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
//计算种群适应度
		public void get_adapt(){
            distance_all=0;
            sum_same=0;

			if(allAdapts.size()!=0)
			{
				allAdapts.removeAllElements();
			}
			
			for(int i=0;i<all.size();i++)
			{
				//分别取出每个个体计算适应度
				double distance_adapt=this.getdistance(all.get(i));
				adapt newAdapt=new adapt();
				distance_all+=distance_adapt;
				
				newAdapt.distance=distance_adapt;
				newAdapt.n=i;
				allAdapts.add(newAdapt);
			}
		}
//复制
		    public void copy(){
	
			  best_index=0;
              double copy_max=points.size()*distance_max;
              //copy_min=1;//清空数据
              double rate=0;//记录个体复制期望值
              double rate_sum=0;
              Vector<list>  new_all=new Vector<list>();//新一代种群
              for(int i=0;i<allAdapts.size();i++)
              {
            	  adapt newAdapt=allAdapts.get(i);
            	  if(copy_max>newAdapt.distance)//记录个体最大复制期望值及该个体编号
            	  {
            		  copy_max=newAdapt.distance;
            		  best_index=i;
            	  }
            
              }
              //优先复制最优个体
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
             
             all=new_all;//完成新老交替
              N=all.size();
		}
		//交叉
		    public void mix(){
				
				rate_mix=0.1-0.1*(time_now-1)/(time_all-1);
				int pairs=(int)(N*rate_mix/2);//总交叉对数
				int pairs_now=0;//当前发生交叉的对数
				int size=points.size();//单个个体基因总数
				int step=size/3;//交叉步长
				while(pairs_now!=pairs)
				{
					pairs_now++;
					//随机产生交叉个体
					int a=(int)(Math.random()*N);
					int b=(int)(Math.random()*N);
					while(a==b)
					{
						b=(int)(Math.random()*N);
					}
					list li1=all.get(a);
					list li2=all.get(b);
					int start=(int)(size*Math.random());
					//交叉点位置
					int mix_index1=0;
					int mix_index2=0;
					for(int i=start;i<size;i=i+step)
					{
						//分别找到交换点
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
						//开始交叉
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
		//变异
	
		public void change() {

			for(int i=0;i<N*rate_change;i++)
			{
				//随机产生变异个体编号
		
				int x=(int)(Math.random()*N);
				list lix=all.get(x);
			
				//随机产生交叉基因片段
				int a=(int)(Math.random()*points.size());
				int b=(int)(Math.random()*points.size());
	
				//开始变异
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
