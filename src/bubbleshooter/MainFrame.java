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

        leftPanel = new Board(this);

        add(leftPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.CENTER);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Bubble Shooter");
        setSize(BubbleShooter.WINDOW_SIZE_X, BubbleShooter.WINDOW_SIZE_Y);
        setResizable(true);
        setVisible(true);
        setSize(2 * BubbleShooter.WINDOW_SIZE_X - getContentPane().getSize().width,
                2 * BubbleShooter.WINDOW_SIZE_Y - getContentPane().getSize().height + 50);
        setLocationRelativeTo(null);
    }

    public void init() {
        leftPanel.displayHighscore(0, true);
    }

    public void gameWon(long score) {
        rightPanel.updateScore(score);
        leftPanel.displayHighscore(score, true);
    }

    public void gameLost(long score) {
        rightPanel.updateScore(score);
        leftPanel.displayHighscore(score, false);
    }

    public void updateScore(long score) {
        rightPanel.updateScore(score);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("NEWGAME")) {
            System.out.println("NEW");
            leftPanel.newGame(rightPanel.getRow(), rightPanel.getColor());
            leftPanel.getGame().setMainFrame(this);
            leftPanel.getGame().reset();
        } else if (e.getActionCommand().equals("Quit")) {
            System.out.println("QUIT");
            System.exit(0);
        } else if (e.getActionCommand().equals("Play Again")) {
            System.out.println("AGAIN");
            leftPanel.newGame(rightPanel.getRow(), rightPanel.getColor());
            leftPanel.getGame().setMainFrame(this);
            leftPanel.getGame().reset();
        }
    }
}
