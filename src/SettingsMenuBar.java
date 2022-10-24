import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;

public class SettingsMenuBar extends JMenuBar {
	
	private Memory mem;
	private JMenu gameMenu;
	private JMenu settingsMenu;
	private JMenuItem soundToggleItem;
	
	public SettingsMenuBar(Memory mem) {
		this.mem = mem;
	
		addGameMenu();
		addSettingsMenu();
	}
	
	private void addGameMenu() {
		gameMenu = new JMenu("Spel");
		
		JMenuItem newGameItem = new JMenuItem("Nytt spel");
		newGameItem.addActionListener(settingsListener);
		newGameItem.setActionCommand("new_game");
		gameMenu.add(newGameItem);
		
		JMenuItem exitItem = new JMenuItem("Avsluta");
		exitItem.addActionListener(settingsListener);
		exitItem.setActionCommand("exit");
		gameMenu.add(exitItem);
		
		add(gameMenu);
	}
	
	private void addSettingsMenu() {
		settingsMenu = new JMenu("Inställningar");
		
		JMenuItem dimensionItem = new JMenuItem("Dimensioner");
		dimensionItem.addActionListener(settingsListener);
		dimensionItem.setActionCommand("test");
		settingsMenu.add(dimensionItem);
	
		JMenuItem timeItem = new JMenuItem("Tid");
		
		timeItem.addActionListener (
				e ->  {
					String s = (String) JOptionPane.showInputDialog(mem, "Ange tid för hur länge bilder visas (ms)",
							"Tidinställningar", JOptionPane.PLAIN_MESSAGE, null, null, "1500");
					try {
						mem.setTimerDelay(Integer.parseInt(s));
					} catch (Exception ex){};
				}
		);
		
		settingsMenu.add(timeItem);
		
		soundToggleItem = new JRadioButtonMenuItem("Sound", true);
		settingsMenu.add(soundToggleItem);
		
		add(settingsMenu);
	}
	
	public boolean soundOn() {
		return soundToggleItem.isSelected();
	}
	
	private ActionListener settingsListener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand().equals("new_game") && !mem.timerIsActive()) {
				mem.newGame();
				mem.resetBoard();
			} else if (e.getActionCommand().equals("exit")) {
				System.exit(0);
			} else if (e.getActionCommand().equals("test")) {
				new DimensionPrompt(mem);
			}
		}
	};
}
