package bubbleshooter;

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

    public boolean updatePosition(int panelWidth, int panelHeight, int bubbleDiameter) {
        if (isShooting) {
            bubble.setxBub((int) (bubble.getxBub() + speedX));
            bubble.setyBub((int) (bubble.getyBub() + speedY));

            if (bubble.getxBub() <= 0 || bubble.getxBub() + bubbleDiameter >= panelWidth) {
                speedX = -speedX; // Đổi hướng X
            }

            if (bubble.getyBub() <= 0 || bubble.getyBub() + bubbleDiameter >= panelHeight) {
                stopShooting();
                return true;
            }
        }
        return false; // Không cần tạo bóng mới
    }

    public boolean isShooting() {
        return isShooting;
    }
}