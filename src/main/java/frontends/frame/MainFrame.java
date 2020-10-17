package frontends.frame;

import backends.model.Algorithm;
import backends.model.City;
import backends.util.Config;
import backends.util.Icons;
import frontends.panel.StartUpPanel;
import lombok.extern.java.Log;

import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Arrays;


import javax.swing.*;


//主要交互窗口
@Log
public class MainFrame extends JFrame implements MouseListener, ActionListener {

    /**
     * @param args
     * 程序设计:郭大为
     * 开始日期:16年09月06日
     * 程序功能:解决TSP问题
     * 完成日期:16年
     */
    //自定义组件
    StartUpPanel startPanel = null;
    TutorialFrame dFrame = null;
    HelpFrame hf = null;
    SettingFrame sf = null;
    WorkFrame fv = null;
    //swing组件
    JToolBar jtl = null;
    JPanel jp_01 = null;
    JPanel jp_02 = null;
    JLabel jl = null;
    JLabel jl2 = null;
    JLabel jl3 = null;
    JScrollPane jsp = null;
    JComboBox jcb = null;
    JButton runTask = null;
    JButton loadFile = null;
    JButton showResult = null;
    JMenuBar jmb = null;
    JMenu jm1 = null;
    JMenu jm2 = null;
    JMenu jm3 = null;
    JTextField jtf = null;
    JTextArea jta = null;
    JMenuItem jmi2 = null;
    JMenuItem jmi3 = null;
    JMenuItem jmi1 = null;
    JMenuItem jmi4 = null;
    JMenuItem jmi5 = null;
    JMenuItem jmi6 = null;
    JMenuItem jmi7 = null;


    boolean p = false;//防止JFrame窗体重复添加JPanel组件
    boolean flag = false;//是否按要求读入文件
    int file_error = 0;//文件读入错误类型

    public MainFrame() throws InterruptedException {
        //初始化控件
        jp_01 = new JPanel();
        jp_02 = new JPanel();

        jsp = new JScrollPane();
        jmb = new JMenuBar();

        jm2 = new JMenu("帮助设置");
        jm3 = new JMenu("退出程序");
        jl = new JLabel("选择算法");
        jl2 = new JLabel("状态栏");
        jtl = new JToolBar();

        jsp.setBackground(Color.white);
        runTask = new JButton("运行");
        loadFile = new JButton("读入数据");
        showResult = new JButton("查看路径");
        jcb = new JComboBox(Arrays.stream(Algorithm.values()).map(Algorithm::getName).toArray());


        jmi4 = new JMenuItem("立即退出");
        jmi5 = new JMenuItem("参数设置");
        jmi6 = new JMenuItem("帮助文档");
        jmi7 = new JMenuItem("关于开发");
        //注册监听

        runTask.addMouseListener(this);
        loadFile.addMouseListener(this);
        showResult.addMouseListener(this);
        jmi4.addActionListener(this);
        jmi4.setActionCommand("four");
        jmi5.addActionListener(this);
        jmi5.setActionCommand("help");
        jmi6.addActionListener(this);
        jmi6.setActionCommand("six");
        jmi7.addActionListener(this);
        jmi7.setActionCommand("seven");
        //组合控件

        //jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        jp_01.setBackground(Color.white);
        jtl.add(jl2);
        jp_02.add(loadFile);
        jp_02.add(jl);
        jp_02.add(jcb);
        jp_02.add(runTask);
        jp_02.add(showResult);
        jp_02.setBackground(Color.lightGray);


        jmb.add(jm2);
        jmb.add(jm3);

        jm2.add(jmi5);
        jm2.add(jmi6);
        jm2.add(jmi7);
        jm3.add(jmi4);

        //窗体属性设置
        this.setSize(600, 480);
        //this.setUndecorated(true);
        this.setLocationRelativeTo(null);
        this.setTitle("tsp问题");
        this.setResizable(false);
        startPanel = new StartUpPanel();
        Thread thread = new Thread(startPanel);
        thread.start();
        this.add(startPanel);
        this.setIconImage(Icons.ProgramIcon.getImage());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

        thread.join();
        flushPanel();
    }

    public void ErrorMesseger(String string) {
        JOptionPane.showMessageDialog(null, string, "警告", JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub
        if (e.getSource().equals(runTask)) {
            if (flag) {
                Config.flag = true;
                Config.shortestPath.clear();
                Config.abstractSolver = Algorithm.getSolverByName((String) this.jcb.getSelectedItem());
                fv = new WorkFrame();
            } else {
                if (this.file_error == 0) {
                    this.ErrorMesseger("        未读入文件!!!");
                } else {
                    this.ErrorMesseger("          读入错误!!!");
                }
            }

        } else if (e.getSource().equals(showResult)) {
            if (Config.state > 1) {
                DecimalFormat df = new DecimalFormat("######0.00");
                StringBuilder s = new StringBuilder("共有" + Config.shortestPath.size() + "个点\n最短距离为" + df.format(Config.shortestDistance) + "\n" + "最短路径如下\n");
                for (int i = 0; i < Config.shortestPath.size(); i++) {

                    s.append(Config.shortestPath.get(i));
                    s.append('-');
                }
                s.append(Config.shortestPath.get(0));
                jta = new JTextArea(s.toString(), 8, 50);

                jta.setLineWrap(true);
                jta.setWrapStyleWord(true);
                jta.setEditable(false);
                jp_01.removeAll();
                jp_01.add(jta);
                Config.state = 1;
            }

            jp_01.updateUI();

        } else if (e.getSource().equals(loadFile)) {
            JFileChooser jfc = new JFileChooser();
            jfc.setDialogTitle("请选择文件(当前仅支持TXT格式)");
            jfc.showOpenDialog(null);
            jfc.setVisible(true);
            Config.cities.clear();
            String st = null;
            try {
                if (jfc.getSelectedFile().getAbsolutePath() != null)
                    st = jfc.getSelectedFile().getAbsolutePath();
            } catch (Exception e2) {
                // TODO Auto-generated catch block

            }//获取文件名及其绝对路径
            if (st != null) {
                int ii = st.length();
                String name = "" + st.charAt(ii - 1) + st.charAt(ii - 2) + st.charAt(ii - 3);
                this.file_error = 1;
                if (name.equals("txt")) {
                    flag = true;
                } else {
                    this.ErrorMesseger("         文件格式错误!!!");
                }

                try (
                        FileReader fr = new FileReader(st);
                        BufferedReader br = new BufferedReader(fr);
                ) {

                    String s = "";
                    StringBuilder string = new StringBuilder();
                    int n = 0;
                    try {
                        while ((s = br.readLine()) != null) {
                            s = s.trim();
                            n++;
                            int i = 0;
                            int x = 0;
                            int y = 0;
                            string = new StringBuilder();
                            while (s.charAt(i) != ' ') {
                                i++;
                            }
                            i++;
                            for (; s.charAt(i) != ' '; i++) {
                                string.append(s.charAt(i));
                            }
                            try {
                                x = Integer.parseInt(string.toString());
                            } catch (NumberFormatException e1) {
                                // TODO Auto-generated catch block

                                this.ErrorMesseger("         文件格式错误!!!");
                                break;
                                //e1.printStackTrace();
                            }
                            while (s.charAt(i) == ' ') {
                                i++;
                            }

                            string = new StringBuilder();
                            for (; i < s.length(); i++) {
                                if (s.charAt(i) != ' ') {
                                    string.append(s.charAt(i));
                                } else break;
                            }
                            try {
                                y = Integer.parseInt(string.toString());
                            } catch (NumberFormatException e1) {
                                // TODO Auto-generated catch block
                                this.ErrorMesseger("         文件内容错误!!!");
                                break;
                                //e1.printStackTrace();
                            }
                            Config.cities.add(new City(x, y, n));
                        }
                    } catch (IOException ioException) {
                        log.info("load file failed");
                    }

                    Config.state = 1;


                } catch (IOException fileNotFoundException) {
                    fileNotFoundException.printStackTrace();
                }
            }
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

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        if (e.getActionCommand().equals("six")) {

            dFrame = new TutorialFrame();


        } else if (e.getActionCommand().equals("seven")) {

            hf = new HelpFrame();


        } else if (e.getActionCommand().equals("two")) {


        } else if (e.getActionCommand().equals("three")) {

        } else if (e.getActionCommand().equals("four")) {
            System.exit(0);
        } else if (e.getActionCommand().equals("help")) {
            sf = new SettingFrame();
        }

    }

    public void flushPanel() {
        if (!p) {
            this.remove(this.startPanel);
            this.add(this.jp_01);
            this.add(this.jp_02, BorderLayout.NORTH);
            this.add(this.jtl, BorderLayout.SOUTH);
            this.p = true;
            jp_01.updateUI();
            jp_02.updateUI();
            jtl.updateUI();
            this.setJMenuBar(jmb);
        }
    }
}





