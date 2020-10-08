package frontends.frame;

import backends.util.Icons;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class TutorialFrame extends JDialog implements ActionListener {
    JPanel jp1=null;
    JPanel jp2=null;
    JPanel jPanel=null;
    JTabbedPane jtb=null;
    JTextArea jta1=null;
    JTextArea jta2=null;
    JButton jb3=null;
    public TutorialFrame(){
        jp1=new JPanel();
        jp2=new JPanel();
        jPanel=new JPanel();
        jtb=new JTabbedPane();
        jta1=new JTextArea();
        jta2=new JTextArea();
        String instruction="第一步:读入数据(详见右标签页)或者选择算法\n第二步:点击运行\n第三步:查看路径";
        String dataFormat="所读入文件必须为文本文件即TXT格式\n文件内容只能出现数字和空格\n每行内容为:点序号，横坐标，纵坐标，数值" +
                "之间留一个空格如下:\n1 34 62\n2 98 14\n3 5 25\n......\nTips:所有数值必须为正整数,横纵坐标不建议超过1000";
        jb3=new JButton("已阅读");
        jta1.setText(instruction);
        jta2.setText(dataFormat);
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
        this.setIconImage(Icons.TutorialIcon.getImage());
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
