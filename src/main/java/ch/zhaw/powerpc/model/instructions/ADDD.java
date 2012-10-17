package ch.zhaw.powerpc.model.instructions;

import ch.zhaw.powerpc.model.ControlUnit;

/**
 * Addition zweier 16-Bit-Zahlen (Zahl im Akku und Zahl im Register 'Rxx'; 00 bis 11 für Akku, R-1, R-2, R-3) im 2-er
 * Komplement; bei Überlauf wird das Carry-Flag gesetzt (=1), sonst auf den Wert 0.
 * 
 * @author Reto
 * 
 */
public final class ADDD extends NumberInstruction {
	
	public ADDD(int number) {
		super("ADDD", number);
	}

	@Override
	public int run(ControlUnit controlUnit) {
		controlUnit.getAlu().addToAccu(getNumber());
		return controlUnit.getProgramCounter() + 1;
	}

}
