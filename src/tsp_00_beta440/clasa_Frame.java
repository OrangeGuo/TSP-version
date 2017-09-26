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
		jb1=new JButton("Ӧ��");
		jb2=new JButton("ȡ��");
		jrb1=new JRadioButton("100");
		jrb2=new JRadioButton("300");
		jrb3=new JRadioButton("500");
		bg=new ButtonGroup();
		jb1.addActionListener(this);
		jb1.setActionCommand("sure");
		jb2.addActionListener(this);
		jb2.setActionCommand("exit");
		jl1=new JLabel("���д���ˢ��ʱ����");
		jl2=new JLabel("��ʼ��Ⱥ��ģ");
		jl3=new JLabel("(̰���㷨)");
		jl4=new JLabel("��Ⱥ���ܴ���");
		String []content={"0.01��(�Ƽ�)","0.1��","1��"};
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
		//����ͼ��
		Image a=Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/tu/ͼ��3.jpg"));
		this.setIconImage(a);
		this.setSize(400,400);
		this.setLocationRelativeTo(null);
		this.setTitle("����");
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
			if(jcb.getSelectedItem().equals("1��")){
				psi.settime(100);
			}
			else if(jcb.getSelectedItem().equals("0.1��")){
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
		jta.setText("    ������ȫ�������д�����Լ�����������������ƾ��ɸ��˶�����ɡ�\n������Ҫ���ڽ��TSP����(��Travelling Sellmen Problems)����ǰ�汾\n�г�������ṩ����" +
				"�㷨��̰���㷨���Ŵ��㷨������ʵ���Ͻ��TSP��\n����㷨�кܶ��֡����ǿ������ṩ���������㷨���д����ԣ�ʵ���϶�\n��TSP�������⣬һ����������Ǻ���������Ž�" +
				"ֻ�ܾ����ܽӽ�������\n�⡣��һ���㷨(̰���㷨)����򵥵��㷨ͬʱҲ�����ٲ��õ��㷨����\nΪ����Ч�ʺܵͣ����������ڵ������ٵ��������������Ŵ��㷨������\n���ֵ�" +
				"���������Ż��㷨�����ڵ����ܶ�������������޽ӽ������Ž�\n�Ľ⣬����̰���㷨�����������ġ�\n    ���ڿ���ʱ��̣ܶ������߻����ڲ��Ե�ʱ�䲢���࣬���Ա������\n��" +
				"����ĳЩδ֪bug��������ʹ���߾������հ��ղ���˵���������⵼��\n�����쳣�����������豸�������ĳ����쳣bug�����ͼ��������˵\n�����������Ŷ����䣺1772086465@qq" +
				".com��" );
		jb=new JButton("�˳�");
		jb.setBackground(Color.pink);
		jb.addMouseListener(this);
		this.add(jl,BorderLayout.NORTH);
		jp.add(jta);
		this.add(jp);
		this.add(jb,BorderLayout.SOUTH);
		 Image a=Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/tu/ͼ��2.jpg"));
 		 this.setIconImage(a);
		this.setSize(400,400);
		this.setLocationRelativeTo(null);
		this.setTitle("����&����");
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
	     String s1="��һ��:��������(����ұ�ǩҳ)����ѡ���㷨\n�ڶ���:�������\n������:�鿴·��";
	     String s2="�������ļ�����Ϊ�ı��ļ���TXT��ʽ\n�ļ�����ֻ�ܳ������ֺͿո�\nÿ������Ϊ:����ţ������꣬�����꣬��ֵ" +
	     		"֮����һ���ո�����:\n1 34 62\n2 98 14\n3 5 25\n......\nTips:������ֵ����Ϊ������,�������겻���鳬��1000";
		 jb3=new JButton("���Ķ�");
		 jta1.setText(s1);
		 jta2.setText(s2);
		 jb3.setContentAreaFilled(false);//����͸��
		 jb3.addActionListener(this);
		 //jb3.setBorder(BorderFactory.createLoweredBevelBorder());//����͹��
		 jp1.setBackground(Color.white);
		 jp2.setBackground(Color.white);
		 jp1.add(jta1);
		 jp2.add(jta2);
		 jPanel.add(jb3);
		 jtb.add(jp1,"����˵��");
		 jtb.add(jp2,"��������");
         this.add(jtb);
         this.add(jPanel,BorderLayout.SOUTH);
		Image a=Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/tu/ͼ��4.jpg"));
 		this.setIconImage(a);
		this.setSize(400,400);
		this.setLocationRelativeTo(null);
		this.setTitle("�����ĵ�");
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



