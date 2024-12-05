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
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

public class Board extends JPanel implements
        MouseMotionListener, MouseListener, ActionListener {

    private Arrow arrow;
    private Shooting shooting;
    private JLayeredPane lPane;
    private JScrollPane scrollPane;

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

        lPane = new JLayeredPane();
        lPane.setBackground(Color.GRAY);

        JPanel blur = new JPanel();
        blur.setBackground(new Color(255, 255, 255, 120));
        blur.setBounds(0, 0, BubbleShooter.WIDTH_BOARD, BubbleShooter.HEIGHT_BOARD);
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
