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

    public Bird() {
        this.x = 360 / 8;
        this.y = 360 / 2;
        this.width = 34;
        this.height = 24;
        this.img = loadImage("flappybird.png");
    }

    // Actualiza la posición del pájaro basada en la gravedad
    public void update() {
        int gravity = 1;
        velocity += gravity;
        y += velocity;
    }

    // Método para que el pájaro "salte"
    public void jump() {
        velocity = -9; // Un valor negativo para mover el pájaro hacia arriba en el tablero
    }

    // Getters
    public int getX() { return x; }
    public int getY() { return y; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public Image getImg() {
        return img;
    }
}
