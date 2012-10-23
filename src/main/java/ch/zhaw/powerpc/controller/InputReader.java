package ch.zhaw.powerpc.controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import ch.zhaw.powerpc.model.ControlUnit;
import ch.zhaw.powerpc.model.MainMemory;

//import ch.zhaw.powerpc.controller.InputInterpreter;

public class InputReader {
	
	int numberOfLines; // Including comment lines
	int numberOfCodeLines; // Excludes comment lines
	String inputFile;
	String stringMemory[];
	
	int numberOfMainMemoryIntegers; 
	
	static int memoryPointer;
	static int initialData[];
	static InputReader programStarter;
	
	static MainMemory ppcMainMemory;
	static ControlUnit ppcControlUnit;
	static Clock ppcClock;
	
	public InputReader(String inputFile) {
		
		this.inputFile = inputFile;
		try {
			stringMemory = OpenFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		/*InputInterpreter(stringMemory);
		starter.InputInterpreter().trimStringMemory();
		
		starter.InputInterpreter().initializeInitialData();
		starter.InputInterpreter().stringMemory2MainMemory();
		
		*/
		
		
	}
	

	/**
	 * @return txtData 
	 * 					(Text aus dem InputFile)
	 * @throws IOException
	 * 
	 * Einlesen des Files
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
	
	/*
	 *  bestimmen der Grösse des Arrays
	 */
	
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

	/*
	 * Test Output des eingelesenen Files
	 */
	
	public void outputStringMemory() {
		System.out.println("\nEingelesenes File:");
		for (int i = 0; i < numberOfCodeLines; i++) {
			System.out.println(stringMemory[i]);
		}
		
	}
	
	/*
	 * Test Output der Ausgabe an ControlUnit in Integer und als Binary
	 */
	
	public void outputMainMemory() {
		System.out.println("\nMainMemory als Integers:");
		for (int i = 0; i < numberOfMainMemoryIntegers; i++) {
			System.out.println(initialData[i]);
		}
		System.out.println("\nMainMemory als binary Integers (2 Codes per Integer):");
		for (int i = 0; i < numberOfMainMemoryIntegers; i++) {
			System.out.println("#"+memoryPointer +": " +Integer.toBinaryString(initialData[i]));
			memoryPointer = memoryPointer + 2;
		}
	}


}
