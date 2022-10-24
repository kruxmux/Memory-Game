import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class Memory extends JFrame implements Runnable {
	
	private File folder;
	private File[] pictures;
	private ImageIcon[] icons;
	private int cardsNeeded;
	private List<Card> cards;
	
	// Till användargränssnittet
	private JPanel cardPanel;
	private JPanel scorePanel;
	private SettingsMenuBar smb;
	private int rows;
	private int cols;

	private Timer timer;
	private boolean timerActive;
	private int timerDelay = 500;
	private Sound sounds;
	
	// Innehåller de synliga korten
	private Card[] visCards;
	
	private PlayerPanel player1;
	private PlayerPanel player2;
	private boolean player1Turn;
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Memory());
	}
	
	public Memory() {
		// Hämta bilder och placera i en array
		folder = new File("src\\CardImages");
		// .DS_Store är en gömd fil som finns i alla mac-folders
		pictures = folder.listFiles((dir, name) -> !name.equals(".DS_Store"));
		icons = new ImageIcon[pictures.length];
		
		for (int i = 0; i < icons.length; i++) {
			icons[i] = new ImageIcon(pictures[i].getPath());
		}

		cards = new ArrayList<Card>();
		visCards = new Card[2];
		player1Turn = true;
		sounds = new Sound();
		
	}
	
	@Override
	public void run() {
		setTitle("Memory");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		timerDelay = 1500;
		timer = new Timer(timerDelay, pauseListener);
		timer.setRepeats(false);
		timerActive = false;
		
		// Dialogruta för att bestämma spelets dimensioner
		new DimensionPrompt(this);
		
		// Menyrad med inställningar
		smb = new SettingsMenuBar(this);
		getContentPane().add(smb, BorderLayout.NORTH);
		
		// Lägger till en panel för att hålla koll på spelarnas poäng
		player1 = new PlayerPanel("William");
		player2 = new PlayerPanel("Eric");
		addScorePanel();
		
		// Lägger till kort baserat på användarens val
		cardPanel = new JPanel();
		newGame();
		resetBoard();
		
		// Låt swing hitta rätt dimensioner för komponenter och centrera fönstret på skärmen
		pack();
		centerWindow();
	}

	private void addScorePanel() {
		scorePanel = new JPanel();
		scorePanel.setLayout(new GridLayout(2,1));
		scorePanel.setBackground(Color.GRAY);
		scorePanel.setPreferredSize(new Dimension(115,115));
		
		scorePanel.add(player1);
		scorePanel.add(player2);
		
		getContentPane().add(scorePanel, BorderLayout.LINE_START);
		highlightCurPlayer();
	}
	
	// Placera fönstret i mitten av skärmen
	private void centerWindow() {
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int) ((dimension.getWidth() - getWidth()) / 2);
		int y = (int) ((dimension.getHeight() - getHeight()) / 2);
		setLocation(x, y);
	}
	
	public void newGame() {
		cards.clear();
		player1.resetScore();
		player2.resetScore();
		visCards[0] = null;
		visCards[1] = null;
		// Spelare 1 startar alltid
		player1Turn = true;
		highlightCurPlayer();
		
		ImageIcon[] iconsCopy = icons.clone();
		Tools.randomOrder(iconsCopy);
		
		cardsNeeded = rows * cols / 2;
		Card[] cards = new Card[cardsNeeded * 2];
		for (int i = 0; i < cardsNeeded; i++) {
			int j = 2 * i;
			cards[j] = new Card(iconsCopy[i], Card.Status.HIDDEN);
			cards[j + 1] = new Card(iconsCopy[i], Card.Status.HIDDEN);
		}
		Tools.randomOrder(cards);
		this.cards.addAll(Arrays.asList(cards));
	}
	
	public void resetBoard() {
		// Rensa gamla kort
		cardPanel.removeAll();
		
		// Lägg till nya kort
		for (Card c : cards) {
			c.setPreferredSize(new Dimension(115, 115));
			c.setBorder(BorderFactory.createLineBorder(Color.GRAY));
			
			c.addActionListener(new CardListener(this, c));
			cardPanel.add(c);
		}
		
		cardPanel.setLayout(new GridLayout(rows, cols));		
		getContentPane().add(cardPanel, BorderLayout.CENTER);
		pack();
		setVisible(true);
	}
	
	public void addVisibleCard(Card c) {
		if (visCards[0] == null) {
			visCards[0] = c;
		} else {
			visCards[1] = c;
		}
	}
	
	public void checkVisibleCards() {
		// Om två kort är vända, starta timern
		if (visCards[0] != null && visCards[1] != null) {
			timer.start();
			timerActive = true;	
		}
	}
	
	private boolean gameHasEnded() {
		if (player1.getScore() + player2.getScore() == cardsNeeded) {
			return true;
		}
		
		return false;
	}
	
	private void printWinner() {
		PlayerPanel winner = null;
		
		if (player1.getScore() > player2.getScore()) {
			winner = player1;
		} else if (player1.getScore() < player2.getScore()) {
			winner = player2;
		} else {
			JOptionPane.showMessageDialog(this, "Spelet är slut! Oavgjort!");
			return;
		}
		
		JOptionPane.showMessageDialog(this, "Spelet är slut! " + winner.getName() + " vinner!");
	}

	public boolean timerIsActive() {
		return timerActive;
	}
	
	private void highlightCurPlayer() {
		if (player1Turn) {
			player1.setBackground(Color.YELLOW);
			player2.setBackground(null);
		} else {
			player1.setBackground(null);
			player2.setBackground(Color.YELLOW);
		}
	}
	
	
	public ImageIcon[] getIcons() {
		return icons;
	}
	
	public void setRows(int rows) {
		this.rows = rows;
	}
	
	public void setCols(int cols) {
		this.cols = cols;
	}
	
	public void setCardsNeeded(int cardsNeeded) {
		this.cardsNeeded = cardsNeeded;
	}
	
	public void setTimerDelay(int timerDuration) {
		this.timerDelay = timerDuration;
		timer.setInitialDelay(timerDuration);
	}
	
	// Actionlisteners för korten och pause
	private ActionListener pauseListener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			if (visCards[0].sameIcon(visCards[1])) {
				removeCards();
				
				if (smb.soundOn()) {
					sounds.playYesSound();
				}
			} else {
				visCards[0].setStatus(Card.Status.HIDDEN);
				visCards[1].setStatus(Card.Status.HIDDEN);
				sounds.playNoSound();
				
				// Byt spelare
				player1Turn = !player1Turn;
				highlightCurPlayer();
			}
			
			visCards[0] = null;
			visCards[1] = null;
			
			timerActive = false;
			
			if (gameHasEnded()) {
				printWinner();
				newGame();
				resetBoard();
			}
		}

		private void removeCards() {
			visCards[0].setStatus(Card.Status.MISSING);
			visCards[1].setStatus(Card.Status.MISSING);
			
			if (player1Turn) {
				player1.incScore();
			} else {
				player2.incScore();
			}
		}

	};
	
}
