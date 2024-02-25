package main.java;

import main.java.Game.ImageLoader;
import main.java.Game.StartScreen;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        int boardWidth = 360;
        int boardHeight = 640;

        JFrame frame = new JFrame("Flappy Bird");
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setIconImage(ImageLoader.loadImage("icon.png"));

        StartScreen screen = new StartScreen(frame);
        frame.add(screen);
        frame.pack();
        screen.requestFocus();
        frame.setVisible(true);
    }
}