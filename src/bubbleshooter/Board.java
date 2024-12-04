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
    private JPanel highscorePanel;
    private JPanel namePanel;
    private JLabel resultText;
    private JTextField textField;
    private HighScores highscores;
    private JTable highscoreTable;
    private JScrollPane scrollPane;
    private static final String fileName = "bubble_shooter_hs.dat";

    public Board() {
        setLayout(new BorderLayout());
        setPreferredSize(
                new Dimension(BubbleShooter.FIELD_SIZE_X,
                        BubbleShooter.FIELD_SIZE_Y));
        setBorder(BorderFactory.createEmptyBorder());
        addMouseMotionListener(this);
        addMouseListener(this);
        setOpaque(true);
        arrow = new Arrow();

        lPane = new JLayeredPane();
        lPane.setBackground(new Color(0, 0, 0, 0));

        JPanel blur = new JPanel();
        blur.setBackground(new Color(255, 255, 255, 120));
        blur.setBounds(0, 0, BubbleShooter.FIELD_SIZE_X, BubbleShooter.FIELD_SIZE_Y);

        highscorePanel = new JPanel();
        highscorePanel.setBackground(new Color(highscorePanel.getBackground().getRed(),
                highscorePanel.getBackground().getGreen(),
                highscorePanel.getBackground().getRed(),
                120));
        highscorePanel.setBounds(40, 20, BubbleShooter.FIELD_SIZE_X - 2 * 40, BubbleShooter.FIELD_SIZE_Y - 2 * 30);
        highscorePanel.setLayout(new BorderLayout());

        highscores = new HighScores();

        highscoreTable = new JTable();
        highscoreTable.setFillsViewportHeight(true);
        highscoreTable.setModel(highscores);
        highscoreTable.getTableHeader().setReorderingAllowed(false);
        scrollPane = new JScrollPane(highscoreTable);
        highscorePanel.add(scrollPane, BorderLayout.CENTER);

        lPane.add(blur, JLayeredPane.DEFAULT_LAYER);
        lPane.add(highscorePanel, JLayeredPane.PALETTE_LAYER);

        namePanel = new JPanel();
        namePanel.setLayout(new BorderLayout());
        namePanel.setBounds(80, 60, BubbleShooter.FIELD_SIZE_X - 2 * 80, 185);
        namePanel.setBorder(BorderFactory.createLineBorder(Color.darkGray));

        JPanel subNamePanel = new JPanel();
        subNamePanel.setLayout(new BoxLayout(subNamePanel, BoxLayout.Y_AXIS));
        subNamePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        resultText = new JLabel("Kecskeeee");
        resultText.setFont(new Font(resultText.getFont().getName(), Font.ITALIC, 30));
        resultText.setAlignmentX(CENTER_ALIGNMENT);
        resultText.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));

        JLabel please = new JLabel("<html><div style=\"text-align: center;\">You made it to the toplist, please enter your name and click the button to proceed!</html>");
        please.setFont(new Font(please.getFont().getName(), Font.PLAIN, 13));
        please.setAlignmentX(CENTER_ALIGNMENT);
        please.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));

        textField = new JTextField(20);
        JButton button = new JButton("Enter");
        button.addActionListener(this);

        JPanel formContainer = new JPanel();
        formContainer.add(textField);
        formContainer.add(button);

        subNamePanel.add(resultText);
        subNamePanel.add(please);
        subNamePanel.add(formContainer);
        namePanel.add(subNamePanel, BorderLayout.CENTER);

    }

    public void displayHighscore(long score, boolean win) {

        resultText.setText(win ? "You win!" : "You lose");
        if (score != 0) {
            lPane.add(namePanel, JLayeredPane.DRAG_LAYER);
        }
        add(lPane);
        loadHighScores();
        highscoreTable.setModel(highscores);
        repaint();
    }

    public void newGame(int row, int color) {
        shooting = new Shooting(row, color, this);
        lPane.remove(namePanel);
        remove(lPane);
        repaint();
    }

    public Shooting getGame() {
        return shooting;
    }

    private void saveHighScores() {
        try {
            ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(fileName));
            os.writeObject(highscores);
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadHighScores() {
        try {
            File f = new File("bubble_shooter_hs.dat");
            if (f.exists()) {
                ObjectInputStream os = new ObjectInputStream(new FileInputStream(fileName));
                highscores = (HighScores) os.readObject();
                os.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        if (!textField.getText().equals("")) {
            highscores.addEntry(new HighscoreEntry(textField.getText(),
                    shooting.getScore(), shooting.getInitialRows(), shooting.getColors()));
            saveHighScores();
            lPane.remove(namePanel);
            displayHighscore(0, true);
        }
    }
}
