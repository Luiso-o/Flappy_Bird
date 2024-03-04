package main.java.Game;

import java.awt.Image;
public class Background {
    private final Image image;

    public Background() {
        this.image = ImageLoader.loadImage("background.png");
    }

    public Image getImage() {
        return image;
    }
}
