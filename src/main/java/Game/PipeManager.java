package main.java.Game;

import java.util.ArrayList;

public class PipeManager {
    private static final String PIPE_LOWER_IMAGE = "tuboInferior.png";
    private final ArrayList<Pipe> tuberias = new ArrayList<>();
    private final int BOARD_WIDTH;
    private final int OPENING_SPACE;
    private final int TUBERIA_HEIGHT;
    private int VELOCITY_X;

    public PipeManager(int boardWidth, int boardHeight) {
        this.BOARD_WIDTH = boardWidth;
        this.TUBERIA_HEIGHT = 512;
        this.OPENING_SPACE = boardHeight/4;
        this.VELOCITY_X = 4;
    }

    public void placePipes() {
        int randomPipeY = generateRandomPipeY();

        // Añade una tubería superior a la lista
        String PIPE_UPPER_IMAGE = "tuboSuperior.png";
        Pipe tuberiaSuperior = new Pipe(BOARD_WIDTH, PIPE_UPPER_IMAGE);
        tuberiaSuperior.setY(randomPipeY);
        tuberias.add(tuberiaSuperior);

        // Añade una tubería inferior a la lista
        Pipe tuberiaInferior = new Pipe(BOARD_WIDTH, PIPE_LOWER_IMAGE);
        tuberiaInferior.setY(randomPipeY + TUBERIA_HEIGHT + OPENING_SPACE);
        tuberias.add(tuberiaInferior);
    }

    private int generateRandomPipeY() {
        int minHeight = -TUBERIA_HEIGHT / 4;
        int maxHeight = -3 * TUBERIA_HEIGHT / 4;
        return minHeight + (int) (Math.random() * (maxHeight - minHeight));
    }

    public GameState movePipes(Bird bird, Score score, GameState currentState, int boardHeight) {
        bird.update();

        // Mueve las tuberías y verifica el paso sin colisión
        for (Pipe pipe : tuberias) {
            pipe.moveLeft(VELOCITY_X);

            if (!pipe.isPassed() && bird.getX() > pipe.getX() + pipe.getWidth()) {
                score.addScore(0.5);
                pipe.setPassed(true);
            }
        }

        // Verifica la colisión y el suelo después de actualizar todas las tuberías
        if (checkCollision(bird) || bird.getY() + bird.getHeight() >= boardHeight) {
            return GameState.GAME_OVER;
        }

        return currentState;
    }


    public boolean checkCollision(Bird bird) {
        for (Pipe tuberia : tuberias) {
            if (bird.getBounds().intersects(tuberia.getBounds())) {
                return true;
            }
        }
        return false;
    }

    public void resetPipes() {
        tuberias.clear();
    }

    public ArrayList<Pipe> getTuberias() {
        return tuberias;
    }

    public void adjustDifficultyBasedOnScore(int score) {
        if (score % 10 == 0) {
            VELOCITY_X += 1;
        }
    }

}

