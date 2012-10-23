package ch.zhaw.powerpc.controller;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

/**
 * Lese
 * 
 * @author des
 * 
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
	 * @return txtData (Text aus dem InputFile)
	 * @throws IOException
	 * 
	 *             Einlesen des Files
	 */
	public String[] OpenFile() throws IOException {

		LinkedList<String> liste = new LinkedList<String>();

		String line = this.inputBuffer.readLine();

		while (line != null) {

			int apostrophindex = line.indexOf('\'');

			// Kommentar entfernen
			if (apostrophindex != -1) {
				line = line.substring(0, apostrophindex - 1);
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
