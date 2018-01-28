package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DirectoryFrame extends JDialog implements ActionListener {
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
