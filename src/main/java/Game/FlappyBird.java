package main.java.Game;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

public class FlappyBird extends JPanel implements ActionListener, KeyListener {
    private static final int BOARD_WIDTH = 360;
    private static final int BOARD_HEIGHT = 640;
    private static final int VELOCITY_X = -4; //movimiento de las tuberias
    private static final int TUBERIA_HEIGHT = 512;
    private static final int OPENING_SPACE = BOARD_HEIGHT / 4;
    Bird bird = new Bird();
    Score score = new Score();
    Draw drawer = new Draw(BOARD_WIDTH,BOARD_HEIGHT);
    ArrayList<Tuberia> tuberias = new ArrayList<>();
    Timer gameLoop = new Timer(1000/60, this);
    Timer placeTuberiaTimer = new Timer(1500, e -> placePipes());
    boolean gameOver = false;
    boolean isPaused = false;


    public FlappyBird() {
        setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));
        setFocusable(true);
        addKeyListener(this);

        placeTuberiaTimer.start();
        gameLoop.start();
    }

    void placePipes() {
        int randomPipeY = generateRandomPipeY();

        // Añade una tubería superior a la lista
        Tuberia tuberiaSuperior = new Tuberia(BOARD_WIDTH, "tuboSuperior.png");
        tuberiaSuperior.setY(randomPipeY);
        tuberias.add(tuberiaSuperior);

        // Añade una tubería inferior a la lista
        Tuberia tuberiaInferior = new Tuberia(BOARD_WIDTH, "tuboInferior.png");
        tuberiaInferior.setY(randomPipeY + TUBERIA_HEIGHT + OPENING_SPACE);
        tuberias.add(tuberiaInferior);
    }

    private int generateRandomPipeY() {
        return (int) (-TUBERIA_HEIGHT / 4 - Math.random() * (TUBERIA_HEIGHT / 2));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Dibuja el juego
        drawer.draw(g, bird, tuberias, gameOver, score);

        // Si el juego está pausado, dibuja el texto de pausa
        if (isPaused && !gameOver) {
            drawer.drawPauseScreen(g);
        }
    }

    public void move() {
        bird.update();

        //tuberias
        for (Tuberia pipe : tuberias) {
            pipe.moveLeft(-VELOCITY_X);

            if (!pipe.isPassed() && bird.getX() > pipe.getX()+ pipe.getWidth()) {
                score.addScore(0.5);
                pipe.setPassed(true);
            }

            if (collision(bird, pipe)) {
                gameOver = true;
            }
        }

        // Verificar si el pájaro toca el suelo
        if (bird.getY() + bird.getHeight() >= BOARD_HEIGHT) {
            gameOver = true;
        }
    }

    // Método para comprobar si hay una colisión entre el pájaro y una tubería
    boolean collision(Bird a, Tuberia b) {
        Rectangle birdRect = new Rectangle(a.getX(), a.getY(), a.getWidth(), a.getHeight());
        Rectangle pipeRect = new Rectangle(b.getX(), b.getY(), b.getWidth(), b.getHeight());

        return birdRect.intersects(pipeRect);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
        if (gameOver) {
            placeTuberiaTimer.stop();
            gameLoop.stop();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            if (!isPaused && !gameOver) {
                bird.jump();
            }
        } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (!gameOver) {
                togglePause();
            }
        }

        if (gameOver && e.getKeyCode() == KeyEvent.VK_SPACE) {
            restartGame();
        }
    }

    private void restartGame() {
        bird = new Bird();
        tuberias.clear();
        score.resetScore();
        gameOver = false;
        isPaused = false;
        gameLoop.start();
        placeTuberiaTimer.start();
    }

    private void togglePause() {
        // Si el juego está pausado, reanudarlo; si no, pausarlo.
        if (isPaused) {
            gameLoop.start();
            placeTuberiaTimer.start();
        } else {
            gameLoop.stop();
            placeTuberiaTimer.stop();
        }
        // Cambiar el estado de pausa al opuesto de lo que estaba.
        isPaused = !isPaused;
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

