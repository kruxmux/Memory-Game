import java.awt.Color;

import javax.swing.Icon;
import javax.swing.JButton;

public class Card extends JButton {
	
	// Hidden  => Kortet är ännu inte taget
	// Visible => Någon av spelarna har vänt på kortet
	// Missing => Någon har hittat paret och en spelare har fått poäng
	public static enum Status {
		HIDDEN, VISIBLE, MISSING
	}
	// Sidlängden för ett kort i px
	public static final int SIDE_LENGTH = 115;
	
	private Icon pic;
	private Status status;
	
	public Card(Icon pic) {
		this.pic = pic;
		setOpaque(true);
		setStatus(Status.MISSING);
	}
	
	public Card(Icon pic, Status status) {
		this.pic = pic;
		setOpaque(true);
		setStatus(status);
	}
	
	public boolean sameIcon(Card c) {
		return pic.equals(c.pic);
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;

		// Byter till rätt icon beroende på status
		// setIcon = null => visa bakgrund (enfärgat)
		if (status == Status.HIDDEN) {
			setBackground(Color.BLUE);
			setIcon(null);
		} else if (status == Status.VISIBLE) {
			setBackground(Color.WHITE);
			setIcon(pic);
		} else {
			setBackground(Color.WHITE);
			setIcon(null);
		}
	}
}
