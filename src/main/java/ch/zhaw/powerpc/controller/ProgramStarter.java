package ch.zhaw.powerpc.controller;


import java.io.File;
import java.io.IOException;

import ch.zhaw.powerpc.model.instructions.Instruction;
import ch.zhaw.powerpc.model.instructions.InvalidInstructionException;

/**
 * Einstiegspunkt für das komplette Programm.
 * 
 * @author Max / Reto / Des
 * 
 */
public class ProgramStarter {

	public static void main(String[] args) throws IOException {
		String filename = getFilename();
		InputReader reader = new InputReader(filename);
		String[] mnemonics = reader.readContents();
		Assembler asm = new Assembler();
		Instruction[] instructions = new Instruction[mnemonics.length];
		for (int i = 0; i < mnemonics.length; i++) {
			try {
				instructions[i] = asm.assemble(mnemonics[i]);
			} catch (InvalidInstructionException iie) {
				System.err.println("Instruktion Nr. " + i + " kann nicht assembliert werden: " + iie.getMessage());
				System.exit(-1);
			}
		}
		// MainMemory ppcMainMemory = interpreter.generateMainMemory(stringInput);
		// ControlUnit ppcControlUnit = new ControlUnit(ppcMainMemory);
		// Clock ppcClock = new Clock(ppcControlUnit, new ConsolePrinter());
		// ppcClock.startSlowMode();
	}

	private static String getFilename() throws IOException {
		String asmFile = System.getProperty("asm");
		if (asmFile != null && new File(asmFile).canRead()) {
			return asmFile;
		} else {
			System.err.println("Etwas stimmt mit der Eingabe nicht:");
			if (asmFile == null) {
				System.err.println("- Es wurde kein File angegeben.");
			} else {
				System.err.println("- Das File ist nicht lesbar/existiert nicht: " + asmFile);
			}
			System.err.println("\n Das Programm muss folgendermassen gestartet werden:");
			System.err.println("\tjava " + ProgramStarter.class.getClass().getCanonicalName()
					+ " -Dasm=/home/usr/input.asm");
			System.exit(-1);
			return null; // never happens
		}
	}

}
