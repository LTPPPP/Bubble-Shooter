package bubbleshooter;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;

public class Arrow {

    private Point p;

    private static final int TIP_LENGTH = 10;
    private static final int LENGTH = 80;

    public Arrow() {
        p = new Point(BubbleShooter.WIDTH_BOARD / 2, 0);
    }

    public void paintComponent(Graphics2D g2d, Point base) {
        g2d.setColor(Color.red);
        Point mouseLoc = MouseInfo.getPointerInfo().getLocation();
        int x = mouseLoc.x - base.x;
        int y = mouseLoc.y - base.y;
        if ((0 <= x) && (x < BubbleShooter.WIDTH_BOARD) && (0 <= y) && (y < BubbleShooter.HEIGHT_BOARD)) {
            p = mouseLoc;
        }
        x = p.x - base.x;
        y = p.y - base.y;
        double angle = Math.atan((double) (x - BubbleShooter.WIDTH_BOARD / 2) / (BubbleShooter.HEIGHT_BOARD - y));
        g2d.rotate(angle, BubbleShooter.WIDTH_BOARD / 2, BubbleShooter.HEIGHT_BOARD);
        g2d.drawLine(BubbleShooter.WIDTH_BOARD / 2, BubbleShooter.HEIGHT_BOARD,
                BubbleShooter.WIDTH_BOARD / 2, BubbleShooter.HEIGHT_BOARD - LENGTH);
        g2d.drawLine(BubbleShooter.WIDTH_BOARD / 2, BubbleShooter.HEIGHT_BOARD - LENGTH,
                BubbleShooter.WIDTH_BOARD / 2 - TIP_LENGTH, BubbleShooter.HEIGHT_BOARD - LENGTH + TIP_LENGTH);
        g2d.drawLine(BubbleShooter.WIDTH_BOARD / 2, BubbleShooter.HEIGHT_BOARD - LENGTH,
                BubbleShooter.WIDTH_BOARD / 2 + TIP_LENGTH, BubbleShooter.HEIGHT_BOARD - LENGTH + TIP_LENGTH);
        g2d.rotate(-angle, BubbleShooter.WIDTH_BOARD / 2, BubbleShooter.HEIGHT_BOARD);
    }

}
