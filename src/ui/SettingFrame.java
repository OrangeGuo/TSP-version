package ui;

import backends.PublicSettingIndex;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SettingFrame extends JDialog implements ActionListener {
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

