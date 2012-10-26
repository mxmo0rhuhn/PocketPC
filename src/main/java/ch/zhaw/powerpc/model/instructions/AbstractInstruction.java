package ch.zhaw.powerpc.model.instructions;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * Einige Hilfsmethoden (z.B. Validierung und Umwandlung) fuer die Instruktionen.
 * 
 * @author Reto
 * 
 */
public abstract class AbstractInstruction implements Instruction {

	/**
	 * Adresse ist bei allen Instruktionen gleich lang.
	 */
	private static final int ADR_LENGTH = 10;

	/**
	 * Nummer ist bei allen Instruktionen gleich lang.
	 */
	private static final int NUM_LENGTH = 15;

	protected static final String reg(int register) {
		switch (register) {
		case 0:
			return "00";
		case 1:
			return "01";
		case 2:
			return "10";
		case 3:
			return "11";
		default:
			throw new IllegalArgumentException("Invalid register:" + register);
		}
	}

	protected static final String adr(int address) {
		return tailor(address, ADR_LENGTH);
	}

	protected static final String num(int number) {
		return tailor(number, NUM_LENGTH);
	}

	public static String tailor(final int num, final int length) {
		char[] bin = Integer.toBinaryString(num).toCharArray();
		char[] res = new char[length];

		// ab welcher position wird das quell-array kopiert
		int srcPos = Math.max(bin.length - length, 0);

		// ab welcher position wird's ins ziel geschrieben
		int destPos = Math.max(length - bin.length, 0);

		// wie viele zeichen werden kopiert
		int len = Math.min(bin.length, length);
		System.arraycopy(bin, srcPos, res, destPos, len);

		// Fuelle Rest mit Nullen
		for (int i = 0; i < length - Math.min(bin.length, length); i++) {
			res[i] = '0';
		}
		return String.valueOf(res);
	}

	/**
	 * Nimmt zwei Bytes und kombiniert diese in einem Integer (16 tiefere Bits).
	 * 
	 * z.B. <pre>combineBytes(10011001, 00110011) -> 00000000000000001001100100110011
	 * 
	 */
	protected static final int combineBytes(byte upper, byte lower) {
		ByteBuffer bb = ByteBuffer.allocate(4);
		bb.order(ByteOrder.BIG_ENDIAN);
		bb.put((byte) 0);
		bb.put((byte) 0);
		bb.put(upper);
		bb.put(lower);
		return bb.getInt(0);
	}

	/**
	 * 
	 * @param xs
	 * @return
	 */
	protected char genBin(String... xs) {
		StringBuilder sb = new StringBuilder(16);
		for (String s : xs) {
			sb.append(s);
		}
		if (sb.length() != 16) {
			throw new IllegalArgumentException("Laenge muss 16 sein, aber: " + sb.toString());
		}
		return (char) Integer.parseInt(sb.toString(), 2);
	}

	/**
	 * Stellt sicher, dass ein Register zwischen -1 und 4 liegt.
	 * 
	 * @throws InvalidInstructionException
	 *             wenn das Register nicht existiert
	 */
	public static void checkRegisterBounds(int register) {
		if (register > 3 || register < 0) {
			throw new InvalidInstructionException("So viele Register haben wir dann auch wieder nicht: " + register);
		}
	}

	/**
	 * Stellt sicher, dass eine Adresse fuer Daten gueltig ist
	 * 
	 * @throws InvalidInstructionException
	 *             wenn die Adresse nicht existiert
	 */
	public static void checkAddressBoundsData(int address) {
		if (address < 500 || address > 1023) {
			throw new InvalidInstructionException("An dieser Adresse werden keine Daten gespeichert: " + address);
		}
	}

	/**
	 * Stellt sicher, dass eine fuer Instruktionen Adresse gueltig ist
	 * 
	 * @throws InvalidInstructionException
	 *             wenn die Adresse nicht existiert
	 */
	public static void checkAddressBoundsInstruction(int address) {
		if (address < 100 || address > 499) {
			throw new InvalidInstructionException("An dieser Adresse werden keine Instruktionen gespeichert: "
					+ address);
		}
	}
}