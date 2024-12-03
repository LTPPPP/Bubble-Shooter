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

            // Xử lý va chạm biên
            if (bubble.getxBub() <= 0 || bubble.getxBub() + bubbleDiameter >= panelWidth) {
                speedX = -speedX; // Đổi hướng X
            }
            if (bubble.getyBub() <= 0) {
                stopShooting();
                return true; // Dừng bắn khi chạm đỉnh
            }

            // Kiểm tra va chạm giữa hình tròn hiện tại và tất cả các hình tròn khác
            if (isColliding(bubble, bubbles, bubbleDiameter)) {
                stopShooting();
                return true; // Va chạm xảy ra
            }
        }
        return false;
    }

    private boolean isColliding(Bubble b1, List<Bubble> others, int diameter) {
        int radius = diameter / 2;
        double maxDistance = 2 * radius + 2; // Sai số 2px

        for (Bubble b2 : others) {
            if (b1 == b2) {
                continue; // Bỏ qua so sánh với chính nó
            }
            int dx = b2.getxBub() - b1.getxBub();
            int dy = b2.getyBub() - b1.getyBub();
            double distance = Math.sqrt(dx * dx + dy * dy);

            if (distance <= maxDistance) {
                return true; // Va chạm xảy ra
            }
        }

        return false; // Không va chạm
    }

    public boolean isShooting() {
        return isShooting;
    }
}
