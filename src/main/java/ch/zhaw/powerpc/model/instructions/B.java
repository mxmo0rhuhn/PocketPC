/**
 * 
 */
package ch.zhaw.powerpc.model.instructions;

import ch.zhaw.powerpc.model.ControlUnit;

/**
 * Verzweige an die durch das Register xx (01 bis 11 für R-1, R-2 bzw. R-3) angegebene Speicheradresse.
 * 
 * @author Max
 * 
 */
public class B extends AbstractInstruction {

	private final int register;

	/**
	 * Erstellt einen unbedingten Sprungbefehl
	 * 
	 * @param register
	 *            ein Register mit der Adresse auf die gesprungen werden soll ... ACHTUNG es wird keineswegs geprüft ob
	 *            der Inhalt des Registers Sinn ergibt für den Sprung => dies kann schnell im NIRVANA enden... höchst
	 *            kritisch ...
	 */
	public B(int register) {
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
		return controlUnit.getRegisters()[register].read();
	}
	
	@Override
	public String toString() {
		return "B " + this.register;
	}

	@Override
	public char getBinary() {
		return genBin("0001", reg(this.register), "00", "00000000");
	}

}
