package ui;

import backends.Config;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SettingFrame extends JDialog implements ActionListener {
    JPanel jp1=null;
    JPanel jp2=null;
    JPanel jp3=null;
    JPanel jp4=null;
    JPanel jp5=null;
    JLabel jl1=null;
    JLabel jl2=null;
    JLabel jl3=null;
    JLabel jl4=null;
    JLabel jl5=null;
    JComboBox jcb=null;
    JComboBox jcb2=null;
    JComboBox jcb3=null;
    JButton apply =null;
    JButton cancle =null;
    JRadioButton jrb1=null;
    JRadioButton jrb2=null;
    JRadioButton jrb3=null;
    ButtonGroup bg=null;
    Config psi=null;
    public SettingFrame(Config psi){
        this.psi=psi;
        jp1=new JPanel();
        jp2=new JPanel();
        jp3=new JPanel();
        jp4=new JPanel();
        jp5=new JPanel();
        apply =new JButton("应用");
        cancle =new JButton("取消");
        jrb1=new JRadioButton("100");
        jrb2=new JRadioButton("300");
        jrb3=new JRadioButton("500");
        bg=new ButtonGroup();
        apply.addActionListener(this);
        apply.setActionCommand("sure");
        cancle.addActionListener(this);
        cancle.setActionCommand("exit");
        jl1=new JLabel("运行窗口刷新时间间隔");
        jl2=new JLabel("初始种群规模");
        jl3=new JLabel("(贪心算法)");
        jl4=new JLabel("种群繁衍代数");
        jl5=new JLabel("最优个体复制率");
        String []content={"0.01秒(推荐)","0.1秒","1秒"};
        String []times={"200","400","600","800","1000"};
        String []rate={"0.1","0.2","0.3","0.4","0.5","0.6","0.7","0.8","0.9"};
        jcb=new JComboBox(content);
        jcb2=new JComboBox(times);
        jcb3=new JComboBox(rate);
        jp1.add(jl1);
        jp1.add(jcb);
        jp1.add(jl3);
        jp2.add(apply);
        jp2.add(cancle);
        jp4.add(jl4);
        jp4.add(jcb2);
        jp5.add(jl5);
        jp5.add(jcb3);
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
        this.add(jp5);
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
                psi.generations =400;
            }
            else if(jcb2.getSelectedItem().equals("600"))
            {
                psi.generations =600;
            }
            else if(jcb2.getSelectedItem().equals("800"))
            {
                psi.generations =800;
            }
            else if(jcb2.getSelectedItem().equals("1000"))
            {
                psi.generations =1000;
            }
            int scale=jcb3.getSelectedIndex();
            psi.copyRate =0.1*(scale+1);
            this.dispose();
        }
        else if(e.getActionCommand().equals("exit")){
            this.dispose();
        }
    }
}

