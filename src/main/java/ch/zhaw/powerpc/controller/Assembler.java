package ch.zhaw.powerpc.controller;

import java.lang.reflect.Constructor;

import ch.zhaw.powerpc.model.instructions.Instruction;
import ch.zhaw.powerpc.model.instructions.InvalidInstructionException;

public final class Assembler {

	/**
	 * Package Name, in dem alle Instruktionen liegen
	 */
	private static final String INSTR_PACKAGE = Instruction.class.getPackage().getName() + ".";

	/**
	 * Assembliert eine Liste vom mnemonischen Instruktionen.
	 * 
	 * @throws InvalidInstructionException
	 *             wenn eine mnemonische Instruktion ungueltig ist
	 */
	public Instruction[] assemble(String[] mnemonics) throws InvalidInstructionException {
		Instruction[] instructions = new Instruction[mnemonics.length];
		for (int i = 0; i < mnemonics.length; i++) {
			instructions[i] = assemble(mnemonics[i]);
		}
		return instructions;
	}

	/**
	 * Assembliert eine mnemonische Instruktion.
	 * 
	 * @throws InvalidInstructionException
	 *             wenn eine mnemonische Instruktion ungueltig ist.
	 */
	public Instruction assemble(String mnemonic) throws InvalidInstructionException {
		String[] parts = mnemonic.split(" ");
		// Der erste Teil der Instruktion muss der Klasse entsprechen
		Class<Instruction> klass = loadClass(parts[0]);

		// Der Rest sind Operanden. Je nach dem wie viele Operanden eine Instruktion hat, ist dieses Array
		// unterschiedlich lang. Wenn es zwei Operanden hat, ist das Register immer vor der Adresse.
		int[] ops = extractOperands(parts);

		// Es wird angenommen, dass eine Instruktion genau einen Konstruktor besitzt. Dieser wird hier ermittelt.
		Constructor<Instruction> constructor = getConstructor(klass);
		try {

			// Der Konstruktor wird mit den Operanden (Parameter fuer Konstruktor) instanziert.
			return instantiate(constructor, ops);
		} catch (Exception e) {
			throw new InvalidInstructionException("Instruktion [" + mnemonic + "] kann nicht zugewiesen werden, weil: "
					+ e.getMessage());
		}
	}

	private Instruction instantiate(Constructor<Instruction> constructor, int[] ops) throws Exception {
		switch (ops.length) {
		case 0:
			return constructor.newInstance();
		case 1:
			return constructor.newInstance(ops[0]);
		case 2:
			return constructor.newInstance(ops[0], ops[1]);
		default:
			throw new Exception("Es gibt keinen Konstruktor mit mehr als zwei Parametern (zumindest hier)");
		}
	}

	private Constructor<Instruction> getConstructor(Class<Instruction> klass) {
		@SuppressWarnings("unchecked")
		Constructor<Instruction>[] constructors = (Constructor<Instruction>[]) klass.getConstructors();
		if (constructors.length != 1) {
			throw new IllegalStateException(klass.getName() + " muss genau einen Konstruktor haben");
		}
		return constructors[0];
	}

	private int[] extractOperands(String[] parts) {
		boolean binaryOp = parts.length == 3; // ob die instruktion zwei parameter hat
		int[] res = new int[parts.length - 1]; // erstes element ist klassenname
		for (int i = 1; i < parts.length; i++) {
			if (parts[i].startsWith("#")) {
				if (binaryOp && i == 2) {
					throw new InvalidInstructionException(
							"Bei einer binaeren Instruktion ist die Adresse der zweite Parameter");
				}
				res[i - 1] = Integer.parseInt(parts[i].substring(1));
			} else {
				res[i - 1] = Integer.parseInt(parts[i]);
			}
		}
		return res;
	}

	@SuppressWarnings("unchecked")
	private Class<Instruction> loadClass(String className) {
		className = INSTR_PACKAGE + className;
		try {
			return (Class<Instruction>) Class.forName(className);
		} catch (Exception e) {
			throw new InvalidInstructionException("Instruction with Class [" + className + "] does not exist!");
		}
	}

}
