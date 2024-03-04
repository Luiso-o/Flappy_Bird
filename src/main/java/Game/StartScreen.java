package main.java.Game;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class StartScreen extends JPanel {
    private final JFrame frame;
    private Background background;

    public StartScreen(JFrame frame) {
        this.frame = frame;
        initializeUI();
    }

    private void initializeUI() {
        setLayout(new GridBagLayout());

        JLabel welcomeText = createWelcomeText();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(20, 20, 0, 20);
        gbc.weightx = 1.0;
        add(welcomeText, gbc);

        // Botón de inicio
        JButton startButton = new JButton("Start Game");
        startButton.addActionListener(e -> startGame());

        // Define el tamaño preferido del botón
        startButton.setPreferredSize(new Dimension(200, 50));
        startButton.setMinimumSize(new Dimension(200, 50));
        startButton.setMaximumSize(new Dimension(200, 50));

        // Carga y escala la imagen de inicio
        ImageIcon originalIcon = new ImageIcon(Objects.requireNonNull(ImageLoader.loadImage("inicio.png")));
        Image scaledImage = originalIcon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        JLabel imageLabel = new JLabel(scaledIcon);

        // Configuración de GridBagConstraints para la imagen
        gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 0, 0, 0);
        add(imageLabel, gbc);

        // Configuración de GridBagConstraints para el botón de inicio
        gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 0, 20, 0); // Ajusta los márgenes como sea necesario
        add(startButton, gbc);

        // Configura el fondo
        background = new Background();
    }


    private JLabel createWelcomeText() {
        JLabel welcomeText = new JLabel("<html><div style='text-align: center;'>¡Bienvenido a Flappy Bird!<br>Vuela a través de los obstáculos y obtén la mayor puntuación posible.<br>Presiona 'Start Game' para comenzar.</div></html>");
        welcomeText.setHorizontalAlignment(JLabel.CENTER);
        welcomeText.setVerticalAlignment(JLabel.TOP);
        welcomeText.setForeground(Color.WHITE);
        welcomeText.setFont(new Font("Arial", Font.BOLD, 14));
        return welcomeText;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (background != null) {
            g.drawImage(background.getImage(), 0, 0, getWidth(), getHeight(), this);
        }
    }

    private void startGame() {
        FlappyBird flappyBird = new FlappyBird();
        frame.getContentPane().removeAll();
        frame.add(flappyBird);
        frame.validate();
        flappyBird.requestFocusInWindow();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(360, 640);
    }
}
