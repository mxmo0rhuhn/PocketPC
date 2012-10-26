/**
 * 
 */
package ch.zhaw.powerpc.model.instructions;

import ch.zhaw.powerpc.model.ControlUnit;

/**
 * Wenn der Akku ≠ 0 ist, verzweige an die durch den Operanden angegebene Speicheradresse; sonst wird das Programm mit
 * dem folgenden Befehl forgesetzt. Mit 10 Bit können 1KiB Speicher adressiert werden.
 * 
 * @author Max
 * 
 */
public class BNZD extends AbstractInstruction {

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
