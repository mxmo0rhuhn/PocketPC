package ch.zhaw.powerpc.model.instructions;

import ch.zhaw.powerpc.model.ControlUnit;

/**
 * Löse das Register 'Rxx' (alle Bit auf 0 setzen) und das Carry-Flag (00 bis 11 für: Akku, R-1, R-2, R-3)
 * 
 * @author Reto
 *
 */
public final class CLR implements Instruction {
	
	private final int register;
	
	public CLR(int register) {
		this.register = register;
	}

	@Override
	public int run(ControlUnit controlUnit) {
		controlUnit.getRegisters()[this.register].write(0);
		controlUnit.getAlu().resetCarryFlag();
		return controlUnit.getProgramCounter() + 1;
	}
	
	@Override
	public String toString() {
		return "CLR " + this.register;
	}

}
