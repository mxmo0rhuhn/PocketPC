package ch.zhaw.powerpc.controller;

import ch.zhaw.powerpc.model.MainMemory;

public class InputInterpreter {
	
	int numberOfLines; // Including comment lines
	int numberOfCodeLines; // Excludes comment lines
	String inputFile;
	String stringMemory[];
	
	int numberOfMainMemoryIntegers; 
	
	static int memoryPointer;
	static int initialData[];
	static InputReader programStarter;
	
	public InputInterpreter() {
		
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


	public MainMemory generateMainMemory(String[] stringInput) {
		// TODO Auto-generated method stub
		return null;
	}


}
