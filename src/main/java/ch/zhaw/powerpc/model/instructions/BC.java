/**
 * 
 */
package ch.zhaw.powerpc.model.instructions;

import ch.zhaw.powerpc.model.ControlUnit;

/**
 * Wenn das Carry-Flag gesetzt ist (= 1), verzweige an die durch das Register xx (01 bis 11 für R-1, R-2 bzw. R-3) angegebene
 * Speicheradresse, sonst wird der folgende Befehl normal fortgeführt.
 * 
 * 
 * @author Max
 * 
 */
public class BC extends AbstractInstruction {

	private final int register;

	/**
	 * Erstellt einen Sprungbefehl auf die Adresse, die in einem Register hinterlegt ist, mit der Bedingung, dass das carry flag gesetzt
	 * ist.
	 * 
	 * @param register
	 *            ein Register mit der Adresse auf die gesprungen werden soll ... ACHTUNG es wird keineswegs geprüft ob der Inhalt des
	 *            Registers Sinn ergibt für den Sprung => dies kann schnell im NIRVANA enden... höchst kritisch ...
	 */
	public BC(int register) {
		checkRegisterBounds(register);
		this.register = register;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.zhaw.powerpc.model.instructions.Instruction#run(ch.zhaw.powerpc.model.ControlUnit)
	 */
	@Override
	public int run(ControlUnit controlUnit) {
		checkAddressBoundsInstruction(controlUnit.getRegisters()[register].read());
		if (controlUnit.getAlu().isCarryFlag()) {
			return controlUnit.getRegisters()[register].read();
		}
		return controlUnit.getProgramCounter() + 2;
	}

	@Override
	public String toString() {
		return "BC " + this.register;
	}

	@Override
	public char getBinary() {
		return genBin("0001", reg(this.register), "11", "00000000");
	}

}
