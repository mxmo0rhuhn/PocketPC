/**
 * 
 */
package ch.zhaw.powerpc.model.instructions;

import ch.zhaw.powerpc.model.ControlUnit;

/**
 * Wenn der Akku ≠ 0 ist, verzweige an die durch das Register xx (01 bis 11 für R-1, R-2 bzw. R-3) angegebene Speicheradresse; sonst wird
 * der folgende Befehl normal fortgeführt.
 * 
 * @author Max
 * 
 */
public class BNZ extends AbstractInstruction {

	private final int register;

	/**
	 * Erstellt einen Sprungbefehl auf die Adresse, die in einem Register hinterlegt ist, mit der Bedingung,dass der Akku ungleich 0 ist.
	 * 
	 * @param register
	 *            ein Register mit der Adresse auf die gesprungen werden soll ... ACHTUNG es wird keineswegs geprüft ob der Inhalt des
	 *            Registers Sinn ergibt für den Sprung => dies kann schnell im NIRVANA enden... höchst kritisch ...
	 */
	public BNZ(int register) {
		checkRegisterBounds(register);
		if (register == 0) {
			throw new InvalidInstructionException("Kann Nicht bei einem Vergleivch auf dieses Register springen");
		}
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
		if (controlUnit.getRegisters()[0].read() != 0) {
			return controlUnit.getRegisters()[register].read();
		}
		return controlUnit.getProgramCounter() + 2;
	}

	@Override
	public String toString() {
		return "BNZ " + this.register;
	}

	@Override
	public char getBinary() {
		return genBin("0001", reg(this.register), "01", "00000000");
	}

}
