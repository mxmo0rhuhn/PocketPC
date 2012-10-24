package ch.zhaw.powerpc.controller;

import ch.zhaw.powerpc.model.instructions.Instruction;

public final class Assembler {
	
	public Instruction[] assemble(String[] mnemonics) throws InvalidInstructionException {
		Instruction[] instructions = new Instruction[mnemonics.length];
		for (int i = 0; i < mnemonics.length; i++) {
			instructions[i] = assemble(mnemonics[i]);
		}
		return instructions;
	}
	
	public Instruction assemble(String mnemonic) throws InvalidInstructionException {
		return null;
	}

}
