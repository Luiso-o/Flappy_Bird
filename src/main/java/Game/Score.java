package main.java.Game;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Score {
    private static final Logger log = Logger.getLogger(Score.class.getName());
    private double currentScore = 0;
    private int highScore = 0;
    private static final String HIGH_SCORE_FILE = "highscore.txt";

    public Score() {
        highScore = loadHighScore();
    }

    public void addScore(double value) {
        currentScore += value;
        int tempScore = (int) currentScore;
        if (tempScore > highScore) {
            highScore = tempScore;
            saveHighScore(tempScore);
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
        try {
            String content = new String(Files.readAllBytes(Paths.get(HIGH_SCORE_FILE)));
            return content.isEmpty() ? 0 : Integer.parseInt(content);
        } catch (IOException | NumberFormatException e) {
            log.log(Level.WARNING, "Error loading high score", e);
            return 0;
        }
    }

    private void saveHighScore(int newHighScore) {
        try {
            Files.write(Paths.get(HIGH_SCORE_FILE), String.valueOf(newHighScore).getBytes());
            log.info("Nuevo puntaje más alto! " + newHighScore);
        } catch (IOException e) {
            log.log(Level.SEVERE, "Error saving high score", e);
        }
    }
}
