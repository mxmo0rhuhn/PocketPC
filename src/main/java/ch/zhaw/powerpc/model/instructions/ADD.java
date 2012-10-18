package ch.zhaw.powerpc.model.instructions;

import ch.zhaw.powerpc.model.ControlUnit;

/**
 * Addition zweier 16-Bit-Zahlen (Zahl im Akku und Zahl im Register 'Rxx'; 00 bis 11 für Akku, R-1, R-2, R-3) im 2er
 * Komplement, bei Überlauf wird das Carry-Flat gesetzt (=1), sonst auf den Wert 0.
 * 
 * @author Reto
 * 
 */
public final class ADD implements Instruction {
	
	private final int register;
	
	public ADD(int register) {
		this.register = register;
	}
	
	@Override
	public int run(ControlUnit controlUnit) {
		controlUnit.getAlu().addToAccu(controlUnit.getRegisters()[this.register].read());
		return controlUnit.getProgramCounter() + 1;
	}
	
	@Override
	public String toString() {
		return "ADD " + this.register;
	}

}
