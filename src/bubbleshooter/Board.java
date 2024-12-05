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

    private JPanel resultPanel;
    private JLabel resultText;

    private JButton playAgainButton;
    private JButton quitButton;

    public Board() {
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
        resultText.setFont(new Font(resultText.getFont().getName(), Font.ITALIC, 30));
        resultText.setAlignmentX(CENTER_ALIGNMENT);
        resultText.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));

        JLabel please = new JLabel(
                "<html><div style=\"text-align: center; Fontsize : 2rem;\">You lose, Haha, too noob 😒 😽!!!</html>");
        please.setFont(new Font(please.getFont().getName(), Font.PLAIN, 13));
        please.setAlignmentX(CENTER_ALIGNMENT);
        please.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));

        JPanel buttonsPanel = new JPanel();
        playAgainButton = new JButton("Play Again");
        quitButton = new JButton("Quit");

        // Add action listeners to buttons
        playAgainButton.addActionListener(this);
        quitButton.addActionListener(this);

        buttonsPanel.add(playAgainButton);
        buttonsPanel.add(quitButton);

        subNamePanel.add(resultText);
        subNamePanel.add(please);
        subNamePanel.add(buttonsPanel);
        resultPanel.add(subNamePanel, BorderLayout.CENTER);
    }

    public void displayHighscore(long score, boolean win) {

        resultText.setText(win ? "You win!" : "You lose");
        if (score != 0) {
            lPane.add(resultPanel, JLayeredPane.DRAG_LAYER);
        }
        add(lPane);
        repaint();
    }

    public void newGame(int row, int color) {
        shooting = new Shooting(row, color, this);
        remove(lPane);
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
    }

    @Override
    public void mouseReleased(MouseEvent arg0) {
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }

}
