package ch.zhaw.powerpc.controller;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

/**
 * Lesen des Input files (nur relevante Zeilen und passagen)
 * 
 * @author des
 * 
 */
public class InputReader {

	private final BufferedReader inputBuffer;

	public InputReader(String inputFile) throws FileNotFoundException {
		FileReader fr = new FileReader(inputFile);
		inputBuffer = new BufferedReader(fr);
	}

	public InputReader(BufferedReader inputBuffer) {
		this.inputBuffer = inputBuffer;
	}

	/**
	 * Einlesen des Files
	 * 
	 * @return txtData (Text aus dem InputFile)
	 * 
	 * @throws IOException
	 */
	public String[] readContents() throws IOException {
		LinkedList<String> liste = new LinkedList<String>();
		String line = this.inputBuffer.readLine();
		while (line != null) {
			int apostrophindex = line.indexOf('\'');
			// Kommentar entfernen, 'line' hatte Kommentar enthalten 
			if (apostrophindex > 0) {
				line = line.substring(0, apostrophindex);
			}
			if (apostrophindex == 0) {
				line = this.inputBuffer.readLine();
				continue;
			}
			// Leerzeilen rauslöschen
			line = line.trim();
			if (!line.isEmpty()) {
				liste.add(line);
			}
			// gehe zur nächsten Zeile
			line = this.inputBuffer.readLine();
		}
		this.inputBuffer.close();
		return liste.toArray(new String[0]);
	}
}