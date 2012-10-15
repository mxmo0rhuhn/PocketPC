package ch.zhaw.powerpc.model;

import ch.zhaw.powerpc.model.instructions.Instruction;

/**
 * Diese Klasse ist der Mittelpunkt unseres PowerPC. Sie ist dafür zuständig, dass in einem Zylus der richtige Ablauf
 * von statten geht (z.B. Reihenfolge).
 * 
 * @author Max / Reto
 * 
 */
public class ControlUnit {

	public static final int BUS_SIZE = 16;

	/**
	 * Referenz auf den Hauptspeicher
	 */
	private final MainMemory memory;

	/**
	 * Referenz auf die 4 Register. Register 1 (bzw. Index 0) ist der Akkumulator.
	 */
	private final Register[] registers;

	/**
	 * Arithmetisch-Logische Einheit. Kann mit char-arrays arithemtische und logische Operationen durchführen.
	 */
	private final ALU alu;

	/**
	 * Der Program Counter zeigt an, welches die nächste Instruktion ist. Nach jeder ausgeführten Instruktion kann
	 * entscheidet die Instruktion, ob und um wie viel der Program Counter erhöht bzw. tiefer gesetzt werden soll.
	 */
	private int programCounter;

	public ControlUnit(MainMemory memory) {
		this.memory = memory;
		this.registers = new Register[] { new Register(), new Register(), new Register(), new Register() };
		this.alu = new ALU(this.registers);
		this.programCounter = 0;
	}

	public void runCycle() {
		char[] binInstruction = instructionFetch();
		Instruction currentInstruction = Decoder.instructionDecode(binInstruction);
		this.programCounter = currentInstruction.run(this);
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

	public int getProgramCounter() {
		return this.programCounter;
	}

	/**
	 * Der aktuelle Program Counter zeigt auf den nächsten Befehl im Speicher. Dieser wird zurückgegeben.
	 * 
	 */
	private char[] instructionFetch() {
		return memory.read(this.programCounter);
	}

}
