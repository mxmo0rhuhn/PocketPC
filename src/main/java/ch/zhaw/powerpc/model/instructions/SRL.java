package ch.zhaw.powerpc.model.instructions;

import ch.zhaw.powerpc.model.ControlUnit;
import ch.zhaw.powerpc.model.Register;

/**
 * Schieben logisch nach rechts: der Inhalt des Akkus wird um eine Stelle nach rechts geschoben; der Inhalt vom LSb des
 * LSB (das rechte Bit des Wortes) wird als Carry-Flag gesetzt. das MSb des MSB (das 1. Bit des Wortes) wird auf 0
 * gesetzt.
 * 
 * @author Reto
 */
public class SRL extends AbstractInstruction {
	
	public SRL() {
		// keine Operanden
	}

	@Override
	public int run(ControlUnit controlUnit) {
		Register accu = controlUnit.getRegisters()[0];
		controlUnit.getAlu().setCarryFlag(accu.read() % 2 != 0);
		accu.write(accu.read() >>> 1);
		return controlUnit.getProgramCounter() + 2;
	}

	@Override
	public char getBinary() {
		return genBin("0000100100000000");
	}
	
	@Override
	public String toString() {
		return "SRL";
	}

}
