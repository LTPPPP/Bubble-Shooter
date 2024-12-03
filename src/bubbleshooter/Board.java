package bubbleshooter;

import static bubbleshooter.BubbleShooter.HEIGHT_BOARD;
import static bubbleshooter.BubbleShooter.WIDTH_BOARD;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

public class Board extends JPanel implements MouseListener, Runnable {

    private Bubble bubble;
    private Color bubbleColor;
    private final int bubbleDiameter = 30;
    private Shooting shooting;

    public Board() {
        bubble = new Bubble((WIDTH_BOARD - bubbleDiameter) / 2, HEIGHT_BOARD - bubbleDiameter - 100);
        bubbleColor = getRandomRainbowColor();

        shooting = new Shooting(bubble);

        // Gán MouseListener
        setFocusable(true);
        addMouseListener(this);

        // Khởi chạy luồng cập nhật
        Thread gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Vẽ bóng
        g.setColor(bubbleColor);
        g.fillOval(bubble.getX(), bubble.getY(), bubbleDiameter, bubbleDiameter);
    }

    private Color getRandomRainbowColor() {
        Color[] bubbleColors = {
            new Color(222, 214, 193), Color.BLUE, Color.ORANGE, Color.RED
        };
        Random random = new Random();
        return bubbleColors[random.nextInt(bubbleColors.length)];
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (!shooting.isShooting()) {
            System.out.println("x Mouse : " + e.getX()+ "/y Mouse : "+ e.getY());
            shooting.startShooting(e.getX(), e.getY());
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (!shooting.isShooting()) {
            System.out.println("x Mouse : " + e.getX()+ "/y Mouse : "+ e.getY());
            shooting.startShooting(e.getX(), e.getY());
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (!shooting.isShooting()) {
            System.out.println("x Mouse : " + e.getX()+ "/y Mouse : "+ e.getY());
            shooting.startShooting(e.getX(), e.getY());
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void run() {
        while (true) {
            if (shooting.isShooting()) {
                boolean createNewBubble = shooting.updatePosition(WIDTH_BOARD, HEIGHT_BOARD, bubbleDiameter);
                if (createNewBubble) {
                    bubble = new Bubble((WIDTH_BOARD - bubbleDiameter) / 2, HEIGHT_BOARD - bubbleDiameter - 100);
                    bubbleColor = getRandomRainbowColor();
                    shooting = new Shooting(bubble);
                }
                repaint();
            }
            try {
                Thread.sleep(16);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
}
