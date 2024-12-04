package bubbleshooter;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.*;

public class SettingPanel extends JPanel {

	private MainFrame mainFrame;
	private JLabel scoreLabel;
	private JPanel lowerPanel;
	private int rowsSpinner = 5;
	private int colorSpinner = 4;
	private JButton newGameButton;
	private JButton stopGameButton;

	public SettingPanel(MainFrame m) {
		setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, Color.darkGray));
		setLayout(new BorderLayout());
		mainFrame = m;
	}

	public void initComponents() {
		scoreLabel = new JLabel("0", SwingConstants.RIGHT);
		scoreLabel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.lightGray));
		scoreLabel.setPreferredSize(new Dimension(BubbleShooter.WINDOW_SIZE_X - BubbleShooter.WIDTH_BOARD - 5, 50));
		scoreLabel.setFont(new Font(scoreLabel.getFont().getName(), Font.PLAIN, 34));

		lowerPanel = new JPanel();
		lowerPanel.setLayout(new BoxLayout(lowerPanel, BoxLayout.Y_AXIS));

		JPanel rowsPanel = new JPanel();
		rowsPanel.setPreferredSize(new Dimension(BubbleShooter.WINDOW_SIZE_X - BubbleShooter.WIDTH_BOARD - 5, 50));
		rowsPanel.setLayout(new BorderLayout());
		SpinnerModel rowsModell = new SpinnerNumberModel(7, 3, 15, 1);
		

		JPanel colorPanel = new JPanel();
		colorPanel.setPreferredSize(new Dimension(BubbleShooter.WINDOW_SIZE_X - BubbleShooter.WIDTH_BOARD - 5, 50));
		colorPanel.setLayout(new BorderLayout());
		SpinnerModel colorModell = new SpinnerNumberModel(4, 3, 8, 1);

		JPanel buttonPanel = new JPanel();
		newGameButton = new JButton("New game");
		newGameButton.setActionCommand("NEWGAME");
		newGameButton.addActionListener(mainFrame);

		stopGameButton = new JButton("Stop game");
		stopGameButton.setActionCommand("STOPGAME");
		stopGameButton.addActionListener(mainFrame);
		buttonPanel.add(newGameButton);
		buttonPanel.add(stopGameButton);

		lowerPanel.add(rowsPanel);
		lowerPanel.add(colorPanel);
		lowerPanel.add(buttonPanel);
		JPanel spaceholder = new JPanel();
		spaceholder.setPreferredSize(new Dimension(BubbleShooter.WINDOW_SIZE_X - BubbleShooter.WIDTH_BOARD - 5, 340));
		lowerPanel.add(spaceholder);
		add(scoreLabel, BorderLayout.NORTH);
		add(lowerPanel, BorderLayout.CENTER);
	}

	public void updateScore(long score) {
		scoreLabel.setText((new Long(score).toString()));
	}

	public int getRow() {
		return (int) rowsSpinner;
	}

	public int getColor() {
		return (int) colorSpinner;
	}
}
