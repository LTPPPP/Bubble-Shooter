package bubbleshooter;

public class BubbleShooter {

    public static final int WIDTH_BOARD = 600;
    public static final int HEIGHT_BOARD = 800;

    public static final int WINDOW_SIZE_X = 840;
    public static final int WINDOW_SIZE_Y = 800;


    public static final int ROW_DISTANCE = 52;

    public static void main(String[] args) {
        MainFrame mainFrame = new MainFrame();
        mainFrame.init();
    }
}
