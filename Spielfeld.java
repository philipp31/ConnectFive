import java.util.Scanner;


/**
 *
 * provides a field for the game 'Connect Five' which allows it to implement the drawing, inserting and evaluating of the whole game
 *
 * @author Philipp, Slebioda, 4809007
 */

public class Spielfeld {
	// declaring the necessary data-types for the game:
	private char[][] Raum;
	private int spieler;
	private int position;
	private int[] aktuellePosition;
	
	/**
	 * constructor for Spielfeld, initialises the necessary data types, etc.
	 *
	 *
	 */
	public Spielfeld() {
		Raum = new char[7][9];
		aktuellePosition = new int[9];			// gibt für jede Spalte die erste freie Füllposition
		spieler = 0;							// spieler = 0 -> Spieler 1 || spieler=1 -> Spieler 2
		
		for(int i = 0;i < 7; i++) {				// Zeilen
			for(int t=0; t < 9;t++) {			// Spalten
				Raum[i][t] = ' ';
				aktuellePosition[t] = 6;
			}
		}
		position = 0;
	}

	/**
	 * allows it to draw a specific wanted line
	 *
	 * @param Zeile this is line you want to print
	 */
	public void drawLine(int Zeile) {
		System.out.println("-------------------");
		System.out.print("|" + Raum[Zeile][0] + "|" + Raum[Zeile][1]+ "|" + Raum[Zeile][2] + "|" + Raum[Zeile][3] + "|" + Raum[Zeile][4] + "|" + Raum[Zeile][5] +"|" + Raum[Zeile][6] +"|" + Raum[Zeile][7] + "|" + Raum[Zeile][8] +"|");
		System.out.println("");
	}
	
	/**
	 * this method allows it to print the Game instruction if the game isnt finished
	 *
	 */
	public void printPlayInformation(){
		//Überprüfen, ob Spiel schon vorbei ist:
		if (siegerErmitteln() == 1 || siegerErmitteln() == -1) {
			return ;										// Wenn es schon einen Sieger gibt, dann gib nicht den Text aus
		}
		else {
			if(spieler == 0) {
				System.out.println("Jetzt am Zug: Spieler 1 (--->hat 'X')");
			} else {
				System.out.println("Jetzt am Zug: Spieler 2 (--->hat '0')");
			}
		}
		System.out.print("Deine Eingabe (gewuenschte Spalte) lautet : ");
	}

	/**
	 * drawField(int ) method allows it to save the Turn of a Play and print the field
	 *
	 * @param position this parameter defines the new Turn the player wants to take
	 */
	 
	public int drawField(int position) {
		if(!saveTurn(position)) {		// Ist das Einfügen eines Zuges nicht möglich soll drawField() rausgegangen werden
			return siegerErmitteln();
		}
		switch(spieler) {
			case 0:
				spieler++;		// Nach dem durch saveTurn() der Zug gemacht wurde ist der andere Spieler dran
				break;
			case 1:
				spieler--;		// der nächste Spieler ist dran nach ausgabe des Textes und speichern des Zugess
				break;
		}
		for(int k = 0; k < 7 ; k++) {
			drawLine(k);
			if(k == 6) {
				System.out.println("-------------------");
			}
		}
		System.out.println("Spalten:");
		System.out.println("|" + 0 + "|" + 1 + "|" + 2 + "|" + 3 + "|" + 4 + "|" + 5 + "|" + 6 + "|" + 7 + "|" + 8 + "|" + "\n");
		return siegerErmitteln();
	}
	
	/**
	 * saveTurn(int ) method allows it to save a new element in the field
	 *
	 * @param Spalte this is the column in which the new element should be stored
	 */
	public boolean saveTurn(int Spalte) {
		if(Spalte > 8 || Spalte < 0) {
			System.out.println("ERROR: Geben Sie bitte eine Zahl von 0 bis 8 an!");
			return false;				// Returns false if there is a problem with the wanted column
		}
		else if(aktuellePosition[Spalte] < 0) {
			System.out.println("Die Spalte ist voll!!");
			return false;											// return false if the column is already full
		}
		switch(spieler) {
			case 0:
				Raum[aktuellePosition[Spalte]][Spalte] = 'X';		// spieler 1 hat das Zeichen X
				break;
			case 1:
				Raum[aktuellePosition[Spalte]][Spalte] = '0';		// spieler 2 hat das Zeichen 0
				break;
			default:
				System.out.println("Problem aufgetreten bei Speichern des Zuges!");
				break;
		}
		aktuellePosition[Spalte]--;
		return true;		// if the element got sucsessfully stored return true
	}
	
	/**
	 * the method siegerErmitteln() allows it to evaluate the winner or not if there isnt one!
	 *
	 */
	public int siegerErmitteln() {
		if(checkReihen() == 1 || checkSpalten() == 1 || checkQuer() == 1) {
			return 1;					// Spieler 1 gewinnt, wenn 1 returnt wird
		} 
		else if(checkReihen() == -1 || checkSpalten() == -1 || checkQuer() == -1) {
			return -1;					// Spieler 2 gewinnt, wenn -1 returnt wird
		}
		else {
			return 0;					// wenn 0 returnt wird geht es weiter(bis hier hat keiner gewonnen)
		}
	}
	
	
	/**
	 * the method checkReihen() allows it to check if there are five connected symbols in a row at the currect Field
	 *
	 */
	public int checkReihen() {
		int zeilenCounter = 0;
		int h = 0;
		// Check Reihen:
		for(int i = 0;i < 7; i++) {		// Zeilen
			for(int t=0; t < 9; t++) {	// Spalten
				if(Raum[i][t] == 'X' && t < 8) {
					zeilenCounter = 1;
					while(Raum[i][t+1+h] == 'X' && (t+1+h) < 8) {
						zeilenCounter++;
						h++;
						if(zeilenCounter == 5) {
							return 1;						// wurden fünf 'X' gezählt hat player 1 gewonnen
						}
					}
					h = 0;
					zeilenCounter = 0;
				}
				if(Raum[i][t] == '0' && (t) < 8) {
					zeilenCounter = 1;
					while(Raum[i][t+1+h] == '0' && (t+1+h) < 9) {
						zeilenCounter++;
						h++;
						if(zeilenCounter == 5) {
							return -1;						// wurden fünf '0' gezählt hat player 2 gewonnen
						}
					}
					h = 0;
					zeilenCounter = 0;
				}
			}
		}
	return 0;
	}
	
	/**
	 * the method checkSpalten() allows it to check if there are five connected symbols in a column at the currect Field
	 *
	 */
	public int checkSpalten() {
		
		// Check Spalten:
		for(int i = 0; i < 7; i++) {				// Zeilen
			for(int t = 0; t < 9; t++) {			// Spalten
			
				if(Raum[i][t] == 'X' && (i+1) < 7) {
					if(Raum[i+1][t] == 'X' && (i+2) < 7) {
						if(Raum[i+2][t] == 'X' && (i+3) < 7) {
							if(Raum[i+3][t] == 'X' && (i+4) < 7) {
								if(Raum[i+4][t] == 'X' ) {
									return 1;			// Player 1 gewinnt bei 5 mal 'X' -> return 1
								}
							}
						}
					}
				}
				
				if(Raum[i][t] == '0' && (i+1) < 7) {
					if(Raum[i+1][t] == '0' && (i+2) < 7) {
						if(Raum[i+2][t] == '0' && (i+3) < 7) {
							if(Raum[i+3][t] == '0' && (i+4) < 7) {
								if(Raum[i+4][t] == '0' ) {
									return -1;			// Player 2 gewinnt bei 5 mal '0' -> return 1
								}
							}
						}
					}
				}
				
			}
		}
	return 0;
	}
	
	/**
	 * the method checkQuer() allows it to check if there are five connected symbols crosswise
	 *
	 */
	public int checkQuer(){
		for(int i = 0; i < 7; i++) {				// Zeilen
			for(int t = 0; t < 9; t++) {			// Spalten
				// QUER NACH OBEN FÜR 'X'
				if( Raum[i][t] == 'X' && i < 6 && t < 8) {
					if( Raum[i+1][t+1] == 'X' && i < 5 && t < 7) {
						if( Raum[i+2][t+2] == 'X' && i < 4 && t < 6) {
							if( Raum[i+3][t+3] == 'X' && i < 3 && t < 5) {
								if( Raum[i+4][t+4] == 'X') {		// Sobald 5 mal 'X' Quer wahr ist hat player 1 gewonnen
									return 1;
								}
							}
						}
					}
				}
				// QUER NACH UNTEN FÜR 'X'
				if( Raum[i][t] == 'X' && i > 0 && t < 8 ) {
					if( Raum[i-1][t+1] == 'X' && i > 1 && t < 7) {
						if( Raum[i-2][t+2] == 'X' && i > 2 && t < 6) {
							if( Raum[i-3][t+3] == 'X' && i > 3 && t < 5) {
								if( Raum[i-4][t+4] == 'X') {		// Sobald 5 mal 'X' Quer wahr ist hat player 1 gewonnen
									return 1;
								}
							}
						}
					}
				}
				// QUER NACH OBEN FÜR '0'
				if( Raum[i][t] == '0'  && i < 6 && t < 8) {
					if( Raum[i+1][t+1] == '0'  && i < 5 && t < 7) {
						if( Raum[i+2][t+2] == '0'  && i < 4 && t < 6) {
							if( Raum[i+3][t+3] == '0'  && i < 3 && t < 5) {
								if( Raum[i+4][t+4] == '0') {		// Sobald 5 mal '0' Quer wahr ist hat player 2 gewonnen
									return -1;
								}
							}
						}
					}
				}
				// QUER NACH UNTEN FÜR '0'
				if( Raum[i][t] == '0' && i > 0 && t < 8) {
					if( Raum[i-1][t+1] == '0' && i > 1 && t < 7) {
						if( Raum[i-2][t+2] == '0' && i > 2 && t < 6) {
							if( Raum[i-3][t+3] == '0' && i > 3 && t < 5) {
								if( Raum[i-4][t+4] == '0') {		// Sobald 5 mal '0' Quer wahr ist hat player 2 gewonnen
									return -1;
								}
							}
						}
					}
				}
				
			}
		}
	return 0;
	}	
	
	public char[][] getFieldData() {
		return Raum;
	}
}
