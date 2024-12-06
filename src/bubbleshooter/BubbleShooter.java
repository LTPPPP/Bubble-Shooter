package bubbleshooter;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BubbleShooter {
    public static final int WIDTH_BOARD = 600;
    public static final int HEIGHT_BOARD = 400;
    public static final int WINDOW_SIZE_X = 840;
    public static final int WINDOW_SIZE_Y = 400;
    public static final int ROW_DISTANCE = 52;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            createMainMenu();
        });
    }

    static void createMainMenu() {
        JFrame frame = new JFrame("Bubble Shooter");
        frame.setSize(WINDOW_SIZE_X, WINDOW_SIZE_Y);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gradient = new GradientPaint(
                    0, 0, new Color(135, 206, 250),  
                    0, getHeight(), new Color(30, 144, 255)  
                );
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        backgroundPanel.setLayout(new BoxLayout(backgroundPanel, BoxLayout.Y_AXIS));
        backgroundPanel.setBorder(BorderFactory.createEmptyBorder(50, 100, 50, 100));

        // Game Title
        JLabel titleLabel = new JLabel("Bubble Shooter");
        titleLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 36));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton playButton = createStyledButton("Play", new Color(50, 205, 50), new Color(0, 150, 0));
        JButton aboutUsButton = createStyledButton("About Us", new Color(255, 165, 0), new Color(200, 100, 0));
        JButton quitButton = createStyledButton("Quit", new Color(255, 69, 0), new Color(200, 0, 0));

        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainFrame mainFrame = new MainFrame();
                mainFrame.init();
                frame.dispose();
            }
        });

        aboutUsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAboutUs(frame);
            }
        });

        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        backgroundPanel.add(Box.createVerticalGlue());
        backgroundPanel.add(titleLabel);
        backgroundPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        backgroundPanel.add(playButton);
        backgroundPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        backgroundPanel.add(aboutUsButton);
        backgroundPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        backgroundPanel.add(quitButton);
        backgroundPanel.add(Box.createVerticalGlue());

        frame.add(backgroundPanel);
        frame.setVisible(true);
    }

    private static JButton createStyledButton(String text, Color baseColor, Color hoverColor) {
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

        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(250, 60));
        button.setPreferredSize(new Dimension(250, 60));
        button.setFont(new Font("Arial", Font.BOLD, 20));
        button.setForeground(Color.WHITE);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);

        // Add hover effect
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

    private static void showAboutUs(JFrame parentFrame) {
        JOptionPane.showMessageDialog(parentFrame,
                "Bubble Shooter Game\n\n"
                + "Created by: LTP vs NKBN\n"
                + "Version: 1.0\n\n"
                + "Enjoy the exciting bubble shooting adventure!",
                "About Us",
                JOptionPane.INFORMATION_MESSAGE);
    }
}