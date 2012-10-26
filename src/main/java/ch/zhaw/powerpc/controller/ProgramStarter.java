package ch.zhaw.powerpc.controller;

import java.io.File;
import java.io.IOException;

import ch.zhaw.powerpc.model.ControlUnit;
import ch.zhaw.powerpc.model.MainMemory;
import ch.zhaw.powerpc.model.instructions.InvalidInstructionException;
import ch.zhaw.powerpc.view.impl.ConsolePrinter;

/**
 * Einstiegspunkt für das komplette Programm.
 * 
 * @author Max / Reto / Des
 * 
 */
public class ProgramStarter {

	private static boolean debug = true;

	public static void main(String[] args) throws IOException {
		String filename = getFilename();
		InputReader reader = new InputReader(filename);
		String[] mnemonics = reader.readContents();
		MainMemory mainMemory = createMainMemory(mnemonics);
		ControlUnit ppcControlUnit = new ControlUnit(mainMemory);
		try {
			new Clock(ppcControlUnit, new ConsolePrinter()).step();
		} catch (Exception e) {
			if (debug) {
				debug(ppcControlUnit, e);
			}
		}
	}

	public static MainMemory createMainMemory(String[] mnemonics) {
		Assembler asm = new Assembler();
		MainMemory mainMemory = new MainMemory();
		int cnt = 100;
		for (String mnemonic : mnemonics) {
			if (mnemonic.contains("=")) {
				String[] parts = mnemonic.split("=");
				mainMemory.writeData(Short.parseShort(parts[0]), Short.parseShort(parts[1]));
			} else {
				try {
					mainMemory.setInstruction(cnt, asm.assemble(mnemonic));
					cnt += 2;
				} catch (InvalidInstructionException iie) {
					System.err.println("Instruktion Nr. " + (cnt - 100) + " kann nicht assembliert werden: "
							+ iie.getMessage());
					System.exit(-1);
				}
			}
		}
		return mainMemory;
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
			System.err.println("\tjava -Dasm=/home/usr/input.asm " + ProgramStarter.class.getCanonicalName());
			System.exit(-1);
			return null; // never happens
		}
	}

	private static void debug(ControlUnit cu, Exception e) {
		System.err.println("------------------------------------------------------------------");
		System.err.println("---------------------------ERROR----------------------------------");
		System.err.println("------------------------------------------------------------------");
		e.printStackTrace();
		System.err.println("------------------------------------------------------------------");
		System.err.println("----------------------------DEBUG---------------------------------");
		System.err.println("------------------------------------------------------------------");
		System.err.println(cu);
		System.err.println("------------------------------------------------------------------");
		System.err.println("---------------------------FINISH---------------------------------");
		System.err.println("------------------------------------------------------------------");
	}

}
