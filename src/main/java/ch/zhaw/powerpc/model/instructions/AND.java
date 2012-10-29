package ch.zhaw.powerpc.model.instructions;

import ch.zhaw.powerpc.model.ControlUnit;

/**
 * Akku und Register xx (00 bis 11 fuer Akku, R-1, R-2 bwz R-3) werden bitweise
 * logisch mit AND verknüpft.
 * 
 * @author des
 *
 */

public class AND extends AbstractInstruction{
	
	private final int register;

	public AND(int register){
		checkRegisterBounds(register);
		this.register = register;
	}

	@Override
	public int run(ControlUnit controlUnit) {
		int akku = controlUnit.getRegisters()[0].read();
		int reg = controlUnit.getRegisters()[this.register].read();
		int result = 0;
		result = akku & reg;
		controlUnit.getRegisters()[0].write(result);
		return controlUnit.getProgramCounter() + 2;
	}

	@Override
	public char getBinary() {
		// TODO Auto-generated method stub
		return 0;
	}

}
