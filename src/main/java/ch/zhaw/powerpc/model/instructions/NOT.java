package ch.zhaw.powerpc.model.instructions;

import ch.zhaw.powerpc.model.ControlUnit;
import ch.zhaw.powerpc.model.Register;

/**
 * Alle Bits im Akku werden Bitsweise negiert
 * 
 * @author Reto
 * 
 */
public class NOT extends AbstractInstruction {

	public NOT() {
		// keine operanden
	}

	@Override
	public int run(ControlUnit controlUnit) {
		Register accu = controlUnit.getRegisters()[0];
		accu.write(~accu.read());
		return controlUnit.getProgramCounter() + 2;
	}

	@Override
	public char getBinary() {
		throw new UnsupportedOperationException("Implement me");
	}

}
