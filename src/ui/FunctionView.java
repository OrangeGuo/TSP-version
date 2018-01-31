package ui;

import backends.Point;
import backends.PublicSettingIndex;

import javax.swing.*;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;

//求解时的运行窗口(弹出)
public class FunctionView extends JDialog implements MouseListener {

    JPanel jp2=null;
    DrawPanel dp=null;
    NewPanel np=null;
    antpanel ap=null;
    JButton jb=null;
    JTextField jtf1=null;
    JTextField jtf2=null;

    public FunctionView (Vector<Point> p, PublicSettingIndex psi){

        jp2=new JPanel();
        jb=new JButton("exit");
        jb.addMouseListener(this);

        jp2.setBackground(Color.CYAN);

        jp2.add(jb);

        if(psi.suanfa_flag==1){
            dp=new DrawPanel(p,psi);
            Thread thread=new Thread(dp);
            thread.start();
            this.add(dp);
        }
        else if(psi.suanfa_flag==0){
            np=new NewPanel(p,psi);
            Thread thread=new Thread(np);
            thread.start();
            this.add(np);
        }
        else {
            ap=new antpanel(p,psi);
            this.add(ap);
        }
        this.add(jp2,BorderLayout.SOUTH);
        Image a=Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/tu/p2.jpg"));
        this.setIconImage(a);
        this.setSize(600, 600);
        this.setTitle("Run for answer");
        this.setLocationRelativeTo(null);
        this.setModal(true);
        this.setResizable(false);
        this.dispose();
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

