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
	
	String stringMemory[];
	// Ask for file
	String inputFile;
	int numberOfLines; // Including comment lines
	int numberOfCodeLines; // Excludes comment lines
	int numberOfMainMemoryIntegers; 
	
	static int memoryPointer;
	static int initialData[];
	static ProgramStarter programStarter;
	
	static MainMemory ppcMainMemory;
	static ControlUnit ppcControlUnit;
	static Clock ppcClock;

	public static void main(String[] args) {
		// read initial memory
		String currentInput = "/home/des/git/pocketpc/ppcTestCode.txt";
		programStarter = new ProgramStarter(currentInput);
		memoryPointer = 100;
		
		programStarter.trimStringMemory();
		programStarter.outputStringMemory();
		
		programStarter.initializeInitialData();
		programStarter.stringMemory2MainMemory();
		programStarter.outputMainMemory();
		
		// intialize control unit
		ppcMainMemory = new MainMemory(initialData);
		ppcControlUnit = new ControlUnit(ppcMainMemory);
		ppcClock = new Clock(ppcControlUnit);
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
	
	/*
	 * Konstruktor ProgramStarter, aufrufen des zeilenweisen einlesens
	 */
		
	public ProgramStarter(String inputFile) {
		
		this.inputFile = inputFile;
		try {
			stringMemory = OpenFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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
	 * Trimmen des Input Files, so dass keine Leerzeichen mehr vorhanden sind vor dem Code
	 */
	
	public boolean trimStringMemory() {
		String code;
		
		for (int i = 0; i < numberOfCodeLines; i++) {
			code = stringMemory[i].trim();
			stringMemory[i] = code;
			
			if (stringMemory[i].length() < 16)
				return false;
			
			code = stringMemory[i].substring(0, 16);
			stringMemory[i] = code;
		}
		
		return true;
	}
	
	/*
	 * Vorbereitung der Übergabe an InitialData:
	 * - Prüfen ob Anz Eingabezeilen ungerade oder gerade um Array korrekt anzulegen
	 */
	
	public boolean initializeInitialData() {
		if (numberOfCodeLines % 2 == 0) {
			numberOfMainMemoryIntegers = numberOfCodeLines/2;
		}
		else {
			numberOfMainMemoryIntegers = numberOfCodeLines/2 + 1; 
		}
		
		if (numberOfMainMemoryIntegers <= 0) {
			return false;
		}
		else {
			initialData = new int[numberOfMainMemoryIntegers];
			return true;
		}
	}
	
	/*
	 * Eingabe Memory umwandeln in Übergabe Memory
	 * - Immer 1. und 2. Zeile zusammen zählen und an korrekte 
	 * Speicherstelle im initialData Array speichern
	 */
	
	public boolean stringMemory2MainMemory() {
		int codeHigher;
		int codeLower;
		int initialDataIndex;
		
		codeHigher = 0;
		codeLower = 0;
		initialDataIndex = 0;
		
		for(int i = 0; i < numberOfCodeLines; i++) {	
			if (i % 2 == 0) {
				codeHigher = Integer.parseInt(stringMemory[i], 2);
				codeHigher = codeHigher << 16;
			}
			else {
				codeLower = Integer.parseInt(stringMemory[i], 2);
			}
			
			initialDataIndex = i/2;
			
			initialData[initialDataIndex] = codeHigher + codeLower;
		}
		
		if (numberOfCodeLines % 2 == 1) {
			initialData[initialDataIndex] = codeHigher;
		}
		
		return true;
	}
	
}
