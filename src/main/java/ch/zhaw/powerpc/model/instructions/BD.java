/**
 * 
 */
package ch.zhaw.powerpc.model.instructions;

import ch.zhaw.powerpc.model.ControlUnit;

/**
 * Verzweige an die durch den Operanden angegebene Speicheradresse. Mit 10 Bit können 1KiB Speicher adressiert werden.
 * 
 * @author Max
 * 
 */
public class BD extends AbstractInstruction {

	private final int address;

	/**
	 * Erstellt einen unbedingten Sprungbefehl auf eine Speicheradresse
	 * 
	 * @param register
	 *            eine Adresse auf die gesprungen werden soll ... ACHTUNG es wird keineswegs geprüft ob
	 *            der Inhalt des Speichers Sinn ergibt für den Sprung => dies kann schnell im NIRVANA enden... höchst
	 *            kritisch ...
	 */
	public BD(int address) {
		checkAddressBoundsInstruction(address);
		this.address = address;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.zhaw.powerpc.model.instructions.Instruction#run(ch.zhaw.powerpc.model.ControlUnit)
	 */
	@Override
	public int run(ControlUnit controlUnit) {
		return (int) address;
	}
	
	@Override
	public String toString() {
		return "B #" + this.address;
	}

	@Override
	public char getBinary() {
		return genBin("00100", "0", adr(this.address));
	}
}
