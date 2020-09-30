package frontends.frame;

import backends.City;
import backends.Config;
import com.google.common.collect.Lists;
import frontends.*;
import frontends.panel.StartUpPanel;

import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Objects;


import javax.swing.*;


//��Ҫ��������
public class MainFrame extends JFrame implements MouseListener,ActionListener,Runnable {

	/**
	 * @param args
	 * �������:����Ϊ
	 * ��ʼ����:16��09��06��
	 * ������:���TSP���� 
	 * �������:16��
	 */
	//�Զ������
	StartUpPanel mp=null;
    TutorialFrame dFrame=null;
	HelpFrame hf=null;
	SettingFrame sf=null;
	WorkFrame fv=null;
	List<City> points=null;
	//swing���
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

	
	boolean p=false;//��ֹJFrame�����ظ����JPanel���
	boolean flag=false;//�Ƿ�Ҫ������ļ�
	int file_error=0;//�ļ������������

	public MainFrame(){
		//��ʼ���ؼ�
		jp_01=new JPanel();
		jp_02=new JPanel();

        jsp=new JScrollPane();
		mp=new StartUpPanel();
		jmb=new JMenuBar();
		
		jm2=new JMenu("��������");
		jm3=new JMenu("�˳�����");
		jl=new JLabel("ѡ���㷨");
		jl2=new JLabel("״̬��");
		jtl=new JToolBar();
	
        jsp.setBackground(Color.white);
		jb1=new JButton("����");
		jb2=new JButton("��������");
		jb3=new JButton("�鿴·��");
		String []option={"̰���㷨","�Ŵ��㷨","��Ⱥ�㷨"};
	
		jcb=new JComboBox(option);
       
	
		jmi4=new JMenuItem("�����˳�");
		jmi5=new JMenuItem("��������");
		jmi6=new JMenuItem("�����ĵ�");
		jmi7=new JMenuItem("���ڿ���");
		//ע�����
   
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
		//��Ͽؼ�
	 
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
        
		//������������
		 this.setSize(600,480);
		 //this.setUndecorated(true);
		 this.setLocationRelativeTo(null);
    	 this.setTitle("tsp����");
    	 this.setResizable(false);
    	
    	 Thread thread=new Thread(mp);
 		 thread.start();
    	 this.add(mp);
 		 this.setIconImage(Icons.ProgramIcon.getImage());
    	 this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	 this.setVisible(true);
    
    

	}
    public void ErrorMesseger(String string)
    {
    	JOptionPane.showMessageDialog(null,string,"����",JOptionPane.ERROR_MESSAGE);
    }
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource().equals(jb1)){
			if(flag){
				Config.flag =true;
				Config.shortestPath.clear();
				if(Objects.equals(this.jcb.getSelectedItem(), "�Ŵ��㷨"))
				{
					Config.algoType =0;
				}
				else if(Objects.equals(this.jcb.getSelectedItem(), "̰���㷨")){
					Config.algoType =1;
				}
				else {
					Config.algoType =2;
				}
				fv=new WorkFrame(points);
			}
			else{
				if(this.file_error==0)
				{
					this.ErrorMesseger("        δ�����ļ�!!!");
				}
				else {
					this.ErrorMesseger("          �������!!!");
				}
			}
			
		}
	    else if(e.getSource().equals(jb3)){
	    	if(Config.state >1)
			{
	    	DecimalFormat df=new DecimalFormat("######0.00");
			String s="����"+String.valueOf(Config.shortestPath.size())+"����\n��̾���Ϊ"+String.valueOf(df.format(Config.shortestDistance))+"\n"+"���·������\n";
			for(int i = 0; i< Config.shortestPath.size(); i++)
			{
				
				s+=String.valueOf(Config.shortestPath.get(i));
				s+='-';
			}
			s+=String.valueOf(Config.shortestPath.get(0));
			jta=new JTextArea(s,8,50);
	
			jta.setLineWrap(true);
			jta.setWrapStyleWord(true);
			jta.setEditable(false);
			jp_01.removeAll();
				jp_01.add(jta);
				Config.state =1;
			}
		
			jp_01.updateUI();
		
		}
		else if(e.getSource().equals(jb2)){
			JFileChooser jfc=new JFileChooser();
			jfc.setDialogTitle("��ѡ���ļ�(��ǰ��֧��TXT��ʽ)");
			jfc.showOpenDialog(null);
			jfc.setVisible(true);
			points= Lists.newArrayList();
			String st=null;
			try {
				if(jfc.getSelectedFile().getAbsolutePath()!=null)
				st = jfc.getSelectedFile().getAbsolutePath();
			} catch (Exception e2) {
				// TODO Auto-generated catch block
			
				st=null;
			}//��ȡ�ļ����������·��
			if(st!=null)
			{
				int ii=st.length();
				String name="" +st.charAt(ii-1)+st.charAt(ii-2)+st.charAt(ii-3);
				double max=0;//��¼���е��������������ֵ
				this.file_error=1;
				if(name.equals("txt"))
				{
					flag=true;
				}
				else
				{
					this.ErrorMesseger("         �ļ���ʽ����!!!");
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
							City p=new City();
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
						
								this.ErrorMesseger("         �ļ���ʽ����!!!");
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
								this.ErrorMesseger("         �ļ����ݴ���!!!");
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
				    Config.scale=(500/max);//�������ű���
				    Config.state=1;


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
	
	       dFrame=new TutorialFrame();

		
		
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
	    	   sf=new SettingFrame();
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





