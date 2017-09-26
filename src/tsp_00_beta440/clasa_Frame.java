package tsp_00_beta440;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Panel;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

class SettingFrame extends JDialog implements ActionListener{
	JPanel jp1=null;
	JPanel jp2=null;
	JPanel jp3=null;
	JPanel jp4=null;
	JLabel jl1=null;
	JLabel jl2=null;
	JLabel jl3=null;
	JLabel jl4=null;
	JComboBox jcb=null;
	JComboBox jcb2=null;
	JButton jb1=null;
	JButton jb2=null;
	JRadioButton jrb1=null;
	JRadioButton jrb2=null;
	JRadioButton jrb3=null;
	ButtonGroup bg=null;
	PublicSettingIndex psi=null;
	public SettingFrame(PublicSettingIndex psi){
		this.psi=psi;
		jp1=new JPanel();
		jp2=new JPanel();
		jp3=new JPanel();
		jp4=new JPanel();
		jb1=new JButton("应用");
		jb2=new JButton("取消");
		jrb1=new JRadioButton("100");
		jrb2=new JRadioButton("300");
		jrb3=new JRadioButton("500");
		bg=new ButtonGroup();
		jb1.addActionListener(this);
		jb1.setActionCommand("sure");
		jb2.addActionListener(this);
		jb2.setActionCommand("exit");
		jl1=new JLabel("运行窗口刷新时间间隔");
		jl2=new JLabel("初始种群规模");
		jl3=new JLabel("(贪心算法)");
		jl4=new JLabel("种群繁衍代数");
		String []content={"0.01秒(推荐)","0.1秒","1秒"};
		String []times={"200","400","600","800","1000"};
		jcb=new JComboBox(content);
		jcb2=new JComboBox(times);
		jp1.add(jl1);
		jp1.add(jcb);
		jp1.add(jl3);
        jp2.add(jb1);
        jp2.add(jb2);
        jp4.add(jl4);
        jp4.add(jcb2);
        if(this.psi.N==100)
        {
        	jrb1.setSelected(true);
        }
        else if(this.psi.N==300)
        {
        	jrb2.setSelected(true);
        }
        else if(this.psi.N==500)
        {
        	jrb3.setSelected(true);
        }
        bg.add(jrb1);
        bg.add(jrb2);
        bg.add(jrb3);
        jp3.add(jl2);
        jp3.add(jrb1);
        jp3.add(jrb2);
        jp3.add(jrb3);
		//设置图标
		Image a=Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/tu/图标3.jpg"));
		this.setIconImage(a);
		this.setSize(400,400);
		this.setLocationRelativeTo(null);
		this.setTitle("设置");
		this.setLayout(new GridLayout(8,1));
		this.add(jp1);
        this.add(jp3);
        this.add(jp4);
        this.add(jp2);
		this.setResizable(false);
		this.dispose();
		this.setModal(true);
		this.setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getActionCommand().equals("sure"))
		{
			if(jcb.getSelectedItem().equals("1秒")){
				psi.settime(100);
			}
			else if(jcb.getSelectedItem().equals("0.1秒")){
				psi.settime(10);
			}
			else{
				psi.settime(1);
			}
			if(jrb2.isSelected())
			{
				psi.N=300;
			}
			else if(jrb3.isSelected())
			{
				psi.N=500;
			}
			if(jcb2.getSelectedItem().equals("400"))
			{
				psi.circle=400;
			}
			else if(jcb2.getSelectedItem().equals("600"))
			{
				psi.circle=600;
			}
			else if(jcb2.getSelectedItem().equals("800"))
			{
				psi.circle=800;
			}
			else if(jcb2.getSelectedItem().equals("1000"))
			{
				psi.circle=1000;
			}
			this.dispose();
		}
		else if(e.getActionCommand().equals("exit")){
			this.dispose();
		}
	}
}
class HelpFrame extends JDialog implements MouseListener{
	JPanel jp=null;
	public  int flag=0;
	JLabel jl=null;
	JTextArea jta=null;
	JButton jb=null;
	public HelpFrame(){
		flag++;
		jp=new JPanel();
		jta=new JTextArea();
	
		jl=new JLabel("All Rights Are Reserved By DGX.STUDIO");
	    jl.setBackground(Color.gray);
		jl.setHorizontalAlignment(JLabel.CENTER);
		jp.setBackground(Color.white);
		jta.setEditable(false);
		jta.setBackground(Color.white);
		jta.setText("    本程序全部代码编写、测试及后期制作，界面设计均由个人独立完成。\n程序主要用于解决TSP问题(即Travelling Sellmen Problems)，当前版本\n中程序仅仅提供两种" +
				"算法：贪心算法和遗传算法。但其实网上解决TSP问\n题的算法有很多种。但是开发者提供的这两种算法具有代表性：实际上对\n于TSP这类问题，一般情况下我们很难求出最优解" +
				"只能尽可能接近于最优\n解。第一种算法(贪心算法)是最简单的算法同时也是最少采用的算法，因\n为它的效率很低，仅仅适用于点数很少的情况。而后面的遗传算法及网上\n出现的" +
				"其衍生和优化算法可以在点数很多的情况下求出无限接近于最优解\n的解，这是贪心算法所不能做到的。\n    由于开发时间很短，开发者花在在测试的时间并不多，所以本程序可\n能" +
				"存在某些未知bug，所以请使用者尽量按照按照操作说明操作以免导致\n程序异常甚至损坏您的设备。如果真的出现异常bug，请截图并附文字说\n明发到开发团队邮箱：1772086465@qq" +
				".com。" );
		jb=new JButton("退出");
		jb.setBackground(Color.pink);
		jb.addMouseListener(this);
		this.add(jl,BorderLayout.NORTH);
		jp.add(jta);
		this.add(jp);
		this.add(jb,BorderLayout.SOUTH);
		 Image a=Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/tu/图标2.jpg"));
 		 this.setIconImage(a);
		this.setSize(400,400);
		this.setLocationRelativeTo(null);
		this.setTitle("开发&关于");
		this.setResizable(false);
		this.dispose();
		this.setModal(true);
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
class DirectoryFrame extends JDialog implements ActionListener{
	JPanel jp1=null;
	JPanel jp2=null;
	JPanel jPanel=null;
    JTabbedPane jtb=null;
    JTextArea jta1=null;
    JTextArea jta2=null;
	JButton jb3=null;
	 public DirectoryFrame(){
		 jp1=new JPanel();
		 jp2=new JPanel();
		 jPanel=new JPanel();
	     jtb=new JTabbedPane();
	     jta1=new JTextArea();
	     jta2=new JTextArea();
	     String s1="第一步:读入数据(详见右标签页)或者选择算法\n第二步:点击运行\n第三步:查看路径";
	     String s2="所读入文件必须为文本文件即TXT格式\n文件内容只能出现数字和空格\n每行内容为:点序号，横坐标，纵坐标，数值" +
	     		"之间留一个空格如下:\n1 34 62\n2 98 14\n3 5 25\n......\nTips:所有数值必须为正整数,横纵坐标不建议超过1000";
		 jb3=new JButton("已阅读");
		 jta1.setText(s1);
		 jta2.setText(s2);
		 jb3.setContentAreaFilled(false);//设置透明
		 jb3.addActionListener(this);
		 //jb3.setBorder(BorderFactory.createLoweredBevelBorder());//设置凸起
		 jp1.setBackground(Color.white);
		 jp2.setBackground(Color.white);
		 jp1.add(jta1);
		 jp2.add(jta2);
		 jPanel.add(jb3);
		 jtb.add(jp1,"操作说明");
		 jtb.add(jp2,"读入数据");
         this.add(jtb);
         this.add(jPanel,BorderLayout.SOUTH);
		Image a=Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/tu/图标4.jpg"));
 		this.setIconImage(a);
		this.setSize(400,400);
		this.setLocationRelativeTo(null);
		this.setTitle("帮助文档");
		this.setResizable(false);
		this.dispose();
		this.setModal(true);
		this.setVisible(true);
	 }
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource().equals(jb3))
			this.dispose();
	}
}



