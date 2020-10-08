package main;

import backends.model.Solution;
import backends.util.SolutionUtil;
import lombok.extern.log4j.Log4j2;

import java.io.*;
import java.util.ArrayList;

@Log4j2
public class Main {

    public static void main(String[] args) throws InterruptedException, IOException, ClassNotFoundException {
        SolutionUtil.loadFromFile();
    }


}
