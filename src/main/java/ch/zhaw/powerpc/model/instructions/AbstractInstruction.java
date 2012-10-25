package ch.zhaw.powerpc.model.instructions;

/**
 * Einige Hilfsmethoden (z.B. Validierung und Umwandlung) fuer die Instruktionen.
 * 
 * @author Reto
 * 
 */
public abstract class AbstractInstruction implements Instruction {

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
		throw new UnsupportedOperationException("Implement Me!");
	}

	protected static final String num(int number) {
		throw new UnsupportedOperationException("Implement Me!");
	}

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
	 * Stellt sicher, dass eine Adresse gueltig ist
	 * 
	 * @throws InvalidInstructionException
	 *             wenn die Adresse nicht existiert
	 */
	public static void checkAddressBounds(int address) {
		// TODO upper and lower bounds
		if (address < 0) {
			throw new InvalidInstructionException("Diese Adresse existiert nicht: " + address);
		}
	}
}