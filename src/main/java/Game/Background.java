package main.java.Game;

import java.awt.Image;
public class Background {
    private final Image image;

    public Background(String imagePath) {
        this.image = ImageLoader.loadImage(imagePath);
    }

    public Image getImage() {
        return image;
    }
}
