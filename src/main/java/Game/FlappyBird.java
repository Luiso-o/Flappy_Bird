package main.java.Game;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class FlappyBird extends JPanel implements ActionListener, KeyListener {
    private static final int BOARD_WIDTH = 360;
    private static final int BOARD_HEIGHT = 640;
    private GameState CURRENT_STATE = GameState.PLAYING;
    private final Timer placePipeTimer;
    private final PipeManager gestorDeTuberia;
    private Bird bird = new Bird();
    private final Score score = new Score();
    private final Draw drawer = new Draw(BOARD_WIDTH,BOARD_HEIGHT);
    private final Timer gameLoop = new Timer(1000/60, this);

    public FlappyBird() {
        setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));
        setFocusable(true);
        addKeyListener(this);

        gestorDeTuberia = new PipeManager(BOARD_WIDTH,BOARD_HEIGHT);
        placePipeTimer = new Timer(1500, e -> gestorDeTuberia.placePipes());

        placePipeTimer.start();
        gameLoop.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Dibuja el juego
        drawer.draw(g, bird, gestorDeTuberia.getTuberias(), CURRENT_STATE == GameState.GAME_OVER, score);

        // Si el juego está pausado, dibuja el texto de pausa
        if (CURRENT_STATE == GameState.PAUSED) {
            drawer.drawPauseScreen(g);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (CURRENT_STATE == GameState.PLAYING){
            CURRENT_STATE = gestorDeTuberia.movePipes(bird, score, CURRENT_STATE,BOARD_HEIGHT);
        }
        repaint();
        if (CURRENT_STATE == GameState.GAME_OVER) {
            placePipeTimer.stop();
            gameLoop.stop();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_SPACE:
                handleJumpOrRestart();
                break;
            case KeyEvent.VK_ENTER:
                togglePause();
                break;
        }
    }

    private void handleJumpOrRestart() {
        if (CURRENT_STATE == GameState.PLAYING) {
            bird.jump();
        } else if (CURRENT_STATE == GameState.GAME_OVER) {
            restartGame();
        }
    }

    private void restartGame() {
        bird = new Bird();
        gestorDeTuberia.resetPipes();
        score.resetScore();
        CURRENT_STATE = GameState.PLAYING;
        gameLoop.start();
        placePipeTimer.start();
    }

    private void togglePause() {
        if (CURRENT_STATE == GameState.PLAYING) {
            CURRENT_STATE = GameState.PAUSED;
            gameLoop.stop();
            placePipeTimer.stop();
        } else if (CURRENT_STATE == GameState.PAUSED) {
            CURRENT_STATE = GameState.PLAYING;
            gameLoop.start();
            placePipeTimer.start();
        }
        repaint();
    }

    //no necesario
    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}

    public static void main(String[] args) {
        int boardWidth = 360;
        int boardHeight = 640;

        JFrame frame = new JFrame("Flappy main");
        // frame.setVisible(true);
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setIconImage(ImageLoader.loadImage("icon.png"));

        FlappyBird flappyBird = new FlappyBird();
        frame.add(flappyBird);
        frame.pack();
        flappyBird.requestFocus();
        frame.setVisible(true);
    }
}

