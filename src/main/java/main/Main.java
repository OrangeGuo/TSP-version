package main;

import ui.MainFrame;

public class Main {
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        MainFrame tsp=new MainFrame();
        Thread t=new Thread(tsp);
        t.start();
    }
}
