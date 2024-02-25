package main.java.Game;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

public class FlappyBird extends JPanel implements ActionListener, KeyListener {
    final int BOARD_WIDTH = 360;
    final int BOARD_HEIGHT = 640;

    //game logic
    Bird bird;
    Score score;
    int velocityX = -4; //movimiento de las tuberias
    int velocityY = 0; //velocidad del salto del ave(movimiento de arriba a abajo)

    //load background
    Background background = new Background("background.png");

    ArrayList<Tuberia> tuberias;

    Timer gameLoop;
    Timer placeTuberiaTimer;
    boolean gameOver = false;
    //double score = 0;

    public FlappyBird() {
        setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));
        // setBackground(Color.blue);
        setFocusable(true);
        addKeyListener(this);

        //Score
        score = new Score();

        //bird
        bird = new Bird(BOARD_WIDTH);
        tuberias = new ArrayList<>();

        //place pipes timer
        placeTuberiaTimer = new Timer(1500, e -> placePipes());

        placeTuberiaTimer.start();

        //game timer
        gameLoop = new Timer(1000/60, this);
        gameLoop.start();
    }

    void placePipes() {
        int tuberiaHeight = 512;
        int tuberiaY = 0;

        int randomPipeY = (int) (tuberiaY - tuberiaHeight /4 - Math.random() * (tuberiaHeight / 2));
        int openingSpace = BOARD_HEIGHT / 4;

        // Crea y añade una tuberia a la lista
        Tuberia tuberiaSuperior = new Tuberia(BOARD_WIDTH, "tuboSuperior.png");
        tuberiaSuperior.setY(randomPipeY);
        tuberias.add(tuberiaSuperior);

        Tuberia tuberiaInferior = new Tuberia(BOARD_WIDTH, "tuboInferior.png");
        tuberiaInferior.setY(tuberiaSuperior.getY() + tuberiaHeight + openingSpace);
        tuberias.add(tuberiaInferior);
    }


    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        //background
        g.drawImage(background.getImage(), 0, 0, this.BOARD_WIDTH, this.BOARD_HEIGHT, null);
        //bird
        g.drawImage(bird.getImg(), bird.getX(), bird.getY(), bird.getWidth(), bird.getHeight(), null);
        //tuberias
        for (Tuberia pipe : tuberias) {
            g.drawImage(pipe.getImg(), pipe.getX(), pipe.getY(), pipe.getWidth(), pipe.getHeight(), null);
        }

        // Configura la fuente para los textos
        Font font1 = new Font("Arial", Font.BOLD, 25);
        Font font2 = new Font("Arial", Font.BOLD, 20);

        //configura colores
        Color white = new Color(255, 255, 255);
        Color blue = new Color(0, 0, 255);
        Color green = new Color(16, 92, 16);

        //imprimir puntajes
        if (!gameOver) {
            g.setColor(white);

            g.setFont(font1);
            g.drawString("Current Score: " + score.getCurrentScore(), 10, 35);

            g.setFont(font2);
            g.drawString("High Score: " + score.getHighScore(), 10, 60);

        } else {
            //centrar puntaje final
            String finalScoreText = "Final Score: " + score.getCurrentScore();

            g.setFont(font2);
            g.setColor(blue);

            int finalScoreWidth = g.getFontMetrics().stringWidth(finalScoreText);
            int xFinalScore = (BOARD_WIDTH - finalScoreWidth) / 2;

            g.drawString(finalScoreText, xFinalScore, 320);

            //centrar puntaje más alto
            String highScoreText = "High Score: " + score.getHighScore();

            g.setFont(font1);
            g.setColor(green);

            int highScoreWidth = g.getFontMetrics().stringWidth(highScoreText);
            int xHighScore = (BOARD_WIDTH - highScoreWidth) / 2;

            g.drawString(highScoreText, xHighScore, 345);
        }
    }

    public void move() {
        bird.update();

        //tuberias
        for (Tuberia pipe : tuberias) {
            pipe.moveLeft(-velocityX);

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
        return a.getX() < b.getX() + b.getWidth() &&
                a.getX() + a.getWidth() > b.getX() &&
                a.getY() < b.getY() + b.getHeight() &&
                a.getY() + a.getHeight() > b.getY();
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
            bird.jump();

            if (gameOver) {
                bird = new Bird(BOARD_WIDTH);
                velocityY = 0;
                tuberias.clear();
                gameOver = false;
                score.resetScore();
                gameLoop.start();
                placeTuberiaTimer.start();
            }
        }
    }

    //not needed
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

