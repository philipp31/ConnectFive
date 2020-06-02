import java.util.Scanner;
import java.io.File;
import java.io.IOException;

/**
 * main program which allows it to play the Game "Connect Five" with a Friend.
 *
 * @author Philipp, Slebioda, 4809007
 */

public class ConnectFive { 

	public static void main(String[] args) {
		
		Scanner objScanner = new Scanner(System.in);
		int position = 0;
		String command = "initial";
		int evaluierung = 0;					//evaluierung speichert ob einer gewonnen hat(1/-1) bzw. es weiter geht 0
		Spielfeld fieldObj = new Spielfeld();
		char[][] writingData = new char[7][9];
		
		firstInformation();			// print the information how the game works and what is expected
		
		do {
			try {
				fieldObj.printPlayInformation();		    // Ausgabe welcher Spieler an der reihe ist und, dass er seinen Zug angeben soll			
			} catch( ArrayIndexOutOfBoundsException e) {	// catching an possible OutOfBoundsException
				System.out.println("Array Index Out Of Bound!");
			}
			if(objScanner.hasNextInt()) {					// bei einem int als Eingabe soll das spiel weitergehen
				System.out.println("");
				position = objScanner.nextInt();
				try {
					evaluierung = fieldObj.drawField(position);		// drawField() returnt 1/-1 für gewinn, es wird 0 zum weiterspielen returnt
				} catch( ArrayIndexOutOfBoundsException e) {	    // catching an possible OutOfBoundsException
					System.out.println("Array Index Out Of Bound!");
				}
			}
			else {
				command = objScanner.next();
			}
		} while(!command.equals("exit") && evaluierung == 0);			// durch exit oder eine -1/1 wird der Spiel-Loop unterbrochen
		
		writingData = fieldObj.getFieldData();					// extracting the FieldData
		try {
			ConnectOutputStream outstream = new ConnectOutputStream("Connect-Five-Backup.txt");	// constructor creates the file if it doesnt exists
			outstream.dataInformation();		// giving information about the data in the backup-File
			outstream.newLine();
			for( int i = 0; i < 7; i++) {
				for( int k = 0; k < 9; k++) {
					outstream.writeData(writingData[i][k]);		// writing the actual data
				}
				outstream.newLine();
				outstream.seperation();
			}
			outstream.close();
		} catch(IOException e) {
			System.out.println("IOException appeared!");
		}
		
		
		switch(evaluierung) {						// if the while loop is finished the Winner get printed 
			case -1:
				System.out.println("Spieler 2 hat gewonnen (fuenf '0en')");
				System.out.println("Spiel ist vorbei");
				break;
			case 1:
				System.out.println("Spieler 1 hat gewonnen (fuenf 'X')");
				System.out.println("Spiel ist vorbei");
				break;
			default:
				System.out.println("Sie haben das Spiel verlassen.");
		}
	}
	
	
	
	public static void firstInformation() {
		System.out.println("");
		System.out.println("Herzlich Willkommen beim Spiel Fuenf gewinnt!");
		System.out.println("Es gibt zwei Spieler und sie koennen eine Spalte von 0 bis 8 eingeben um einen Zug durchzufuehren.");
		System.out.println("Wenn Sie das Spiel beenden wollen koennen Sie 'exit' eingeben.");
		System.out.println("");
	}
}