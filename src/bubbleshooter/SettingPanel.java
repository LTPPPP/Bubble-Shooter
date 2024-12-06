package bubbleshooter;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Cursor;
import javax.swing.*;

public class SettingPanel extends JPanel {
    private MainFrame mainFrame;
    private JLabel scoreLabel;
    private JPanel lowerPanel;
    private JButton newGameButton;
    private JButton homeButton;
    private JButton stopGameButton;
    
    //Row and color
    public int rowsSpinner = 7;
    public int colorSpinner = 5;

    public SettingPanel(MainFrame m) {
        // Set a gradient background
        setBackground(new Color(240, 248, 255)); // Light blue background
        setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 1, 0, 0, new Color(70, 130, 180)), // Steel Blue border
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        setLayout(new BorderLayout());
        mainFrame = m;
    }

    public void initComponents() {
        // Score Label
        scoreLabel = new JLabel("0", SwingConstants.RIGHT);
        scoreLabel.setOpaque(true);
        scoreLabel.setBackground(new Color(70, 130, 180)); // Steel Blue
        scoreLabel.setForeground(Color.WHITE);
        scoreLabel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(100, 149, 237)), // Cornflower Blue
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        scoreLabel.setPreferredSize(new Dimension(BubbleShooter.WINDOW_SIZE_X - BubbleShooter.WIDTH_BOARD - 5, 50));
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 34));

        // Lower Panel with GridBagLayout for better control
        lowerPanel = new JPanel(new GridBagLayout());
        lowerPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        // Button Panel with Vertical Layout
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setOpaque(false);

        // New Game Button
        newGameButton = createStyledButton("New Game", new Color(50, 205, 50), new Color(0, 150, 0));
        newGameButton.setActionCommand("NEWGAME");
        newGameButton.addActionListener(mainFrame);

        // Home Button
        homeButton = createStyledButton("Home", new Color(255, 165, 0), new Color(200, 100, 0));
        homeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.dispose(); 
                SwingUtilities.invokeLater(() -> {
                    BubbleShooter.createMainMenu();
                });
            }
        });

        buttonPanel.add(newGameButton);
        buttonPanel.add(Box.createVerticalStrut(10));
        buttonPanel.add(homeButton);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 5, 5, 5);
        lowerPanel.add(buttonPanel, gbc);

        add(scoreLabel, BorderLayout.NORTH);
        add(lowerPanel, BorderLayout.CENTER);
    }

    private JButton createStyledButton(String text, Color baseColor, Color hoverColor) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                if (getModel().isRollover()) {
                    g2d.setColor(hoverColor);
                } else {
                    g2d.setColor(baseColor);
                }
                
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                g2d.setColor(Color.WHITE);
                g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 15, 15);
                
                super.paintComponent(g);
                g2d.dispose();
            }
        };

        button.setPreferredSize(new Dimension(150, 40));
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setForeground(Color.WHITE);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setCursor(Cursor.getDefaultCursor());
            }
        });

        return button;
    }

    public void updateScore(long score) {
        scoreLabel.setText(Long.toString(score));
    }

    public int getRow() {
        return (int) rowsSpinner;
    }

    public int getColor() {
        return (int) colorSpinner;
    }
}