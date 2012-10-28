/**
 * 
 */
package ch.zhaw.powerpc.model.instructions;

import ch.zhaw.powerpc.model.ControlUnit;

/**
 * Wenn das Carry-Flag gesetzt sie, verzweige an die durch den Operanden angegebene Speicheradresse; sonst wird das
 * Programm mit dem folgenden Befehl forgesetzt. Mit 10 Bit können 1KiB Speicher adressiert werden.
 * 
 * @author Max
 * 
 */
public class BCD extends AbstractInstruction {

	private final int address;

	/**
	 * Erstellt einen Sprungbefehl auf eine Speicheradresse mit der Bedingung, dass das carry flag gesetzt ist
	 * 
	 * @param register
	 *            eine Adresse auf die gesprungen werden soll ... ACHTUNG es wird keineswegs geprüft ob
	 *            der Inhalt des Speichers Sinn ergibt für den Sprung => dies kann schnell im NIRVANA enden... höchst
	 *            kritisch ...
	 */
	public BCD(int address) {
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
		if (controlUnit.getAlu().isCarryFlag()) {
			return (int) address;
		}
		return controlUnit.getProgramCounter() + 2;
	}
	
	@Override
	public String toString() {
		return "BCD #" + this.address;
	}

	@Override
	public char getBinary() {
		return genBin("00111", "0", adr(this.address));
	}

}
