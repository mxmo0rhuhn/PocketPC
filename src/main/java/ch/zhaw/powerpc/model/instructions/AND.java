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
	private final int address;

	public AND(int register, int address){
		checkRegisterBounds(register);
		checkAddressBoundsData(address);
		this.register = register;
		this.address = address;
	}

	@Override
	public int run(ControlUnit controlUnit) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public char getBinary() {
		// TODO Auto-generated method stub
		return 0;
	}

}
