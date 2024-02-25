package main.java.Game;

import java.awt.*;

import static main.java.Game.ImageLoader.loadImage;

public class Bird {
    private final int x;
    private int y;
    private final int width;
    private final int height;
    private final Image img;
    private int velocity = 0;

    public Bird(int boardWidth) {
        this.x = boardWidth / 8;
        this.y = boardWidth / 2;
        this.width = 34;
        this.height = 24;
        this.img = loadImage("flappybird.png");
    }

    // Actualiza la posición del pájaro basada en la gravedad
    public void update() {
        // Gravedad que afecta al pájaro
        int gravity = 1;
        velocity += gravity; // Aumenta la velocidad basada en la gravedad
        y += velocity; // Actualiza la posición Y basada en la velocidad
    }

    // Método para que el pájaro "salte"
    public void jump() {
        velocity = -9; // Un valor negativo para mover el pájaro hacia arriba en el tablero
    }

    // Getters y setters si son necesarios
    public int getX() { return x; }
    public int getY() { return y; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public Image getImg() {
        return img;
    }
}
