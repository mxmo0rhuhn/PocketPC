package ch.zhaw.powerpc.view.impl;

import ch.zhaw.powerpc.model.ALU;
import ch.zhaw.powerpc.model.ControlUnit;
import ch.zhaw.powerpc.model.MainMemory;
import ch.zhaw.powerpc.model.Register;
import ch.zhaw.powerpc.model.instructions.Instruction;
import ch.zhaw.powerpc.view.Formatter;
import ch.zhaw.powerpc.view.Printer;

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
	private static final int MAX_CNT = 498;

	/**
	 * Vor dem Counter geben wir max 5 Befehle aus
	 */
	private static final int BEFORE_CNT = 10;

	/**
	 * Nach dem Counter geben wir max 10 Befehle aus
	 */
	private static final int AFTER_CNT = 20;

	/**
	 * Wie viele Instruktionen passen auf eine Linie? Je nach Bildschirm..
	 */
	private static final int BLOCKS_PER_LINE = Integer.parseInt(System.getProperty("BLOCKS_PER_LINE", "5"));

	/**
	 * Formatter zur Binaeren Ausgabe
	 */
	private final Formatter binFormat = new BinaryFormatter();

	/**
	 * Formatter zur mnemonischen Ausgabe
	 */
	private final Formatter mneFormat = new MnemonicFormatter();

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

		sb.append("PowerPC by Team SSH").append(NEWLINE).append(NEWLINE); // Sacher Schrimpf Hablützel (muahaha)

		appendMeta(alu, registers, cnt, sb);
		appendRegisters(registers, sb);
		appendInstructions(cnt, memory, sb);
		appendMemory(memory, sb);

		sblen = sb.length();
		System.out.println(sb.toString());
	}

	private void appendMeta(final ALU alu, final Register[] registers, final int cnt, final StringBuilder sb) {
		sb.append(" Befehlszähler: ").append(cnt);
		sb.append(" (").append(binFormat.formatNumber(cnt, 16)).append(')');
		sb.append(NEWLINE);
		sb.append(" Akku:\t\t").append(registers[0].read());
		sb.append(" (").append(binFormat.formatNumber(registers[0].read(), 16)).append(")");
		sb.append(NEWLINE);
		sb.append(" Carry:\t\t").append(alu.isCarryFlag() ? '1' : '0').append(NEWLINE);

	}

	private void appendRegisters(final Register[] registers, final StringBuilder sb) {
		sb.append(" Reg-1:\t\t").append(registers[1].read());
		sb.append(" (").append(binFormat.formatNumber(registers[1].read(), 16)).append(")");
		sb.append(", Reg-2: ").append(registers[2].read());
		sb.append(" (").append(binFormat.formatNumber(registers[2].read(), 16)).append(")");
		sb.append(", Reg-3: ").append(registers[3].read());
		sb.append(" (").append(binFormat.formatNumber(registers[3].read(), 16)).append(")");
		sb.append(NEWLINE);
	}

	private void appendInstructions(final int cnt, final MainMemory memory, final StringBuilder sb) {
		// Wenn der cnt bei 101 ist, wollen wir erst ab 101 ausgeben
		final int start = Math.max(MIN_CNT, cnt - BEFORE_CNT);
		final int end = Math.min(MAX_CNT, cnt + AFTER_CNT);
		sb.append(" Befehle:      ");

		for (int i = start; i <= end; i += 2) {
			Instruction instr = memory.readInstruction(i);
			if (instr == null) {
				break;
			}

			if (i - start != 0 && (i - start) % BLOCKS_PER_LINE == 0) {
				sb.append(NEWLINE).append("               ");
			}

			sb.append(' ');

			if (i == cnt) {
				sb.append("[");
			}

			sb.append(instr.toString());
			sb.append(" (").append(binFormat.formatNumber(instr.getBinary(), 16)).append(')');

			if (i == cnt) {
				sb.append("]");
			}
		}
		sb.append(NEWLINE);
	}

	private void appendMemory(final MainMemory memory, final StringBuilder sb) {
		sb.append(" Speicher:     ");

		for (int i = 500; i <= 528; i += 2) {
			if (i - 500 != 0 && (i - 500) % BLOCKS_PER_LINE == 0) {
				sb.append(NEWLINE).append("               ");
			} else if (i > 500) {
				sb.append(',');
			}

			sb.append(' ').append(i).append(" = ");

			short data = memory.readData(i);
			sb.append(mneFormat.formatNumber(data, 7));
			sb.append(" (").append(binFormat.formatNumber(data, 16)).append(')');
		}
		sb.append(NEWLINE);
	}
}
