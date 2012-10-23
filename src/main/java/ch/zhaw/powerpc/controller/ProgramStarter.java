package ch.zhaw.powerpc.controller;

//import java.io.BufferedReader;
//import java.io.FileReader;
//import java.io.IOException;

import java.io.IOException;

import ch.zhaw.powerpc.model.ControlUnit;
import ch.zhaw.powerpc.model.MainMemory;
import ch.zhaw.powerpc.view.impl.ConsolePrinter;

/**
 * Einstiegspunkt für das komplette Programm.
 * 
 * @author Max / Reto / Des
 * 
 * 
 *
 */

public class ProgramStarter {
	

	private static String currentInput = "/home/des/git/pocketpc/ppcTestCode.txt";
	
	public static void main(String[] args) throws IOException {
		// read initial memory
		
		InputReader reader = new InputReader(currentInput);
		InputInterpreter interpreter = new InputInterpreter();
		
		String[] stringInput = reader.OpenFile();
		MainMemory ppcMainMemory = interpreter.generateMainMemory(stringInput);
		ControlUnit ppcControlUnit = new ControlUnit(ppcMainMemory);
		Clock ppcClock = new Clock(ppcControlUnit, new ConsolePrinter());
		


		
	}
	
}
