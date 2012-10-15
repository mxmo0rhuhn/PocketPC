package ch.zhaw.powerpc.model;

import ch.zhaw.powerpc.model.instructions.Instruction;

public class ControlUnit {

	private final MainMemory memory; // RAM

	private int programCounter;

	private final Register[] registers;

	private final ALU alu;

	public ControlUnit(MainMemory memory) {
		this.memory = memory;
		this.registers = new Register[] { new Register(), new Register(), new Register(), new Register() };
		this.alu = new ALU();
		this.programCounter = 0;
	}

	public void runCycle() {
		char[] binInstruction = instructionFetch(); // 16 bit character array mit 0 und 1 als werten
		Instruction currentInstruction = Decoder.instructionDecode(binInstruction);
		currentInstruction.fetchOperands(memory); // liest nur aus dem memory
		currentInstruction.run(this);
		this.programCounter = currentInstruction.getNextAddress(this.programCounter);
	}
	
	public MainMemory getMemory() {
		return memory;
	}

	public Register[] getRegisters() {
		return registers;
	}
	
	public ALU getAlu() {
		return this.alu;
	}


	private char[] instructionFetch() {
		return memory.get(this.programCounter);
	}

}
