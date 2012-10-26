package ch.zhaw.powerpc.model.instructions;

import ch.zhaw.powerpc.model.ControlUnit;

/**
 * "Hilfsbefehl" für die Realisierung: Tatsächlich arbeitet der Prozessor ja immer weiter; er muss sogesehen in eine
 * Schleife eintreten, in der er keine Werte verändert und die er durch einen Interrupt (z. B. für einen
 * Programmneustart) wieder verlassen kann. Technisch wird das Ende realisiert indem der Befehlszähler auf -1 gesetzt
 * wird.
 * 
 * @author Max
 * 
 */
public class END extends AbstractInstruction {

	@Override
	public int run(ControlUnit controlUnit) {
		return -1;
	}

	@Override
	public String toString() {
		return "END";
	}

	@Override
	public char getBinary() {
		return genBin("0000000000000000");
	}
}
