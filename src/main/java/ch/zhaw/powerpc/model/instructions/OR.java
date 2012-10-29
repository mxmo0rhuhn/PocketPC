package ch.zhaw.powerpc.model.instructions;

import ch.zhaw.powerpc.model.ControlUnit;
import ch.zhaw.powerpc.model.Register;

/**
 * Akku und Register xx (00 bis 11 für Akku, R-1, R-2 bzw R-3) werden bitweise logisch mit OD veknüpft.
 * 
 * @author Reto
 *
 */
public class OR extends AbstractInstruction {
	
	private final int register;
	
	public OR(int register) {
		checkRegisterBounds(register);
		this.register = register;
	}

	@Override
	public int run(ControlUnit controlUnit) {
		Register accu = controlUnit.getRegisters()[0];
		Register reg = controlUnit.getRegisters()[this.register];
		accu.write(accu.read() | reg.read());
		return controlUnit.getProgramCounter() + 2;
	}

	@Override
	public char getBinary() {
		return genBin("0000", reg(this.register), "1100000000");
	}
	
	@Override
	public String toString() {
		return "OR " + this.register;
	}

}
