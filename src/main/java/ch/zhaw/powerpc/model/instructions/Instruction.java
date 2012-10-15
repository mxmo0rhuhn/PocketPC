package ch.zhaw.powerpc.model.instructions;

import ch.zhaw.powerpc.model.ControlUnit;
import ch.zhaw.powerpc.model.MainMemory;

public interface Instruction {

	void fetchOperands(MainMemory memory);

	void run(ControlUnit controlUnit);

	/**
	 * 
	 * @param momentaner program counter
	 * @return absolute adresse
	 */
	int getNextAddress(int programCounter);

}
