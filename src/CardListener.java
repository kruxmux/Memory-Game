import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CardListener implements ActionListener {
	
	private Memory mem;
	private Card c;
	
	public CardListener(Memory mem, Card c) {
		this.mem = mem;
		this.c = c;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		/*
		 * Man kan inte klicka på korten om:
		 *  * två kort redan är vända, dvs timern är igång
		 *  * kortet redan är 'taget' och spelaren fått poäng för det
		 */
		if (!mem.timerIsActive() && c.getStatus() != Card.Status.MISSING) {
			if (c.getStatus() == Card.Status.HIDDEN) {
				c.setStatus(Card.Status.VISIBLE);
				mem.addVisibleCard(c);
				mem.checkVisibleCards();
			} 
		}
	}
}
