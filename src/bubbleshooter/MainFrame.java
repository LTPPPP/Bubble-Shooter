package bubbleshooter;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

public class MainFrame extends JFrame implements ActionListener {

    private SettingPanel rightPanel;
    private Board leftPanel;

    public MainFrame() {
        setLayout(new BorderLayout());

        rightPanel = new SettingPanel(this);
        rightPanel.initComponents();

        leftPanel = new Board();

        add(leftPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.CENTER);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Bubble Shooter");
        setSize(BubbleShooter.WINDOW_SIZE_X, BubbleShooter.WINDOW_SIZE_Y);
        setResizable(true);
        setVisible(true);
        setSize(2 * BubbleShooter.WINDOW_SIZE_X - getContentPane().getSize().width,
                2 * BubbleShooter.WINDOW_SIZE_Y - getContentPane().getSize().height);
    }

    public void init() {
    }

    public void gameWon(long score) {
        rightPanel.updateScore(score);
    }

    public void gameLost(long score) {
        rightPanel.updateScore(score);
    }

    public void updateScore(long score) {
        rightPanel.updateScore(score);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("NEWGAME")) {
            leftPanel.newGame(rightPanel.getRow(), rightPanel.getColor());
            leftPanel.getGame().setMainFrame(this);
        } else if (e.getActionCommand().equals("STOPGAME")) {
            if (leftPanel.getGame() != null) {
                leftPanel.getGame().stop();
            }
        }
    }
}
