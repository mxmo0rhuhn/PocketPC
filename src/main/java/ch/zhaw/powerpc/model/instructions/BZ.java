/**
 * 
 */
package ch.zhaw.powerpc.model.instructions;

import ch.zhaw.powerpc.model.ControlUnit;

/**
 * Wenn der Akku 0 ist, verzweige an die durch das Register xx (01 bis 11 für R-1, R-2 bzw. R-3) angegebene Speicheradresse; sonst wird der
 * folgende Befehl normal fortgeführt.
 * 
 * @author Max
 * 
 */
public class BZ extends AbstractInstruction {

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.zhaw.powerpc.model.instructions.Instruction#run(ch.zhaw.powerpc.model.ControlUnit)
	 */
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
