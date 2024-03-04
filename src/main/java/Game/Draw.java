package main.java.Game;

import java.awt.*;
import java.util.ArrayList;

public class Draw {
    private final int BOARD_WIDTH;
    private final int BOARD_HEIGHT;
    private final Font FONT_1 = new Font("Segoe UI Emoji", Font.BOLD, 25);
    private final Font FONT_2 = new Font("Segoe UI Emoji", Font.BOLD, 20);
    private final Font PAUSE_FONT = new Font("Segoe UI Emoji", Font.BOLD, 48);
    private final Color WHITE = new Color(255, 255, 255);
    private final Color BLUE = new Color(0, 0, 255);
    private final Color GREEN = new Color(16, 92, 16);
    private final Background BACKGROUND = new Background();

    public Draw(int BOARD_WIDTH, int BOARD_HEIGHT) {
        this.BOARD_WIDTH = BOARD_WIDTH;
        this.BOARD_HEIGHT = BOARD_HEIGHT;
    }

    public void draw(Graphics g,Bird bird, ArrayList<Tuberia> tuberias, Boolean gameOver, Score score) {
        drawBackground(g);
        drawBird(g, bird);
        drawPipes(g,tuberias);
        drawScores(g, gameOver, score);
    }

    public void drawBackground(Graphics g) {
        g.drawImage(BACKGROUND.getImage(), 0, 0, BOARD_WIDTH, BOARD_HEIGHT, null);
    }

    public void drawBird(Graphics g, Bird bird) {
        g.drawImage(bird.getImg(), bird.getX(), bird.getY(), bird.getWidth(), bird.getHeight(), null);
    }

    public void drawPipes(Graphics g, ArrayList<Tuberia> tuberias) {
        for (Tuberia pipe : tuberias) {
            g.drawImage(pipe.getImg(), pipe.getX(), pipe.getY(), pipe.getWidth(), pipe.getHeight(), null);
        }
    }

    public void drawScores(Graphics g, boolean gameOver, Score score) {
        if (!gameOver) {
            g.setColor(WHITE);

            g.setFont(FONT_1);
            g.drawString("Current Score: " + score.getCurrentScore(), 10, 35);

            g.setFont(FONT_2);
            g.drawString("Record: \uD83E\uDD47" + score.getHighScore(), 10, 60);

        } else {
            //centrar puntaje final
            String finalScoreText = "Final Score: " + score.getCurrentScore();

            g.setFont(FONT_2);
            g.setColor(BLUE);

            int finalScoreWidth = g.getFontMetrics().stringWidth(finalScoreText);
            int xFinalScore = (BOARD_WIDTH - finalScoreWidth) / 2;

            g.drawString(finalScoreText, xFinalScore, 320);

            //centrar puntaje más alto
            String highScoreText = "Record: " + score.getHighScore();

            g.setFont(FONT_1);
            g.setColor(GREEN);

            int highScoreWidth = g.getFontMetrics().stringWidth(highScoreText);
            int xHighScore = (BOARD_WIDTH - highScoreWidth) / 2;

            g.drawString(highScoreText, xHighScore, 345);
        }
    }

    public void drawPauseScreen(Graphics g) {
        //System.out.println("Dibujando pantalla de pausa");
        String pauseText = "PAUSE";
        g.setFont(PAUSE_FONT);
        g.setColor(GREEN);
        FontMetrics metrics = g.getFontMetrics(PAUSE_FONT);
        int x = (BOARD_WIDTH - metrics.stringWidth(pauseText)) / 2;
        int y = ((BOARD_HEIGHT - metrics.getHeight()) / 2) + metrics.getAscent();
        g.drawString(pauseText, x, y);
    }

}
