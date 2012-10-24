package ch.zhaw.powerpc.model.instructions;

/**
 * Einige Hilfsmethoden (z.B. Validierung) fuer die Instruktionen.
 * 
 * @author Reto
 * 
 */
public final class InstructionUtil {

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
