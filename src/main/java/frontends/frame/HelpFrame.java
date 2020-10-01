package frontends.frame;

import frontends.Icons;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

class HelpFrame extends JDialog implements MouseListener {
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
        this.setIconImage(Icons.HelpIcon.getImage());
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
