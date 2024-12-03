package bubbleshooter;

import static bubbleshooter.BubbleShooter.HEIGHT_BOARD;
import static bubbleshooter.BubbleShooter.WIDTH_BOARD;
import javax.swing.*;
import java.awt.*;
import java.util.Random;

/**
 *
 * @author Gigabyte
 */
public class Board extends JPanel {

    private Bubble bubble;
    private Color bubbleColor;
    private final int bubbleDiameter = 30;
    private int bubbleX;
    private int bubbleY;

    public Board() {
        bubble = new Bubble(100, 100);
        bubbleColor = getRandomRainbowColor();
        bubbleX = (WIDTH_BOARD - bubbleDiameter) / 2;
        bubbleY = HEIGHT_BOARD - bubbleDiameter - 100;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(bubbleColor);
        g.fillOval(bubbleX, bubbleY, bubbleDiameter, bubbleDiameter);
    }

    private Color getRandomRainbowColor() {
        Color[] bubbleColors = {
            new Color(222, 214, 193), Color.BLUE, Color.ORANGE, Color.RED
        };
        Random random = new Random();
        return bubbleColors[random.nextInt(bubbleColors.length)];
    }
}
