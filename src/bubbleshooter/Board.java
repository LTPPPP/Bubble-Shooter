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
        // Set up a vibrant color scheme
        Color winColor = new Color(76, 175, 80);    // Bright green for winning
        Color loseColor = new Color(244, 67, 54);   // Bright red for losing

        // Create a more dynamic result text
        String result = String.format("%s: %d Points",
                win ? "Victory" : "Game Over", score);

        // Update result text with new styling
        resultText.setText(result);
        resultText.setFont(new Font("Arial", Font.BOLD, 20));
        resultText.setForeground(win ? winColor : loseColor);

        // Create an enhanced message label
        JLabel messageLabel = new JLabel();
        messageLabel.setFont(new Font("Comic Sans MS", Font.ITALIC, 20));
        messageLabel.setAlignmentX(CENTER_ALIGNMENT);
        messageLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        // Add more expressive and fun messages
        if (win) {
            messageLabel.setText("Congratulations! You crushed it!");
            messageLabel.setForeground(winColor.darker());
        } else {
            messageLabel.setText("Haha you lose, too noobbbbb");
            messageLabel.setForeground(loseColor.darker());
        }

        // Enhance result panel visually
        resultPanel.setBackground(new Color(255, 255, 255, 200));
        resultPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(win ? winColor : loseColor, 3),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        // Add components to the result panel
        JPanel subNamePanel = (JPanel) resultPanel.getComponent(0);
        subNamePanel.setOpaque(false);

        // Clear previous components and add new ones
        subNamePanel.removeAll();
        subNamePanel.add(resultText);
        subNamePanel.add(messageLabel);

        // Style buttons
        playAgainButton.setBackground(new Color(33, 150, 243)); // Blue
        playAgainButton.setForeground(Color.WHITE);
        playAgainButton.setFont(new Font("Arial", Font.BOLD, 16));

        quitButton.setBackground(new Color(244, 67, 54)); // Red
        quitButton.setForeground(Color.WHITE);
        quitButton.setFont(new Font("Arial", Font.BOLD, 16));

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setOpaque(false);
        buttonsPanel.add(playAgainButton);
        buttonsPanel.add(quitButton);

        subNamePanel.add(buttonsPanel);

        // Add to layered pane
        if (score != 0) {
            lPane.add(resultPanel, JLayeredPane.DRAG_LAYER);
        }

        add(lPane);
        revalidate();
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
