package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class HelpFrame extends JDialog implements MouseListener {
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