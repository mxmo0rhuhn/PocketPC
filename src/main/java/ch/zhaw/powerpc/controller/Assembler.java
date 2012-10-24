package ch.zhaw.powerpc.controller;

import java.lang.reflect.Constructor;

import ch.zhaw.powerpc.model.instructions.Instruction;
import ch.zhaw.powerpc.model.instructions.InvalidInstructionException;

public final class Assembler {

	/**
	 * Package Name, in dem alle Instruktionen liegen
	 */
	private static final String INSTR_PACKAGE = Instruction.class.getPackage().getName() + ".";

	public Instruction[] assemble(String[] mnemonics) throws InvalidInstructionException {
		Instruction[] instructions = new Instruction[mnemonics.length];
		for (int i = 0; i < mnemonics.length; i++) {
			instructions[i] = assemble(mnemonics[i]);
		}
		return instructions;
	}

	public Instruction assemble(String mnemonic) throws InvalidInstructionException {
		String[] parts = mnemonic.split(" ");
		Class<Instruction> klass = loadClass(parts[0]);
		int[] ops = extractOperands(parts);
		Constructor<Instruction> constructor = getConstructor(klass);
		try {
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
		int[] res = new int[parts.length - 1]; // erstes element ist klassenname
		for (int i = 1; i < parts.length; i++) {
			if (parts[i].startsWith("#")) {
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
