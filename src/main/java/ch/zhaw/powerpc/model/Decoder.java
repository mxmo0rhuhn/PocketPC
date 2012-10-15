package ch.zhaw.powerpc.model;

import ch.zhaw.powerpc.model.instructions.Instruction;

/**
 * 
 * @author Max / Reto
 *
 */
public final class Decoder {

	/**
	 * Basierend auf dem übergebenen binären array muss ein Objekt der Klasse Instruction erstellt werden. Im Package
	 * instructions gibt es eine Anzahl davon - von dort muss eine genommen werden! 
	 * 
	 * @param binInstruction character array aus Nullen und Einsen
	 * @return eine Instruktion, die ausgeführt werden kann.
	 */
	public static Instruction instructionDecode(char[] binInstruction) {
		throw new UnsupportedOperationException("Implement me [https://bitbucket.org/rethab/pocketpc/issue/30/instruction-decoder]");
	}

}
