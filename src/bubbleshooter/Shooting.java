package bubbleshooter;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.function.Consumer;

import javax.swing.Timer;

public class Shooting implements ActionListener {

    private ArrayList<RowList> bubbles;
    private LinkedList<Bubble> upcoming;
    private MovingBubble moving_bubble;
    private int initial_rows;
    private int colors;
    private Timer timer;
    private Board board;
    private int shotCount;
    private int numOfBubbles;
    private MainFrame mainFrame;
    private long score;
    private boolean stopped;

    public static final int ROW_COUNT = (int) Math.ceil(BubbleShooter.HEIGHT_BOARD / (2.0 * Bubble.RADIUS));
    public static final int COL_COUNT_FULL = (int) Math.ceil(BubbleShooter.WIDTH_BOARD / (2.0 * Bubble.RADIUS));
    public static final int COL_COUNT = COL_COUNT_FULL - 1;
    public static final int SCORE_SHOT = 10;
    public static final int SCORE_COHERENT = 20;
    public static final int SCORE_FLOATING = 40;

    public Shooting(int row, int colors, Board c) {
        board = c;
        stopped = false;
        initial_rows = row;
        this.colors = colors;
        shotCount = 0;
        numOfBubbles = 0;
        score = 0;
        bubbles = new ArrayList<>();
        for (int i = 0; i < ROW_COUNT; i++) {
            RowList r = new RowList((i % 2 == 0));
            bubbles.add(r);
            for (int j = 0; j < (r.isFull() ? 14 : 13); j++) {
                Bubble b = new Bubble(Bubble.getRandomColor(colors));
                b.setLocation(new Point(r.isFull() ? j * 2 * (Bubble.RADIUS + 1)
                        : j * 2 * (Bubble.RADIUS + 1) + (Bubble.RADIUS + 1),
                        r.isFull() ? (i / 2) * BubbleShooter.ROW_DISTANCE
                        : (i / 2) * BubbleShooter.ROW_DISTANCE + BubbleShooter.ROW_DISTANCE / 2));
                r.add(b);
                if (i < initial_rows) {
                    b.setVisible(true);
                    numOfBubbles++;
                } else {
                    b.setVisible(false);
                }
            }
        }

        upcoming = new LinkedList<>();
        for (int i = 0; i < 4; i++) {
            Bubble b = new Bubble(Bubble.getRandomColor(colors));
            upcoming.add(b);
        }
        arrangeUpcoming();
    }

    public void setMainFrame(MainFrame m) {
        mainFrame = m;
    }

    public void paintBubbles(Graphics2D g2d) {
        bubbles.forEach((RowList r) -> {
            r.forEach((b) -> {
                b.paintBubble(g2d);
            });
        });
        upcoming.forEach((b) -> {
            b.paintBubble(g2d);
        });
        if (moving_bubble != null) {
            moving_bubble.paintBubble(g2d);
        }
    }

    private void arrangeUpcoming() {
        upcoming.element().setLocation(new Point(BubbleShooter.WIDTH_BOARD / 2 - Bubble.RADIUS,
                BubbleShooter.HEIGHT_BOARD - Bubble.RADIUS));
        upcoming.element().setVisible(true);
        for (int i = 1; i < 4; i++) {
            upcoming.get(i).setLocation(new Point(
                    BubbleShooter.WIDTH_BOARD - (4 - i) * (2 * (Bubble.RADIUS + 6)),
                    BubbleShooter.HEIGHT_BOARD - (Bubble.RADIUS + 1)));
            upcoming.get(i).setVisible(true);
        }
    }

    public void fire(Point mouseLoc, Point panelLoc) {
        boolean movingExists = !(moving_bubble == null);
        movingExists = (movingExists ? moving_bubble.isMoving() : false);
        if (!movingExists) {
            Point dir = new Point(mouseLoc.x - panelLoc.x, mouseLoc.y - panelLoc.y);
            moving_bubble = new MovingBubble(upcoming.remove(), dir);
            upcoming.add(new Bubble(Bubble.getRandomColor(colors)));
            arrangeUpcoming();
            numOfBubbles++;
            score += SCORE_SHOT;
            mainFrame.updateScore(score);
            timer = new Timer(20, this);
            timer.start();
        }
    }

    public void checkProximity() {
        int currentPosX = moving_bubble.getCenterLocation().x;
        int currentPosY = moving_bubble.getCenterLocation().y;
        int row = (currentPosY - Bubble.RADIUS) / (BubbleShooter.ROW_DISTANCE / 2);
        int col;
        if (row < ROW_COUNT) {
            if (bubbles.get(row).isFull()) {
                col = (currentPosX) / ((Bubble.RADIUS + 1) * 2);
            } else {
                col = (currentPosX - (Bubble.RADIUS + 1)) / ((Bubble.RADIUS + 1) * 2);
            }
            if (row == 0) {
                fixBubble(row, col);
            }
            ArrayList<Bubble> neighbours = getNeighbours(row, col);
            for (Bubble b : neighbours) {
                if (b.isVisible() && BubbleDist(moving_bubble, b) <= 4 + (Bubble.RADIUS + 1) * 2) {
                    fixBubble(row, col);
                    break;
                }
            }
        }
    }

    private void fixBubble(int row, int col) {
        Point temp_point = bubbles.get(row).get(col).getLocation();
        moving_bubble.setLocation(temp_point);
        bubbles.get(row).set(col, moving_bubble);
        timer.stop();
        moving_bubble.setMoving(false);
        int removed = removeCoherent(row, col) + removeFloating();
        mainFrame.updateScore(score);
        numOfBubbles -= removed;
        if (removed == 0) {
            shotCount++;
        }
        if (shotCount == 5) {
            shotCount = 0;
            addRow();
        }
        board.repaint();
        if (numOfBubbles == 0) {
            stop();
            score *= 1.2;
            mainFrame.gameWon(score);
        }
        for (Bubble b : bubbles.get(ROW_COUNT - 1)) {
            if (b.isVisible()) {
                stop();
                score *= 0.8;
                mainFrame.gameLost(score);
                break;
            }
        }
    }

    private void addRow() {
        bubbles.remove(ROW_COUNT - 1);
        bubbles.forEach((RowList r) -> {
            r.forEach((b) -> {
                b.setLocation(new Point(b.getLocation().x, b.getLocation().y + BubbleShooter.ROW_DISTANCE / 2));
            });
        });
        RowList newRow = new RowList(!bubbles.get(0).isFull());
        for (int i = 0; i < (newRow.isFull() ? 14 : 13); i++) {
            Bubble b = new Bubble(Bubble.getRandomColor(colors));
            b.setLocation(new Point((newRow.isFull() ? i * 2 * (Bubble.RADIUS + 1)
                    : i * 2 * (Bubble.RADIUS + 1) + (Bubble.RADIUS + 1)), 0));
            b.setVisible(true);
            newRow.add(b);
            numOfBubbles++;
        }
        bubbles.add(0, newRow);
    }

    private ArrayList<Bubble> getNeighbours(int row, int col) {
        ArrayList<Bubble> neighbours = new ArrayList<>();
        if (col > 0) {
            neighbours.add(bubbles.get(row).get(col - 1));
        }
        if (col < (bubbles.get(row).isFull() ? COL_COUNT_FULL : COL_COUNT) - 1) {
            neighbours.add(bubbles.get(row).get(col + 1));
        }
        if (bubbles.get(row).isFull() && col > 0 && row > 0) {
            neighbours.add(bubbles.get(row - 1).get(col - 1));
        }
        if (!bubbles.get(row).isFull() && row > 0) {
            neighbours.add(bubbles.get(row - 1).get(col));
        }
        if (bubbles.get(row).isFull() && col < COL_COUNT_FULL - 1 && row > 0) {
            neighbours.add(bubbles.get(row - 1).get(col));
        }
        if (!bubbles.get(row).isFull() && row > 0) {
            neighbours.add(bubbles.get(row - 1).get(col + 1));
        }
        if (bubbles.get(row).isFull() && col > 0 && row < ROW_COUNT - 1) {
            neighbours.add(bubbles.get(row + 1).get(col - 1));
        }
        if (!bubbles.get(row).isFull() && row < ROW_COUNT - 1) {
            neighbours.add(bubbles.get(row + 1).get(col));
        }
        if (bubbles.get(row).isFull() && col < COL_COUNT_FULL - 1 && row < ROW_COUNT - 1) {
            neighbours.add(bubbles.get(row + 1).get(col));
        }
        if (!bubbles.get(row).isFull() && row < ROW_COUNT - 1) {
            neighbours.add(bubbles.get(row + 1).get(col + 1));
        }
        return neighbours;
    }

    public static double BubbleDist(Bubble b1, Bubble b2) {
        double x_dist = b1.getCenterLocation().x - b2.getCenterLocation().x;
        double y_dist = b1.getCenterLocation().y - b2.getCenterLocation().y;
        return Math.sqrt(Math.pow(x_dist, 2) + Math.pow(y_dist, 2));
    }

    private int removeCoherent(int row, int col) {
        unMarkAll();
        markColor(row, col);
        int ret = 0;
        if (countMarked() > 2) {
            ret = countMarked();
            removeMarked();
        }
        unMarkAll();
        score += ret * SCORE_COHERENT;
        return ret;
    }

    private int removeFloating() {
        markAll();
        bubbles.get(0).stream().filter((b) -> (b.isVisible())).forEachOrdered((b) -> {
            unMarkNotFloating(b.getRow(), b.getCol());
        });
        int ret = countMarked();
        removeMarked();
        unMarkAll();
        score += ret * SCORE_FLOATING;
        return ret;
    }

    private void unMarkNotFloating(int row, int col) {
        bubbles.get(row).get(col).unmark();
        getNeighbours(row, col).stream().filter((b) -> (b.isMarked() && b.isVisible())).forEachOrdered((b) -> {
            unMarkNotFloating(b.getRow(), b.getCol());
        });
    }

    private void markColor(int row, int col) {
        bubbles.get(row).get(col).mark();
        for (Bubble b : getNeighbours(row, col)) {
            if (b.isVisible() && !b.isMarked()) {
                if (b.getColor().equals(bubbles.get(row).get(col).getColor())) {
                    markColor(b.getRow(), b.getCol());
                }
            }
        }
    }

    private int countMarked() {
        int ret = 0;
        for (RowList r : bubbles) {
            for (Bubble b : r) {
                if (b.isMarked() && b.isVisible()) {
                    ret++;
                }
            }
        }
        return ret;
    }

    private void unMarkAll() {
        for (RowList r : bubbles) {
            for (Bubble b : r) {
                b.unmark();
            }
        }
    }

    private void markAll() {
        for (RowList r : bubbles) {
            for (Bubble b : r) {
                b.mark();
            }
        }
    }

    private void removeMarked() {
        for (RowList r : bubbles) {
            for (Bubble b : r) {
                if (b.isMarked()) {
                    b.setVisible(false);
                }
            }
        }
    }

    public boolean isStopped() {
        return stopped;
    }

    public void stop() {
        stopped = true;
    }

    public int getInitialRows() {
        return initial_rows;
    }

    public int getColors() {
        return colors;
    }

    public long getScore() {
        return score;
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        moving_bubble.move();
        checkProximity();
        board.repaint();
    }
}
