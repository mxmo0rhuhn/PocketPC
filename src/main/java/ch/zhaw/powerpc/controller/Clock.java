package ch.zhaw.powerpc.controller;

import ch.zhaw.powerpc.model.ControlUnit;
import ch.zhaw.powerpc.view.Printer;

/**
 * Diese Klasse startet einen Zyklus in der ControlUnit. Somit bestimmt diese Klasse auch, wie schnell die ein Zyklus
 * nach dem anderen ausgeführt wird.
 * 
 * @author Max / Reto
 * 
 */
public class Clock {

	private final ControlUnit controlUnit;
	
	private final Printer printer;

	public Clock(ControlUnit controlUnit, Printer printer) {
		this.controlUnit = controlUnit;
		this.printer = printer;
	}

	/**
	 * Startet einen Zyklus
	 */
	public void tick() {
		controlUnit.runCycle();
		printer.print(controlUnit);
		throw new UnsupportedOperationException("Implement me [https://bitbucket.org/rethab/pocketpc/issue/29/laufmodi]");
	}

}
