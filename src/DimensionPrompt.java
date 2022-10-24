import javax.swing.JOptionPane;

public class DimensionPrompt {
	
	private Memory mem;

	// Eftersom vi vill ha olika felmeddelanden beroende på vad användaren
	// gjorde fel vill behöver vi ha flera variabler.
	private boolean validInput;
	private boolean wrongFormat;
	private boolean oddCardAmount;
	private boolean tooManyCards;
	
	private boolean startupPrompt;
	private int rows;
	private int cols;
	
	public DimensionPrompt(Memory mem) {
		this.mem = mem;
		
		validInput = false;
		wrongFormat = false;
		oddCardAmount = false;
		tooManyCards = false;
		
		rows = 0;
		cols = 0;
		
		startupPrompt = false;
		prompt();
	}
	
	public DimensionPrompt(Memory mem, boolean startupPrompt) {
		this.mem = mem;
		
		validInput = false;
		wrongFormat = false;
		oddCardAmount = false;
		tooManyCards = false;
		
		rows = 0;
		cols = 0;
		
		this.startupPrompt = startupPrompt;
		prompt();
	}
	
	public void prompt() {
		String s = null;
		while (!validInput) {
			s = (String) JOptionPane.showInputDialog(mem, "Ange dimensioner för spelet:\n" + "(rader x kolumner)",
											"Inställningar - Memory", JOptionPane.PLAIN_MESSAGE, null, null, "4x4");
			
			// Om man trycker 'Cancel' vill vi avsluta programmet
			if (s == null && startupPrompt) {
				System.exit(0);
				break;
			} else if (s == null) {
				break;
			}
			
			// Ta bort mellanslag utifall att användaren skriver tex. "4 x 4" istället för "4x4".
			s = s.replaceAll(" ", "");
			parseString(s);
			
			if (!validInput) {
				errorMessage();
			}
		}
		// Ute ur loopen => användaren lyckades skriva in en korrekt sträng och vi kan nu starta spelet.
		mem.setRows(rows);
		mem.setCols(cols);
	}

	private void errorMessage() {
		String errorMessage = "";
		
		if (wrongFormat) {
			errorMessage = "Dimensioner måste anges på formen r x k!";
		} else if (oddCardAmount) {
			errorMessage = "r x k måste resultera i jämnt antal kort!";
		} else if (tooManyCards) {
			errorMessage = "Det finns inte tillräckligt med bilder för " + rows + "x" + cols + "!"
					+ " Max " + mem.getIcons().length * 2 + " bilder";
		}
		
		JOptionPane.showMessageDialog(mem, errorMessage);
	}
	
	// Kolla om strängen är giltig
	private void parseString(String s) {
		if (s.matches("[1-9]+x[1-9]+")) {
			String[] splitted = s.split("x");
			rows = Integer.parseInt(splitted[0]);
			cols = Integer.parseInt(splitted[1]);
			
			if ((rows * cols) % 2 != 0) {
				oddCardAmount = true;	// I memory måste det vara ett jämnt antal kort
			} else if (rows * cols > mem.getIcons().length * 2) {
				tooManyCards = true; 	// Vi kan inte ha fler kort än tillgängliga bilder
			} else {
				validInput = true;
			}
		} else {
			wrongFormat = true;
		}
	}
}
