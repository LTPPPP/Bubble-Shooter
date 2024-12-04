package bubbleshooter;

import java.awt.Point;

public class MovingBubble extends Bubble {

    private boolean moving;
    private double step_x;
    private double step_y;
    private double point_x;
    private double point_y;
    private static double STEP = 5;

    public MovingBubble(Bubble b, Point dir) {
        super(b.getColor());
        point = new Point(b.getLocation());
        point_x = point.x;
        point_y = point.y;
        setVisible(true);
        moving = true;
        double offset_x = dir.x - BubbleShooter.FIELD_SIZE_X / 2;
        double offset_y = dir.y - BubbleShooter.FIELD_SIZE_Y;
        double dist = Math.sqrt(Math.pow(offset_x, 2) + Math.pow(offset_y, 2));
        step_x = offset_x / dist * STEP;
        step_y = offset_y / dist * STEP;
    }

    public boolean isMoving() {
        return moving;
    }

    public void setMoving(boolean x) {
        moving = x;
    }

    public void move() {
        if (point_x + step_x < 0) {
            point_x = (int) -(point_x + step_x);
            step_x = -step_x;
        } else if (point_x + step_x > BubbleShooter.FIELD_SIZE_X - 1 - 2 * (Bubble.RADIUS + 1)) {
            point_x = (int) ((BubbleShooter.FIELD_SIZE_X - 1 - 2 * (Bubble.RADIUS + 1)) * 2 - (point_x + step_x));
            step_x = -step_x;
        } else {
            point_x += step_x;
        }
        point_y += step_y;
        point.x = (int) point_x;
        point.y = (int) point_y;
    }
}
