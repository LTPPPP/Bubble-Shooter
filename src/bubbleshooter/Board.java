package bubbleshooter;

import static bubbleshooter.BubbleShooter.HEIGHT_BOARD;
import static bubbleshooter.BubbleShooter.WIDTH_BOARD;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

public class Board extends JPanel implements MouseListener, Runnable {

    private Bubble bubbleShoot;
    private final int bubbleDiameter = 30;
    private final int bubbleSpacing = 2;
    private Shooting shooting;
    private List<Bubble> bubbles;
    public static int ROW_ADD_INTERVAL = 0;

    public Board() {
        bubbleShoot = new Bubble((WIDTH_BOARD - bubbleDiameter) / 2, HEIGHT_BOARD - bubbleDiameter - 100, null);
        bubbleShoot.setRandomColor();
        bubbles = new ArrayList<>();
        createBubbleRows();

        shooting = new Shooting(bubbleShoot);

        setFocusable(true);
        addMouseListener(this);

        Thread gameThread = new Thread(this);
        gameThread.start();
    }

    private void createBubbleRows() {
        int rows = 5;
        for (int row = 0; row < rows; row++) {
            int offsetX = (row % 2 == 0) ? 0 : (bubbleDiameter + bubbleSpacing) / 2;
            for (int col = 0; col < WIDTH_BOARD / (bubbleDiameter + bubbleSpacing) - 1; col++) {
                int x = col * (bubbleDiameter + bubbleSpacing) + offsetX;
                int y = row * (bubbleDiameter + bubbleSpacing);
                Bubble bubble = new Bubble(x + 15, y, null);
                bubble.setRandomColor();
                bubbles.add(bubble);
            }
        }
    }

    private List<Bubble> findCluster(Bubble target) {
        List<Bubble> cluster = new ArrayList<>();
        Queue<Bubble> queue = new LinkedList<>();
        Set<Bubble> visited = new HashSet<>();
        queue.add(target);
        visited.add(target);

        while (!queue.isEmpty()) {
            Bubble current = queue.poll();
            cluster.add(current);

            for (Bubble neighbor : getNeighbors(current)) {
                if (!visited.contains(neighbor) && neighbor.getColorBubbles().equals(target.getColorBubbles())) {
                    queue.add(neighbor);
                    visited.add(neighbor);
                }
            }
        }
        return cluster;
    }

    private List<Bubble> getNeighbors(Bubble bubble) {
        List<Bubble> neighbors = new ArrayList<>();
        int radius = bubbleDiameter + bubbleSpacing;

        for (Bubble other : bubbles) {
            if (bubble == other) {
                continue;
            }

            int dx = bubble.getxBub() - other.getxBub();
            int dy = bubble.getyBub() - other.getyBub();
            if (Math.sqrt(dx * dx + dy * dy) <= radius) {
                neighbors.add(other);
            }
        }
        return neighbors;
    }

    private void removeCluster(List<Bubble> cluster) {
        bubbles.removeAll(cluster);
    }

    private void checkFloatingBubbles() {
        Set<Bubble> connected = new HashSet<>();
        Queue<Bubble> queue = new LinkedList<>();

        for (Bubble bubble : bubbles) {
            if (bubble.getyBub() == 0) {
                queue.add(bubble);
                connected.add(bubble);
            }
        }

        while (!queue.isEmpty()) {
            Bubble current = queue.poll();

            for (Bubble neighbor : getNeighbors(current)) {
                if (!connected.contains(neighbor)) {
                    connected.add(neighbor);
                    queue.add(neighbor);
                }
            }
        }

        bubbles.removeIf(bubble -> !connected.contains(bubble));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

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
        while (true) {
            if (shooting.isShooting()) {
                boolean createNewBubble = shooting.updatePosition(WIDTH_BOARD, HEIGHT_BOARD, bubbleDiameter + 2, bubbles);
                if (createNewBubble) {
                    bubbles.add(new Bubble(bubbleShoot.getxBub(), bubbleShoot.getyBub(), bubbleShoot.getColorBubbles()));

                    List<Bubble> cluster = findCluster(bubbleShoot);
                    if (cluster.size() >= 3) {
                        removeCluster(cluster);
                        checkFloatingBubbles();
                    }

                    bubbleShoot = new Bubble((WIDTH_BOARD - bubbleDiameter) / 2, HEIGHT_BOARD - bubbleDiameter - 100, null);
                    bubbleShoot.setRandomColor();
                    shooting = new Shooting(bubbleShoot);

                    ROW_ADD_INTERVAL++;
                }
            }

            if (ROW_ADD_INTERVAL == 10) {
                ROW_ADD_INTERVAL = 0;
                shooting.stopShooting();
//                addNewRow();
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
