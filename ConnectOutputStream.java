import java.io.IOException;
import java.io.OutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.nio.ByteBuffer;
import java.io.PrintWriter;

public class ConnectOutputStream extends OutputStream {

	// declaring necessary data-types:
	private FileOutputStream outstream;
	private File fileObj;
	private PrintWriter pw;
	
	public ConnectOutputStream(String dateiName) throws IOException {
		fileObj = new File(dateiName);		// Instanziieren des File-objekts
		if(fileObj.exists()) {				// wenn bereits eine file da ist löschen
			fileObj.delete();				// die alte datei wird gelöscht, wenn eine da war
		}
		fileObj.createNewFile();			// createNewFile würde, wenn im Verzeichnis keine Datei mit Name existiert diese erstellen
		outstream = new FileOutputStream(fileObj);	// nun kann unser fileoutputstream instanziiert werden mit dem fileobject
		pw = new PrintWriter(outstream);
	}
	
	public void writeData(char k) throws IOException {
				pw.print(k);	// Durch ein PrintWriter-Objekt lassen sich einfach auch strings,int,etc. über FileOutputStream schreiben
				pw.print("|");
	}
	
	public void newLine() throws IOException {
		pw.println("");
	}
	
	public void seperation() throws IOException {
		pw.println("-------------------");
	}
	
	public void dataInformation() throws IOException {
		pw.print("This data represents the end of the last game: ");
	}
	
	@Override
	public void close() throws IOException{
		pw.close();			// zusätzlich auch schließen des PrintWriters
		outstream.close();	// schließen des Fileoutputstreams über close()
	}
	
	@Override
	public void write(int arg0) throws IOException {	//can be ignored
		// Kann ignoriert werden
	}
	
}