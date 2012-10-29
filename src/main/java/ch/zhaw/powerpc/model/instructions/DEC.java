/**
 * 
 */
package ch.zhaw.powerpc.model.instructions;

import ch.zhaw.powerpc.model.ControlUnit;

/**
 * Der Akku (16-Bit-Zahl im 2er -Komplement) wird um den Wert 1 inkrementiert; bei Überlauf wird das Carry-Flag gesetzt (= 1), sonst auf den
 * Wert 0.
 * 
 * @author Max
 * 
 */
public class DEC extends AbstractInstruction {

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.zhaw.powerpc.model.instructions.Instruction#run(ch.zhaw.powerpc.model.ControlUnit)
	 */
	@Override
	public int run(ControlUnit controlUnit) {
		controlUnit.getAlu().addToAccu((short) -1);
		return controlUnit.getProgramCounter() + 2;
	}

	@Override
	public String toString() {
		return "DEC";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.zhaw.powerpc.model.instructions.Instruction#getBinary()
	 */
	@Override
	public char getBinary() {
		return genBin("00000100", "00000000");
	}

}
