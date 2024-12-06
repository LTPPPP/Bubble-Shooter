package bubbleshooter;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class Board extends JPanel implements
        MouseMotionListener, MouseListener, ActionListener {

    private Arrow arrow;
    private Shooting shooting;
    private JLayeredPane lPane;
    private JScrollPane scrollPane;
    private MainFrame mainFrame;

    private JPanel resultPanel;
    private JLabel resultText;

    private JButton playAgainButton;
    private JButton quitButton;

    public Board(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout());
        setPreferredSize(
                new Dimension(BubbleShooter.WIDTH_BOARD,
                        BubbleShooter.HEIGHT_BOARD));
        setBorder(BorderFactory.createEmptyBorder());
        addMouseMotionListener(this);
        addMouseListener(this);
        setOpaque(true);
        arrow = new Arrow();

        resultText = new JLabel();

        lPane = new JLayeredPane();
        lPane.setBackground(Color.GRAY);

        JPanel blur = new JPanel();
        blur.setBackground(new Color(255, 255, 255, 120));
        blur.setBounds(0, 0, BubbleShooter.WIDTH_BOARD, BubbleShooter.HEIGHT_BOARD);

        lPane.add(blur, JLayeredPane.DEFAULT_LAYER);

        resultPanel = new JPanel();
        resultPanel.setLayout(new BorderLayout());
        resultPanel.setBounds(80, 60, BubbleShooter.WIDTH_BOARD - 2 * 80, 185);
        resultPanel.setBorder(BorderFactory.createLineBorder(Color.darkGray));

        JPanel subNamePanel = new JPanel();
        subNamePanel.setLayout(new BoxLayout(subNamePanel, BoxLayout.Y_AXIS));
        subNamePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        resultText = new JLabel("Kecskeeee");
        resultText.setFont(new Font(resultText.getFont().getName(), Font.ITALIC, 60));
        resultText.setAlignmentX(CENTER_ALIGNMENT);
        resultText.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));

        JPanel buttonsPanel = new JPanel();
        playAgainButton = new JButton("Play Again");
        quitButton = new JButton("Quit");

        JPanel buttonPanel = new JPanel();
        playAgainButton = new JButton("Play Again");
        quitButton = new JButton("Quit");
        playAgainButton.setActionCommand("Play Again");
        quitButton.setActionCommand("Quit");
        playAgainButton.addActionListener(mainFrame);
        quitButton.addActionListener(mainFrame);

        buttonsPanel.add(playAgainButton);
        buttonsPanel.add(quitButton);

        subNamePanel.add(resultText);
        subNamePanel.add(buttonsPanel);
        resultPanel.add(subNamePanel, BorderLayout.CENTER);
    }

    public void displayHighscore(long score, boolean win) {
        String result = "";
        if (win) {
            result += "You Win : " + score + " point";
        } else {
            result += "You Lose : " + score + " point";
        }
        resultText.setText(result);

        // Optional: Add a specific label for win/lose scenarios
        JLabel messageLabel = new JLabel();
        messageLabel.setFont(new Font(messageLabel.getFont().getName(), Font.PLAIN, 20));
        messageLabel.setAlignmentX(CENTER_ALIGNMENT);
        messageLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));

        if (win) {
            messageLabel.setText("Great job! You've won the game! 🎉🏆");
        } else {
            messageLabel.setText("You lose, Haha, too noob 😒 😽!!!");
        }

        // Add the message label to the result panel
        if (resultPanel != null) {
            ((JPanel) resultPanel.getComponent(0)).add(messageLabel, 1); // Add after resultText
        }

        if (score != 0) {
            lPane.add(resultPanel, JLayeredPane.DRAG_LAYER);
        }
        add(lPane);
        repaint();
    }

    public void newGame(int row, int color) {
        // Remove the result panel if it exists
        if (lPane != null) {
            remove(lPane);
        }

        resultPanel = new JPanel();
        resultPanel.setLayout(new BorderLayout());
        resultPanel.setBounds(80, 60, BubbleShooter.WIDTH_BOARD - 2 * 80, 185);
        resultPanel.setBorder(BorderFactory.createLineBorder(Color.darkGray));

        JPanel subNamePanel = new JPanel();
        subNamePanel.setLayout(new BoxLayout(subNamePanel, BoxLayout.Y_AXIS));
        subNamePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        resultText = new JLabel("Kecskeeee");
        resultText.setFont(new Font(resultText.getFont().getName(), Font.ITALIC, 30));
        resultText.setAlignmentX(CENTER_ALIGNMENT);
        resultText.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));

        // Recreate buttons panel
        JPanel buttonsPanel = new JPanel();
        playAgainButton = new JButton("Play Again");
        quitButton = new JButton("Quit");
        playAgainButton.setActionCommand("Play Again");
        quitButton.setActionCommand("Quit");
        playAgainButton.addActionListener(mainFrame);
        quitButton.addActionListener(mainFrame);

        buttonsPanel.add(playAgainButton);
        buttonsPanel.add(quitButton);

        subNamePanel.add(resultText);
        subNamePanel.add(buttonsPanel);
        resultPanel.add(subNamePanel, BorderLayout.CENTER);

        // Recreate layered pane
        lPane = new JLayeredPane();
        lPane.setBackground(Color.GRAY);

        JPanel blur = new JPanel();
        blur.setBackground(new Color(255, 255, 255, 120));
        blur.setBounds(0, 0, BubbleShooter.WIDTH_BOARD, BubbleShooter.HEIGHT_BOARD);

        lPane.add(blur, JLayeredPane.DEFAULT_LAYER);

        // Prepare new game
        shooting = new Shooting(row, color, this);
        repaint();
    }

    public Shooting getGame() {
        return shooting;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHints(
                new RenderingHints(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON));
        if (shooting != null) {
            shooting.paintBubbles(g2d);
        }
        arrow.paintComponent(g2d, getLocationOnScreen());
    }

    ;

    @Override
    public void mouseDragged(MouseEvent arg0) {
        mouseMoved(arg0);
    }

    @Override
    public void mouseMoved(MouseEvent arg0) {
        repaint();
    }

    @Override
    public void mouseClicked(MouseEvent arg0) {
        if (shooting != null) {
            if (!shooting.isStopped()) {
                shooting.fire(MouseInfo.getPointerInfo().getLocation(), getLocationOnScreen());
                repaint();
            }
        }
    }

    @Override
    public void mouseEntered(MouseEvent arg0) {
    }

    @Override
    public void mouseExited(MouseEvent arg0) {
        if (shooting != null) {
            if (!shooting.isStopped()) {
                shooting.fire(MouseInfo.getPointerInfo().getLocation(), getLocationOnScreen());
                repaint();
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent arg0) {
        if (shooting != null) {
            if (!shooting.isStopped()) {
                shooting.fire(MouseInfo.getPointerInfo().getLocation(), getLocationOnScreen());
                repaint();
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent arg0) {
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }

}
