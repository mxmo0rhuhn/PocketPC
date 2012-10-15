package ch.zhaw.powerpc.model.instructions;

import ch.zhaw.powerpc.model.ControlUnit;
import ch.zhaw.powerpc.model.MainMemory;

public final class ADDD implements Instruction {
	
	private final char[] origInstruction;
	
	public ADDD(char[] origInstruction) {
		this.origInstruction = origInstruction;
	}

	@Override
	public void fetchOperands(MainMemory memory) {
		// extrahiere memory adressen aus der origInstruction und lese vom memory
	}

	@Override
	public void run(ControlUnit controlUnit) {
		// fuehre operation aus
	}

	@Override
	public int getNextAddress(int programCounter) {
		return programCounter + 2;
	}

}
