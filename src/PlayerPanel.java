import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PlayerPanel extends JPanel {

	private int score;
	private JLabel scoreLabel;
	private JLabel nameLabel;
	
	public PlayerPanel(String playerName) {
		this.score = 0;
		setLayout(new GridLayout(2,1));
		
		nameLabel = new JLabel(playerName);
		scoreLabel = new JLabel(this.score + "");
		
		nameLabel.setHorizontalAlignment(JLabel.CENTER);
		scoreLabel.setHorizontalAlignment(JLabel.CENTER);
		
		nameLabel.setFont(new Font("Sans", Font.BOLD, 20));
		scoreLabel.setFont(new Font("Sans", Font.BOLD, 20));
		
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		add(nameLabel);
		add(scoreLabel);
	}

	public void incScore() {
		score++;
		scoreLabel.setText(score + "");
	}
	
	public void resetScore() {
		score = 0;
		scoreLabel.setText(score + "");
	}
	
	public String getName() {
		return nameLabel.getText();
	}
	
	public int getScore() {
		return score;
	}
}
