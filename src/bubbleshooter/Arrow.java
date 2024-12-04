package bubbleshooter;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;

public class Arrow {

    private Point p;

    private static final int TIP_LENGTH = 20;
    private static final int LENGTH = 80;

    public Arrow() {
        p = new Point(BubbleShooter.FIELD_SIZE_X / 2, 0);
    }

    public void paintComponent(Graphics2D g2d, Point base) {
        g2d.setColor(Color.red);
        Point mouseLoc = MouseInfo.getPointerInfo().getLocation();
        int x = mouseLoc.x - base.x;
        int y = mouseLoc.y - base.y;
        if ((0 <= x) && (x < BubbleShooter.FIELD_SIZE_X) && (0 <= y) && (y < BubbleShooter.FIELD_SIZE_Y)) {
            p = mouseLoc;
        }
        x = p.x - base.x;
        y = p.y - base.y;
        double angle = Math.atan((double) (x - BubbleShooter.FIELD_SIZE_X / 2) / (BubbleShooter.FIELD_SIZE_Y - y));
        g2d.rotate(angle, BubbleShooter.FIELD_SIZE_X / 2, BubbleShooter.FIELD_SIZE_Y);
        g2d.drawLine(BubbleShooter.FIELD_SIZE_X / 2, BubbleShooter.FIELD_SIZE_Y,
                BubbleShooter.FIELD_SIZE_X / 2, BubbleShooter.FIELD_SIZE_Y - LENGTH);
        g2d.drawLine(BubbleShooter.FIELD_SIZE_X / 2, BubbleShooter.FIELD_SIZE_Y - LENGTH,
                BubbleShooter.FIELD_SIZE_X / 2 - TIP_LENGTH, BubbleShooter.FIELD_SIZE_Y - LENGTH + TIP_LENGTH);
        g2d.drawLine(BubbleShooter.FIELD_SIZE_X / 2, BubbleShooter.FIELD_SIZE_Y - LENGTH,
                BubbleShooter.FIELD_SIZE_X / 2 + TIP_LENGTH, BubbleShooter.FIELD_SIZE_Y - LENGTH + TIP_LENGTH);
        g2d.rotate(-angle, BubbleShooter.FIELD_SIZE_X / 2, BubbleShooter.FIELD_SIZE_Y);
    }

}
