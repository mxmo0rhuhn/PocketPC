/*
 * Copyright (c) 2012 - Reto Hablützel, Max Schrimpf, Désirée Sacher
 *
 * Licensed under the EUPL, Version 1.1 or – as soon they will be approved by the European Commission - subsequent versions of the EUPL (the "Licence");
 * You may not use this work except in compliance with the Licence.
 * You may obtain a copy of the Licence at:
 *
 * https://joinup.ec.europa.eu/software/page/eupl5
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the Licence is distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Licence for the specific language governing permissions and limitations under the Licence.
 */

package ch.zhaw.powerpc.model;

import ch.zhaw.powerpc.controller.Clock;
import ch.zhaw.powerpc.model.instructions.Instruction;
import ch.zhaw.powerpc.model.instructions.NoMoreInstructionsException;

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

	/**
	 * The clock rate of a CPU is normally determined by the frequency of an oscillator crystal. Typically a crystal
	 * oscillator produces a fixed sine wave—the frequency reference signal.
	 */
	private final Clock clock;

	public Clock getClock() {
		return clock;
	}

	public ControlUnit(MainMemory memory) {
		this.clock = new Clock(this);
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

		try {
			this.programCounter = currentInstruction.run(this);
			return true;
		} catch (NoMoreInstructionsException nmie) {
			this.programCounter += 2;
			return false;
		}

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
		return "ControlUnit\nMainMemory: " + memory + "\n" + "PrgCnt:     " + programCounter + "\n" + "Akku:       "
				+ registers[0] + "\n" + "Reg-1:      " + registers[1] + "\n" + "Reg-2:      " + registers[2] + "\n"
				+ "Reg-3:      " + registers[3] + "\n" + "ALU:        " + alu + "\n";
	}

}