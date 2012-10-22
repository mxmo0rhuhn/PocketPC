package ch.zhaw.powerpc.view;

import ch.zhaw.powerpc.model.ALU;
import ch.zhaw.powerpc.model.ControlUnit;
import ch.zhaw.powerpc.model.Register;

/**
 * 
 * @author Reto
 * 
 */
public class ConsolePrinter implements Printer {
	
	private static final char NEWLINE = '\n';

	/**
	 * Ich glaube, die Ausgabe muesste jedes mal +/- 10 die gleiche Anzahl Zeichen haben. Deshalb waere es sinnvoll
	 * diese Zahl vorzumerken. Wuerde dann auch ohne berechnen gehen..
	 */
	private int sblen = 42;

	@Override
	public void print(ControlUnit controlUnit) {
		ALU alu = controlUnit.getAlu();
		Register[] registers = controlUnit.getRegisters();
		StringBuilder sb = new StringBuilder(sblen);
		
		sb.append("PowerPC by Team SSH"); // Sacher Schrimpf Hablützel (muahaha)
		sb.append("Befehlszähler: ").append(controlUnit.getProgramCounter()).append(NEWLINE);
		
		sb.append("Akku: ").append(registers[0].read());
		sb.append(" Carry: ").append(alu.isCarryFlag() ? '1' : '0').append(NEWLINE);
		
		sb.append("Reg-1: ").append(registers[1].read());
		sb.append(" Reg-2").append(registers[2].read());
		sb.append(" Reg-3").append(registers[3].read());
		
		// TODO binär
		
		// TODO mnemonics

		sblen = sb.length();
		System.out.println(sb.toString());
	}

}
