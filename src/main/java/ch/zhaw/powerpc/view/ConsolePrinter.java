package ch.zhaw.powerpc.view;

import ch.zhaw.powerpc.model.ALU;
import ch.zhaw.powerpc.model.ControlUnit;
import ch.zhaw.powerpc.model.MainMemory;
import ch.zhaw.powerpc.model.Register;

/**
 * 
 * @author Reto
 * 
 */
public class ConsolePrinter implements Printer {

	private static final char NEWLINE = '\n';

	/**
	 * Der kleinste Wert, der vom Program Counter jemals erreicht werden kann.
	 */
	private static final int MIN_CNT = 100;

	/**
	 * Der groesste Wert, der vom Program Counter jemals erreicht werden kann (zumindest sofern er nicht absichtlich in
	 * den Bereich der Daten geht, obwohl das moeglich waere.)
	 */
	private static final int MAX_CNT = 499;

	/**
	 * Vor dem Counter geben wir max 5 Befehle aus
	 */
	private static final int BEFORE_CNT = 5;

	/**
	 * Nach dem Counter geben wir max 10 Befehle aus
	 */
	private static final int AFTER_CNT = 10;

	/**
	 * Ich glaube, die Ausgabe muesste jedes mal +/- 10 die gleiche Anzahl Zeichen haben. Deshalb waere es sinnvoll
	 * diese Zahl vorzumerken. Wuerde dann auch ohne berechnen gehen..
	 */
	private int sblen = 42;

	@Override
	public void print(ControlUnit controlUnit) {
		final ALU alu = controlUnit.getAlu();
		final Register[] registers = controlUnit.getRegisters();
		final int cnt = controlUnit.getProgramCounter();
		final MainMemory memory = controlUnit.getMemory();

		final StringBuilder sb = new StringBuilder(sblen);

		sb.append("PowerPC by Team SSH").append(NEWLINE); // Sacher Schrimpf Hablützel (muahaha)

		appendMeta(alu, registers, cnt, sb);
		appendRegisters(registers, sb);
		appendInstructions(cnt, memory, sb);
		appendMemory(memory, sb);
		
		

		// TODO binär

		// TODO mnemonics

		sblen = sb.length();
		System.out.println(sb.toString());
	}

	private void appendMeta(final ALU alu, final Register[] registers, final int cnt, final StringBuilder sb) {
		sb.append("Befehlszähler: ").append(cnt);
		sb.append(" Akku: ").append(registers[0].read());
		sb.append(" Carry: ").append(alu.isCarryFlag() ? '1' : '0').append(NEWLINE);
	}

	private void appendRegisters(final Register[] registers, final StringBuilder sb) {
		sb.append("Register: Reg-1: ").append(registers[1].read());
		sb.append(" Reg-2").append(registers[2].read());
		sb.append(" Reg-3").append(registers[3].read());
		sb.append(NEWLINE);
	}

	private void appendInstructions(final int cnt, final MainMemory memory, final StringBuilder sb) {
		final int start = Math.max(MIN_CNT, cnt - BEFORE_CNT); // Wenn der cnt bei 101 ist, wollen wir nur 101 ausgeben
		final int end = Math.min(MAX_CNT, cnt + AFTER_CNT);
		sb.append("Befehle:"); // Erster Schleifendurchlauf produziert leerzeichen
		for(int i = start; i < cnt; i++) {
			sb.append(' ').append(memory.read(i));
		}
		sb.append(" [").append(memory.read(cnt)).append(']');
		for(int i = cnt+1; i <= end; i++) {
			sb.append(' ').append(memory.read(i));
		}
		sb.append(NEWLINE);
	}
	
	private void appendMemory(final MainMemory memory, final StringBuilder sb) {
		sb.append("Speicher:"); // Erster Schleifendurchlauf produziert leerzeichen
		for(int i = 500; i <= 529; i++) {
			sb.append(' ').append(memory.read(i));
		}
		sb.append(NEWLINE);
	}
}
