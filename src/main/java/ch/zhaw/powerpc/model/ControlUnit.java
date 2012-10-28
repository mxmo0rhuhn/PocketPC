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

		// Per Definition beginnen Programme bei 100
		this.programCounter = 100;
	}

	/**
	 * Bildet einen normalen Zyklus der Befehlsverarbeitung ab.
	 * 
	 * @return gibt zurück ob ein weiterer Zyklus möglich ist.
	 */
	public boolean runCycle() {
		Instruction currentInstruction = instructionFetch();
		this.programCounter = currentInstruction.run(this);

		// -1 wird von Befehlen gesetzt die zum sofortigen Ende des Programmes führen sollen.
		if (this.programCounter == -1) {
			return false;
		}
		return true;
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
	private Instruction instructionFetch() {
		return memory.readInstruction(this.programCounter);
	}
	
	@Override
	public String toString() {
		return "ControlUnit\nMainMemory: "+memory+"\n" +
				"PrgCnt:     "+programCounter+"\n" +
				"Akku:       "+registers[0]+"\n" +
				"Reg-1:      "+registers[1]+"\n" +
				"Reg-2:      "+registers[2]+"\n" +
				"Reg-3:      "+registers[3]+"\n" +
				"ALU:        "+alu+"\n";
	}

}