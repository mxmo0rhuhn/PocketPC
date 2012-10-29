package ch.zhaw.powerpc.model.instructions;

import ch.zhaw.powerpc.model.ControlUnit;
import ch.zhaw.powerpc.model.Register;

/**
 * Schieben logisch nach links: der Inhalt des Akkus wird um eine Stelle nach links geschoben; der Inhalt vom LSB (das
 * rechte Bit des Worts) wird mit 0 aufgefüllt, das MSb des MSB (das 1. Bit des Wortes) wird als Carry-Flag gesetzt.
 * 
 * @author Reto
 * 
 */
public class SLL extends AbstractInstruction {
	
	public SLL() {
		// keine Operanden
	}

	@Override
	public int run(ControlUnit controlUnit) {
		Register accu = controlUnit.getRegisters()[0];
		short curAccu = accu.read();
		controlUnit.getAlu().setCarryFlag(curAccu < 0);
		accu.write(curAccu << 1);
		return controlUnit.getProgramCounter() + 2;
	}

	@Override
	public char getBinary() {
		return genBin("0000110000000000");
	}
	
	@Override
	public String toString() {
		return "SLL";
	}

}
