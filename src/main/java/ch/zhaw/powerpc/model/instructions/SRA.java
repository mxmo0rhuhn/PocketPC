package ch.zhaw.powerpc.model.instructions;

import ch.zhaw.powerpc.model.ControlUnit;
import ch.zhaw.powerpc.model.Register;

public class SRA extends AbstractInstruction {
	
	public SRA() {
		// keine Operanden
	}

	@Override
	public int run(ControlUnit controlUnit) {
		Register akku = controlUnit.getRegisters()[0];
		short curAkku = akku.read();
		controlUnit.getAlu().setCarryFlag(curAkku < 0);
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
