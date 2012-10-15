package ch.zhaw.powerpc.controller;

import ch.zhaw.powerpc.model.ControlUnit;

/**
 * Diese Klasse startet einen Zyklus in der ControlUnit. Somit bestimmt diese Klasse auch, wie schnell die ein Zyklus
 * nach dem anderen ausgeführt wird.
 * 
 * @author Max / Reto
 * 
 */
public class Clock {

	private final ControlUnit controlUnit;

	public Clock(ControlUnit controlUnit) {
		this.controlUnit = controlUnit;
	}

	/**
	 * Startet einen Zyklus
	 */
	public void tick() {
		controlUnit.runCycle();
		throw new UnsupportedOperationException("Implement me [https://bitbucket.org/rethab/pocketpc/issue/29/laufmodi]");
	}

}
