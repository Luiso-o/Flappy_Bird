package main.java.Game;

import javax.swing.*;
import java.awt.*;

public class ImageLoader {
    public static Image loadImage(String path) {
        try {
            ImageIcon imageIcon = new ImageIcon("src/main/resources/" + path);
            Image image = imageIcon.getImage();
            if(image != null && image.getWidth(null) > 0) {
                return image;
            } else {
                throw new IllegalArgumentException("La imagen no se pudo cargar correctamente.");
            }
        } catch (Exception e) {
            System.err.println("Error al cargar la imagen: " + e.getMessage());
            return null;
        }
    }

}
