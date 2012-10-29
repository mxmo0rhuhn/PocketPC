package ch.zhaw.powerpc.model.instructions;

import ch.zhaw.powerpc.model.ControlUnit;

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
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public char getBinary() {
		// TODO Auto-generated method stub
		return 0;
	}

}
