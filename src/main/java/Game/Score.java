package main.java.Game;

import java.io.*;

public class Score {
    private double currentScore = 0;
    private int highScore = 0;
    private static final String HIGH_SCORE_FILE = "highscore.txt";

    public Score() {
        highScore = loadHighScore();
    }

    public void addScore(double value) {
        currentScore += value;
        if (currentScore > highScore) {
            highScore = (int) currentScore;
            saveHighScore((int) currentScore);
        }
    }

    public int getCurrentScore() {
        return (int) currentScore;
    }

    public int getHighScore() {
        return highScore;
    }

    public void resetScore() {
        currentScore = 0;
    }

    private int loadHighScore() {
        try (BufferedReader reader = new BufferedReader(new FileReader(HIGH_SCORE_FILE))) {
            String line = reader.readLine();
            highScore = line != null ? Integer.parseInt(line) : 0;
            return highScore;
        } catch (IOException | NumberFormatException e) {
            return 0;
        }
    }

    private void saveHighScore(int newHighScore) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(HIGH_SCORE_FILE))) {
            writer.write(String.valueOf(newHighScore));
            System.out.println("Nuevo puntaje más alto! " + newHighScore);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
