package bubbleshooter;

import java.util.List;

public class Shooting {

    private Bubble bubble;
    private double speedX;
    private double speedY;
    private boolean isShooting;

    public Shooting(Bubble bubble) {
        this.bubble = bubble;
        this.speedX = 0;
        this.speedY = 0;
        this.isShooting = false;
    }

    public void startShooting(int targetX, int targetY) {
        isShooting = true;

        double deltaX = targetX - bubble.getxBub();
        double deltaY = targetY - bubble.getyBub();
        double magnitude = Math.sqrt(deltaX * deltaX + deltaY * deltaY);

        double speed = 10; // Tốc độ bắn
        this.speedX = (deltaX / magnitude) * speed;
        this.speedY = (deltaY / magnitude) * speed;
    }

    public void stopShooting() {
        isShooting = false;
        this.speedX = 0;
        this.speedY = 0;
    }

    public boolean updatePosition(int panelWidth, int panelHeight, int bubbleDiameter, List<Bubble> bubbles) {
        if (isShooting) {
            bubble.setxBub((int) (bubble.getxBub() + speedX));
            bubble.setyBub((int) (bubble.getyBub() + speedY));

            if (bubble.getxBub() <= 0 || bubble.getxBub() + bubbleDiameter >= panelWidth) {
                speedX = -speedX; // Đổi hướng X
            }

            if (bubble.getyBub() <= 0) {
                stopShooting();
                return true;
            }

            for (Bubble other : bubbles) {
                if (isColliding(bubble, other, bubbleDiameter)) {
                    stopShooting();
                    return true;
                }
            }
        }
        return false; 
    }

    private boolean isColliding(Bubble b1, Bubble b2, int diameter) {
        int dx = b1.getxBub() - b2.getxBub();
        int dy = b1.getyBub() - b2.getyBub();
        float distance =(float) Math.sqrt((dx*dx)+(dy*dy)) ;
        return distance <= diameter*2  ;
    }

    public boolean isShooting() {
        return isShooting;
    }
}
