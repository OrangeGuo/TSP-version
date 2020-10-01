package frontends.panel;

import javax.swing.*;
import java.awt.*;

public class StartUpPanel extends JPanel implements Runnable{
    int time=6;


    public void paint(Graphics g)
    {
        super.paint(g);
        g.fillRect(0, 0, 600, 480);
        g.setColor(Color.yellow);
        Font font=new Font("宋体",Font.BOLD,100);
        this.setFont(font);
        this.paints(g);
    }
    public void paints(Graphics g){
        if(time<4){
            g.drawString(String.valueOf(time), 250, 240);
        }
        else
        {
            g.drawString("TSP问题", 120, 240);

        }
    }
    @Override
    public void run() {
        // TODO Auto-generated method stub
        while(time>0)
        {
            try {
                Thread.sleep(1000);
                time--;

            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            this.repaint();

        }
    }


}