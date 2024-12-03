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
    public static int ROW_ADD_INTERVAL = 0; // Thời gian tạo hàng mới (5 giây)

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
            int offsetX = (row % 2 == 0) ? 0 : (bubbleDiameter + bubbleSpacing) / 2; // Dịch ngang hàng lẻ
            for (int col = 0; col < WIDTH_BOARD / (bubbleDiameter + bubbleSpacing) - 1; col++) {
                int x = col * (bubbleDiameter + bubbleSpacing) + offsetX;
                int y = row * (bubbleDiameter + bubbleSpacing);
                Bubble bubble = new Bubble(x + 15, y, null);
                bubble.setRandomColor();
                bubbles.add(bubble);
            }
        }
        System.out.println(bubbles.toString());
    }

    private void addNewRow() {
        int offsetX = (bubbles.size() / (WIDTH_BOARD / (bubbleDiameter + bubbleSpacing) - 2)) % 2 == 0
                ? 0
                : (bubbleDiameter + bubbleSpacing) / 2;

        for (Bubble bubble : bubbles) {
            bubble.setyBub(bubble.getyBub() + bubbleDiameter + bubbleSpacing);
        }

        for (int col = 0; col < WIDTH_BOARD / (bubbleDiameter + bubbleSpacing) - 2; col++) {
            int x = col * (bubbleDiameter + bubbleSpacing) + offsetX;
            int y = 0;
            Bubble bubble = new Bubble(x + 15, y, null);
            bubble.setRandomColor();
            bubbles.add(0, bubble); // Thêm vào đầu danh sách
        }
    }

    private List<Bubble> findCluster(Bubble startBubble, List<Bubble> bubbles, int bubbleDiameter) {
        List<Bubble> cluster = new ArrayList<>();

        cluster.add(startBubble);

        Set<Bubble> visited = new HashSet<>();
        visited.add(startBubble);

        Queue<Bubble> queue = new LinkedList<>();
        queue.add(startBubble);

        while (!queue.isEmpty()) {
            Bubble current = queue.poll();

            for (Bubble neighbor : new ArrayList<>(bubbles)) {
                if (visited.contains(neighbor)
                        || !neighbor.getColorBubbles().equals(current.getColorBubbles())) {
                    continue;
                }

                int dx = Math.abs(current.getxBub() - neighbor.getxBub());
                int dy = Math.abs(current.getyBub() - neighbor.getyBub());

                if (dx <= bubbleDiameter + 2 && dy <= bubbleDiameter + 2
                        && (dx <= bubbleDiameter / 2 || dy <= bubbleDiameter / 2)) {

                    cluster.add(neighbor);
                    queue.add(neighbor);
                    visited.add(neighbor);
                }
            }
        }

        return cluster.size() >= 3 ? cluster : new ArrayList<>();
    }

    private void dfsCluster(Bubble current, List<Bubble> sameColorBubbles,
            List<Bubble> currentCluster, boolean[] visited, int bubbleDiameter) {
        int index = sameColorBubbles.indexOf(current);
        if (index == -1 || visited[index]) {
            return;
        }

        visited[index] = true;
        currentCluster.add(current);

        for (Bubble neighbor : sameColorBubbles) {
            if (!visited[sameColorBubbles.indexOf(neighbor)]) {
                int dx = current.getxBub() - neighbor.getxBub();
                int dy = current.getyBub() - neighbor.getyBub();
                double distance = Math.sqrt(dx * dx + dy * dy);

                if (distance <= bubbleDiameter + 2) {
                    dfsCluster(neighbor, sameColorBubbles, currentCluster, visited, bubbleDiameter);
                }
            }
        }
    }

    private void dfs(Bubble current, List<Bubble> bubbles, List<Bubble> cluster, boolean[] visited, int bubbleDiameter) {
        int index = bubbles.indexOf(current);
        if (index == -1 || visited[index]) {
            return; 
        }
        visited[index] = true;
        cluster.add(current);

        for (Bubble neighbor : getNeighbors(current, bubbles, bubbleDiameter)) {
            if (!visited[bubbles.indexOf(neighbor)] && neighbor.getColorBubbles().equals(current.getColorBubbles())) {
                dfs(neighbor, bubbles, cluster, visited, bubbleDiameter);
            }
        }
    }

    private List<Bubble> getNeighbors(Bubble bubble, List<Bubble> bubbles, int bubbleDiameter) {
        List<Bubble> neighbors = new ArrayList<>();
        for (Bubble other : bubbles) {
            if (other != bubble) {
                int dx = bubble.getxBub() - other.getxBub();
                int dy = bubble.getyBub() - other.getyBub();
                double distance = Math.sqrt(dx * dx + dy * dy);
                if (distance <= bubbleDiameter + 2) { 
                    neighbors.add(other);
                }
            }
        }
        return neighbors;
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
            shooting.startShooting(e.getX(), e.getY());
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
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

                    List<Bubble> cluster = findCluster(bubbleShoot, bubbles, bubbleDiameter);
                    if (cluster.size() >= 3) {
                        bubbles.removeAll(cluster); // Xóa cụm bóng
                    }

                    bubbleShoot = new Bubble((WIDTH_BOARD - bubbleDiameter) / 2, HEIGHT_BOARD - bubbleDiameter - 100, null);
                    bubbleShoot.setRandomColor();
                    shooting = new Shooting(bubbleShoot);
                    System.out.println(bubbles.size());
                    System.out.println(bubbles.toString());
                    ROW_ADD_INTERVAL++;
                }
            }

            if (ROW_ADD_INTERVAL == 10) {
                ROW_ADD_INTERVAL = 0;
                shooting.stopShooting();
                addNewRow();
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
