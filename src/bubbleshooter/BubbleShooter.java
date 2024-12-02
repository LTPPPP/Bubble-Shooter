package bubbleshooter;

import java.awt.BorderLayout;
import javax.swing.*;

public class BubbleShooter {

    public static void main(String[] args) {
        // Tạo khung chính (JFrame)
        JFrame frame = new JFrame("Bubble Shooter");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 800);
        frame.setLayout(new BorderLayout());

        // Đặt vị trí của khung chính ở giữa màn hình
        frame.setLocationRelativeTo(null);

        // Hiển thị cửa sổ
        frame.setVisible(true);
    }
}
