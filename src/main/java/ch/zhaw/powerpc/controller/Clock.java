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

	/**
	 * Der verschwindet hier noch ;)
	 */
	private final Printer printer;

	public Clock(ControlUnit controlUnit, Printer printer) {
		this.controlUnit = controlUnit;
		this.printer = printer;
	}

	/**
	 * Step-Modus: Wie der Slow-Modus, jedoch wird das Programm nach Bearbeitung eines jeden Befehls unterbrochen und
	 * wird erst nach einer Bestätigung durch den User (z. B. Drücken einer Taste) wieder fortgesetzt
	 */
	public void step() {
		this.controlUnit.runCycle();
		this.printer.print(this.controlUnit);
	}

	/**
	 * Slow-Modus: Während des Programmablaufs wird nach Bearbeitung eines jeden Befehls die Ausgabe aktualisiert
	 */
	public void startSlowMode() {
		while (controlUnit.runCycle()) {
			this.printer.print(this.controlUnit);
			try {
				wait(1000);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}
	}

	/**
	 * Schneller Modus: Während des Programmablaufs erfolgt keine Ausgabe (keine Aktualisierung der Ausgabedaten); diese
	 * werden erst am Programmende aktualisiert
	 */
	public void startFastMode() {
		while (controlUnit.runCycle()) {
			this.printer.print(controlUnit);
		}
	}
}
