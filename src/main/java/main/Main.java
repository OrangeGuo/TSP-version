package main;

import lombok.extern.java.Log;
import frontends.frame.MainFrame;
@Log
public class Main {
    public static void main(String[] args) {
        log.info("start");
        MainFrame tsp=new MainFrame();
        Thread t=new Thread(tsp);
        t.start();
    }
}
