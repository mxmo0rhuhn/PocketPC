package ch.zhaw.powerpc.model.instructions;

import ch.zhaw.powerpc.model.ControlUnit;
/**
 * Schieben arithmetisch nach links: der Inalt des Akkus wird um eine STelle nach links verschoben;
 * der Inhalt vom 2.Bit des MSB (das 2. Bit des Wortes) wird als Carry Flag gesetzt, Dabei bleibt 
 * das MSb des MSB (Vorzeichenbit) erhalten. In das LSb des LSB (das letzte Bit des Wortes) wird 
 * eine 0 geschrieben.
 * 
 * @author des
 *
 */

public class SLA extends AbstractInstruction{

	@Override
	public int run(ControlUnit controlUnit) {
		int akku = controlUnit.getRegisters()[0].read();
		
		return 0;
	}

	@Override
	public char getBinary() {
		// TODO Auto-generated method stub
		return 0;
	}

}
