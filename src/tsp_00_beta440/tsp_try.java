package tsp_00_beta440;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Panel;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import javax.swing.filechooser.FileSystemView;


//主要交互窗口
public class tsp_try extends JFrame implements MouseListener,ActionListener,Runnable {

	/**
	 * @param args
	 * 程序设计:郭大为
	 * 开始日期:16年09月06日
	 * 程序功能:解决TSP问题 
	 * 完成日期:16年
	 */
	//自定义组件
	MyPanel mp=null;
    DirectoryFrame dFrame=null;
	HelpFrame hf=null;
	SettingFrame sf=null;
	FunctionView fv=null;
	PublicSettingIndex psi=null;
	Vector<Point> points=null;
	//swing组件
	JToolBar jtl=null;
	JPanel jp_01=null;
	JPanel jp_02=null;
	JLabel jl=null;
	JLabel jl2=null;
	JLabel jl3=null;
	JScrollPane jsp=null;
	JComboBox jcb=null;
	JButton jb1=null;
	JButton jb2=null;
	JButton jb3=null;
	JMenuBar jmb=null;
	JMenu jm1=null;
	JMenu jm2=null;
	JMenu jm3=null;
	JTextField jtf=null;
	JTextArea jta=null;
	JMenuItem jmi2=null;
	JMenuItem jmi3=null;
	JMenuItem jmi1=null;
	JMenuItem jmi4=null;
	JMenuItem jmi5=null;
	JMenuItem jmi6=null;
	JMenuItem jmi7=null;

	
	boolean p=false;//防止JFrame窗体重复添加JPanel组件
	boolean flag=false;//是否按要求读入文件
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		tsp_try tsp=new tsp_try();
		 Thread t=new Thread(tsp);
 		 t.start();
 	
 	
	}
	public tsp_try(){
		//初始化控件
		jp_01=new JPanel();
		jp_02=new JPanel();

        jsp=new JScrollPane();
		psi=new PublicSettingIndex();
		mp=new MyPanel();
		jmb=new JMenuBar();
		
		jm2=new JMenu("帮助设置");
		jm3=new JMenu("退出程序");
		jl=new JLabel("选择算法");
		jl2=new JLabel("状态栏");
		jtl=new JToolBar();
	
        jsp.setBackground(Color.white);
		jb1=new JButton("运行");
		jb2=new JButton("读入数据");
		jb3=new JButton("查看路径");
		String []option={"贪心算法","遗传算法"};
	
		jcb=new JComboBox(option);
       
	
		jmi4=new JMenuItem("立即退出");
		jmi5=new JMenuItem("参数设置");
		jmi6=new JMenuItem("帮助文档");
		jmi7=new JMenuItem("关于开发");
		//注册监听
   
        jb1.addMouseListener(this);
        jb2.addMouseListener(this);
        jb3.addMouseListener(this);
		jmi4.addActionListener(this);
		jmi4.setActionCommand("four");
		jmi5.addActionListener(this);
		jmi5.setActionCommand("help");
		jmi6.addActionListener(this);
		jmi6.setActionCommand("six");
		jmi7.addActionListener(this);
		jmi7.setActionCommand("seven");
		//组合控件
	 
	    //jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		jp_01.setBackground(Color.white);
		jtl.add(jl2);
		jp_02.add(jb2);
		jp_02.add(jl);
		jp_02.add(jcb);
		jp_02.add(jb1);
	     jp_02.add(jb3);
		jp_02.setBackground(Color.lightGray);


		jmb.add(jm2);
		jmb.add(jm3);

		jm2.add(jmi5);
		jm2.add(jmi6);
		jm2.add(jmi7);
		jm3.add(jmi4);
        
		//窗体属性设置
		 this.setSize(600,480);
		 //this.setUndecorated(true);
		 this.setLocationRelativeTo(null);
    	 this.setTitle("tsp问题");
    	 this.setResizable(false);
    	
    	 Thread thread=new Thread(mp);
 		 thread.start();
    	 this.add(mp);
    	 Image a=Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/tu/p1.jpg"));
 		 this.setIconImage(a);
    	 this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	 this.setVisible(true);
    
    

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource().equals(jb1)){
			if(flag){
				this.psi.flag=true;
				this.psi.path_short.removeAllElements();
				if(this.jcb.getSelectedItem().equals("遗传算法"))
				{
					this.psi.suanfa_flag=false;
				}
				else {
					this.psi.suanfa_flag=true;
				}
				fv=new FunctionView(points,psi);
			}
			else{
				JOptionPane.showMessageDialog(null, "未按要求正确读入文件！！！","警告",JOptionPane.ERROR_MESSAGE);
			}
			
		}
	    else if(e.getSource().equals(jb3)){
	    	if(this.psi.state==2)
			{
	    	DecimalFormat df=new DecimalFormat("######0.00");
			String s="共有"+String.valueOf(this.psi.path_short.size())+"个点\n最短距离为"+String.valueOf(df.format(this.psi.distance))+"\n"+"最短路径如下\n";
			for(int i=0;i<this.psi.path_short.size();i++)
			{
				
				s+=String.valueOf(this.psi.path_short.get(i));
				s+='-';
			}
			s+=String.valueOf(this.psi.path_short.get(0));
			jta=new JTextArea(s,8,50);
	
			jta.setLineWrap(true);
			jta.setWrapStyleWord(true);
			jta.setEditable(false);
			jp_01.removeAll();
				jp_01.add(jta);
				this.psi.state=1;
			}
		
			jp_01.updateUI();
		
		}
		else if(e.getSource().equals(jb2)){
			JFileChooser jfc=new JFileChooser();
			jfc.setDialogTitle("请选择文件(当前仅支持TXT格式)");
			jfc.showOpenDialog(null);
			jfc.setVisible(true);
			points=new Vector<Point>();
			String st=null;
			try {
				if(jfc.getSelectedFile().getAbsolutePath()!=null)
				st = jfc.getSelectedFile().getAbsolutePath();
			} catch (Exception e2) {
				// TODO Auto-generated catch block
			
				st=null;
			}//获取文件名及其绝对路径
			if(st!=null)
			{
				int ii=st.length();
				String name="" +st.charAt(ii-1)+st.charAt(ii-2)+st.charAt(ii-3);
				double max=0;//记录所有点横纵坐标最大绝对值
				if(name.equals("txt"))
				{
					flag=true;
				}
				else
				{
					JOptionPane.showMessageDialog(null, "文件格式错误！！！","警告",JOptionPane.ERROR_MESSAGE);
				}
				FileReader fr=null;
				BufferedReader br=null;
		         try {
					fr=new FileReader(st);
					br=new BufferedReader(fr);
					String s="";
					String string="";
					int n=0;
					try {
						while((s=br.readLine())!=null)
						{
							s.trim();
							n++;
							Point p=new Point();
							int i=0;
							int x=0;
							int y=0;
						    string="";
						    while(s.charAt(i)!=' ')
						    {
						        i++;
						    }
						    i++;
					        for(;s.charAt(i)!=' ';i++){
					        	string+=s.charAt(i);
					        }
					        try {
								x=Integer.parseInt(string);
							} catch (NumberFormatException e1) {
								// TODO Auto-generated catch block
						
								JOptionPane.showMessageDialog(null, "文件内容格式错误！！！","警告",JOptionPane.ERROR_MESSAGE);
								break;
								//e1.printStackTrace();
							}
					        while(s.charAt(i)==' ')
					        {
					        	i++;
					        }
					        
					        string="";
					        for(;i<s.length();i++)
					        {
					        	if(s.charAt(i)!=' ')
					        	{
					        		string+=s.charAt(i);
					        	}
					        	else break;
					        }
					        try {
								y=Integer.parseInt(string);
							} catch (NumberFormatException e1) {
								// TODO Auto-generated catch block
					
								JOptionPane.showMessageDialog(null, "文件内容格式错误！！！","警告",JOptionPane.ERROR_MESSAGE);
								break;
								//e1.printStackTrace();
							}
					        p.setdata(x, y, n);
				   
					        if(max<Math.max(x, y))
					        {
					        	max=Math.max(x, y);
					        }
					        points.add(p);
					
						}
					} catch (IOException e1) {
						// TODO Auto-generated catch block


						e1.printStackTrace();
					}
					
				
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}finally{
					try {
						br.close();
						fr.close();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();


					}
				    psi.setscale(500/max);//设置缩放比例
				    psi.state=1;


				}
			}
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
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getActionCommand().equals("six")){
	
	       dFrame=new DirectoryFrame();

		
		
		}
		else if(e.getActionCommand().equals("seven")){
	
			hf=new HelpFrame();
	        
			
		}
		else if(e.getActionCommand().equals("two")){
			
	   
		}
		else if(e.getActionCommand().equals("three")){
		
		}
		else if(e.getActionCommand().equals("four")){
			System.exit(0);
		}
		else if(e.getActionCommand().equals("help")){
	    	   sf=new SettingFrame(psi);
		}
		
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		for(int i=0;i<6;i++)
		{
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				}
		      if(i==5){
		        		if(!p){
		     				this.remove(this.mp);
		     				this.add(this.jp_01);
		     				this.add(this.jp_02,BorderLayout.NORTH);
		     				this.add(this.jtl,BorderLayout.SOUTH);
		     				this.p=true;
		     				jp_01.updateUI();
		     				jp_02.updateUI();
		     				jtl.updateUI();
		     				this.setJMenuBar(jmb);
		        		}
		      }
		}
	}

}





