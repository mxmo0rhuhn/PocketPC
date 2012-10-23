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

	public void step() {
		controlUnit.runCycle();
		printer.print(controlUnit);
	}
	
	public void startSlowMode() {
		while(controlUnit.runCycle()) {
			try {
				wait(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			printer.print(controlUnit);
		}
	}
	
	public void startFastMode() {
		while(controlUnit.runCycle()) {
		}
	}
}
