package bubbleshooter;

import static bubbleshooter.BubbleShooter.HEIGHT_BOARD;
import static bubbleshooter.BubbleShooter.WIDTH_BOARD;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

public class Board extends JPanel implements MouseListener, Runnable {

    private Bubble bubbleShoot;
    private final int bubbleDiameter = 30;
    private Shooting shooting;
    private List<Bubble> bubbles;
    private long lastRowAddTime;
    private final int ROW_ADD_INTERVAL = 5000; // Thời gian tạo hàng mới (5 giây)

    public Board() {
        bubbleShoot = new Bubble((WIDTH_BOARD - bubbleDiameter) / 2, HEIGHT_BOARD - bubbleDiameter - 100, null);
        bubbleShoot.setRandomColor();
        bubbles = new ArrayList<>();
        createBubbleRows(); // Tạo các hàng bóng

        shooting = new Shooting(bubbleShoot);

        setFocusable(true);
        addMouseListener(this);

        Thread gameThread = new Thread(this);
        gameThread.start();
    }

    private void createBubbleRows() {
        int rows = 5;
        for (int row = 0; row < rows; row++) {
            int offsetX = (row % 2 == 0) ? 0 : bubbleDiameter / 2; // Dịch ngang hàng lẻ
            for (int col = 0; col < WIDTH_BOARD / bubbleDiameter - 2; col++) {
                int x = col * bubbleDiameter + offsetX;
                int y = row * bubbleDiameter;
                Bubble bubble = new Bubble(x + 15, y, null);
                bubble.setRandomColor();
                bubbles.add(bubble);
            }
        }
    }

    private void addNewRow() {
        int offsetX = (bubbles.size() / (WIDTH_BOARD / bubbleDiameter - 2)) % 2 == 0 ? 0 : bubbleDiameter / 2;

        for (Bubble bubble : bubbles) {
            bubble.setyBub(bubble.getyBub() + bubbleDiameter);
        }

        for (int col = 0; col < WIDTH_BOARD / bubbleDiameter - 2; col++) {
            int x = col * bubbleDiameter + offsetX;
            int y = 0;
            Bubble bubble = new Bubble(x + 15, y, null);
            bubble.setRandomColor();
            bubbles.add(0, bubble); // Thêm vào đầu danh sách
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Vẽ bóng
        g.setColor(bubbleShoot.getColorBubbles());
        g.fillOval(bubbleShoot.getxBub(), bubbleShoot.getyBub(), bubbleDiameter, bubbleDiameter);

        for (Bubble bubble : bubbles) {
            g.setColor(bubble.getColorBubbles());
            g.fillOval(bubble.getxBub(), bubble.getyBub(), bubbleDiameter, bubbleDiameter);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (!shooting.isShooting()) {
            System.out.println("x Mouse : " + e.getX() + "/y Mouse : " + e.getY());
            shooting.startShooting(e.getX(), e.getY());
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (!shooting.isShooting()) {
            System.out.println("x Mouse : " + e.getX() + "/y Mouse : " + e.getY());
            shooting.startShooting(e.getX(), e.getY());
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (!shooting.isShooting()) {
            System.out.println("x Mouse : " + e.getX() + "/y Mouse : " + e.getY());
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
        lastRowAddTime = System.currentTimeMillis();

        while (true) {
            if (shooting.isShooting()) {
                boolean createNewBubble = shooting.updatePosition(WIDTH_BOARD, HEIGHT_BOARD, bubbleDiameter / 2, bubbles);
                if (createNewBubble) {
                    bubbles.add(new Bubble(bubbleShoot.getxBub(), bubbleShoot.getyBub(), bubbleShoot.getColorBubbles()));

                    bubbleShoot = new Bubble((WIDTH_BOARD - bubbleDiameter) / 2, HEIGHT_BOARD - bubbleDiameter - 100, null);
                    bubbleShoot.setRandomColor();
                    shooting = new Shooting(bubbleShoot);
                }
            }

            long currentTime = System.currentTimeMillis();
            if (currentTime - lastRowAddTime >= ROW_ADD_INTERVAL) {
                addNewRow();
                lastRowAddTime = currentTime;
            }

            repaint();
            try {
                Thread.sleep(16);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
}
