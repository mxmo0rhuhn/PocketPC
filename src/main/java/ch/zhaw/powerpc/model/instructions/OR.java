package ch.zhaw.powerpc.model.instructions;

import ch.zhaw.powerpc.model.ControlUnit;

/**
 * Akku und Register xx (00 bis 11 für Akku, R-1, R-2 bzw R-3) werden bitweise logisch mit OD veknüpft.
 * 
 * @author Reto
 *
 */
public class OR extends AbstractInstruction {
	
	private final int register;
	
	public OR(int register) {
		this.register = register;
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
	
	@Override
	public String toString() {
		return null;
	}

}
