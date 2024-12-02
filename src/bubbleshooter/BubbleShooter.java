package bubbleshooter;

import java.awt.*;
import javax.swing.*;

public class BubbleShooter {

    public static final int WIDTH_BOARD = 600;
    public static final int HEIGHT_BOARD = 800;

    public static void main(String[] args) {
        // Create the main frame
        JFrame frame = new JFrame("Bubble Shooter");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(WIDTH_BOARD+18, HEIGHT_BOARD);
        frame.setLayout(new BorderLayout());

        // Create and add the Board panel
        Board board = new Board();
        board.setBackground(Color.DARK_GRAY); 
        frame.add(board, BorderLayout.CENTER);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
