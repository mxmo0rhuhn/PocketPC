package ch.zhaw.powerpc.controller;

import ch.zhaw.powerpc.model.ControlUnit;

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
	}

}
