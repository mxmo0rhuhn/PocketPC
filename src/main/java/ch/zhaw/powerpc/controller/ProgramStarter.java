package ch.zhaw.powerpc.controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Einstiegspunkt für das komplette Programm.
 * 
 * @author Max / Reto / Des
 * 
 * 
 *
 */

public class ProgramStarter {
	
	String memory[];
	// Ask for file
	String inputFile;

	public static void main(String[] args) {
		// read initial memory
		// intialize control unit
		
		String currentInput = "/home/des/input.txt";
		
	//	new ControlUnit();
		
		new ProgramStarter(currentInput);
		
	}
		
	public ProgramStarter(String inputFile) {
		
		this.inputFile = inputFile;
		try {
			memory = OpenFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	/**
	 * @return txtData 
	 * 					(Text aus dem InputFile)
	 * @throws IOException
	 */
	public String[] OpenFile() throws IOException{
		FileReader fr = new FileReader(inputFile);
		BufferedReader txtReader = new BufferedReader(fr);
		
		int numberOfLines = readLines();
		String[] txtData = new String[numberOfLines];
		int i;
		for (i=0; i< numberOfLines; i++) {
			txtData[i] = txtReader.readLine();
		}
		
		txtReader.close();
		return txtData;
		
	}
	
	
	
	int readLines() throws IOException {
		
		FileReader file2Input = new FileReader(inputFile);
		BufferedReader bf = new BufferedReader(file2Input);
		
		@SuppressWarnings("unused")
		String begLine;
		int amountLines = 0;
		
		while ((begLine = bf.readLine()) != null) {
			amountLines++;
		}
		bf.close();
		
		return amountLines;
	}
	
		
		
	
}
