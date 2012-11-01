package ch.zhaw.powerpc.model.instructions;

import ch.zhaw.powerpc.model.ControlUnit;
import ch.zhaw.powerpc.model.Register;

/**
 * Schieben arithmetisch nach rechts: der Inhalt des Akkus wird um 
 * eine Stelle nach rechts geschoben; der Inhalt vom LSb des LSB 
 * (das rechte Bit des Wortes) wird als Carry-Flag gesetzt. 
 * Dabei bleibt das MSb des MSB (Vorzeichenbit) und das 2. Bit des 
 * MSB erhalten.
 * 
 * @author des
 *
 */

public class SRA extends AbstractInstruction {
	
	public SRA() {
		// keine Operanden
	}

	@Override
	public int run(ControlUnit controlUnit) {
		Register akku = controlUnit.getRegisters()[0];
		short curAkku = akku.read();
		
		// Check if curAkku is even (%2 = even => if even than CarryFlag = false
		controlUnit.getAlu().setCarryFlag(curAkku % 2 != 0);
		akku.write(curAkku >> 1);
		
		return controlUnit.getProgramCounter() + 2;
	}

	@Override
	public char getBinary() {
		return genBin("0000101000000000");
	}
	
	@Override
	public String toString() {
		return "SRA";
	}

}
