package bubbleshooter;

import java.awt.*;
import javax.swing.*;
import java.awt.GridLayout;

import javax.swing.SwingUtilities;

public class BubbleShooter {

    public static final int WIDTH_BOARD = 600;
    public static final int HEIGHT_BOARD = 800;

    public static final int WINDOW_SIZE_X = 640;
    public static final int WINDOW_SIZE_Y = 480;

    public static final int FIELD_SIZE_X = 420;
    public static final int FIELD_SIZE_Y = 480;

    public static final int ROW_DISTANCE = 52;

    public static void main(String[] args) {
        MainFrame mainFrame = new MainFrame();
        mainFrame.init();
    }
}
