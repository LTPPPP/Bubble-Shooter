package bubbleshooter;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

public class Bubble {

    private Color color;
    public static final int RADIUS = 14;
    private boolean visible;
    public Point point;
    private boolean marked;

    public Bubble(Color c) {
        color = c;
        marked = false;
    }

    public Bubble(Color color, boolean visible, Point point, boolean marked) {
        this.color = color;
        this.visible = visible;
        this.point = point;
        this.marked = marked;
    }

    public int getRow() {
        return point.y / (BubbleShooter.ROW_DISTANCE / 2);
    }

    public int getCol() {
        return point.x / ((Bubble.RADIUS + 1) * 2);
    }

    public void mark() {
        marked = true;
    }

    public void unmark() {
        marked = false;
    }

    public boolean isMarked() {
        return marked;
    }

    public Color getColor() {
        return color;
    }

    public void setVisible(boolean v) {
        visible = v;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setLocation(Point p) {
        this.point = p;
    }

    public Point getLocation() {
        return point;
    }

    public Point getCenterLocation() {
        return new Point(point.x + RADIUS + 1,
                point.y + RADIUS + 1);
    }

    public void paintBubble(Graphics2D g2d) {
        if (isVisible()) {
            g2d.setColor(color);
            g2d.fillOval(point.x, point.y, RADIUS * 2, RADIUS * 2);
        }
    }

    public static Color getRandomColor(int bound) {
        int rnd = (int) (bound <= 5 ? Math.random() * bound : Math.random() * 5);
        switch (rnd) {
            case 0:
                return Color.blue;
            case 1:
                return Color.red;
            case 2:
                return Color.yellow;
            case 3:
                return Color.green;
            case 4:
                return Color.cyan;
        }
        return null;
    }
}
