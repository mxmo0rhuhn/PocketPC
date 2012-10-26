package ch.zhaw.powerpc.model.instructions;

import ch.zhaw.powerpc.model.ControlUnit;

/**
 * Löse das Register 'Rxx' (alle Bit auf 0 setzen) und das Carry-Flag (00 bis 11 für: Akku, R-1, R-2, R-3)
 * 
 * @author Reto
 * 
 */
public class CLR extends AbstractInstruction {

	private final int register;

	public CLR(int register) {
		checkRegisterBounds(register);
		this.register = register;
	}

	@Override
	public int run(ControlUnit controlUnit) {
		controlUnit.getRegisters()[this.register].write(0);
		controlUnit.getAlu().setCarryFlag(false);
		return controlUnit.getProgramCounter() + 2;
	}

	@Override
	public String toString() {
		return "CLR " + this.register;
	}

	@Override
	public char getBinary() {
		return genBin("0000" + reg(this.register) + "1010000000");
	}
}
