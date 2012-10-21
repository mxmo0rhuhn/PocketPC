package ch.zhaw.powerpc.controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import ch.zhaw.powerpc.model.ControlUnit;
import ch.zhaw.powerpc.model.MainMemory;

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
	int numberOfLines; // Including comment lines
	int numberOfCodeLines; // Excludes comment lines
	static int initialData[];
	static ProgramStarter programStarter;
	
	static MainMemory ppcMainMemory;
	static ControlUnit ppcControlUnit;

	public static void main(String[] args) {
		// read initial memory
		String currentInput = "/home/des/input.txt";
		programStarter = new ProgramStarter(currentInput);
		programStarter.String2IntParser();
		programStarter.outputMemory();
		
		// intialize control unit
		ppcMainMemory = new MainMemory(initialData);
		ppcControlUnit = new ControlUnit(ppcMainMemory);

		
	//	new ControlUnit();
		

		
	}
	
	public void outputMemory() {
		for (int i = 0; i < numberOfCodeLines; i++) {
			System.out.println(memory[i]);
		}
		
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
		
		readLines();
		String[] txtData = new String[numberOfCodeLines];
		
		int i;
		String line;
		int firstNonSpaceIndex;
		boolean firstNonSpaceFound;
		int currentCodeLine = 0;
		
		
		for (i=0; i< numberOfLines; i++) {
			firstNonSpaceIndex = 0;
			firstNonSpaceFound = false;
			
			line = txtReader.readLine();
			for (int j = 0; j < line.length() && firstNonSpaceFound == false; j++) {
				firstNonSpaceIndex = j;
				if (line.charAt(j) == ' ') {
					continue;
				}
				firstNonSpaceFound = true;
			}
			if (firstNonSpaceFound == false){
				continue;
			}
			else {
				if (line.charAt(firstNonSpaceIndex) == '\'') {
					continue;
				}
				else {
					txtData[currentCodeLine] = line;
					currentCodeLine++;
				}		
			}	
		}
		
		txtReader.close();
		return txtData;
		
	}
	
	void readLines() throws IOException {
		
		FileReader file2Input = new FileReader(inputFile);
		BufferedReader bf = new BufferedReader(file2Input);
		

		String begLine;
		int firstNonSpaceIndex;
		boolean firstNonSpaceFound;
		numberOfCodeLines = 0;
		numberOfLines = 0;
		
		while ((begLine = bf.readLine()) != null) {
			
			firstNonSpaceIndex = 0;
			firstNonSpaceFound = false;
			
			for (int i = 0; i < begLine.length() && firstNonSpaceFound == false; i++) {
				firstNonSpaceIndex = i;
				if (begLine.charAt(i) == ' ') {
					continue;
				}
				firstNonSpaceFound = true;
			}
			if (firstNonSpaceFound == false) {
				numberOfLines++;
				continue;
			}
			else {
				if (begLine.charAt(firstNonSpaceIndex) == '\'') {
					numberOfLines++;
					continue;
				}
				else {
					numberOfCodeLines++;
					numberOfLines++;
				}
				
			}
		}
		bf.close();
		
	}
	
	public boolean String2IntParser() {
		return true;
	}
	
}
