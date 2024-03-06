package main.java.Game;

import java.awt.*;

public class Pipe {
    private int x, y;
    private final int width, height;
    private final Image img;
    private boolean passed;

    // Constructor
    public Pipe(int BOARD_WITH, String imgPath) {
        this.x = BOARD_WITH;
        this.y = 0;
        this.width = 64;
        this.height = 512;
        this.img = ImageLoader.loadImage(imgPath);
        this.passed = false;
    }

    // Getters
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Image getImg() {
        return img;
    }

    public boolean isPassed() {
        return passed;
    }

    // Setters
    public void setY(int y) {
        this.y = y;
    }

    public void setPassed(boolean passed) {
        this.passed = passed;
    }

    public void moveLeft(int speed) {
        this.x -= speed;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

}
