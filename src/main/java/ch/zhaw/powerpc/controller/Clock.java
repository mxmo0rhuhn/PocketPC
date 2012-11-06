package ch.zhaw.powerpc.controller;

import java.util.Observable;

import ch.zhaw.powerpc.model.ControlUnit;

/**
 * Diese Klasse startet einen Zyklus in der ControlUnit. Somit bestimmt diese Klasse auch, wie schnell die ein Zyklus
 * nach dem anderen ausgeführt wird.
 * 
 * @author Max / Reto
 * 
 */
public class Clock extends Observable {

	private final ControlUnit controlUnit;
	private volatile boolean stopped;
	private volatile boolean paused;
	private int stepCounter;

	public Clock(ControlUnit controlUnit) {
		stopped = false;
		this.controlUnit = controlUnit;
	}

	/**
	 * Step-Modus: Wie der Slow-Modus, jedoch wird das Programm nach Bearbeitung eines jeden Befehls unterbrochen und
	 * wird erst nach einer Bestätigung durch den User (z. B. Drücken einer Taste) wieder fortgesetzt
	 */
	public boolean step() {
		stopped = !this.controlUnit.runCycle();
		stepCounter++;
		setChanged();
		notifyObservers();
		return stopped;
	}

	public void setStopped(boolean stopped) {
		this.stopped = stopped;
	}

	public void setPaused(boolean paused) {
		this.paused = paused;
	}

	/**
	 * Slow-Modus: Während des Programmablaufs wird nach Bearbeitung eines jeden Befehls die Ausgabe aktualisiert
	 */
	public void startSlowMode() {
		paused = false;
		while (!stopped && !paused) {
			stepCounter++;
			setChanged();
			notifyObservers();
			stopped = !this.controlUnit.runCycle();
			try {
				Thread.sleep(1000);
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
		paused = false;
		while (!stopped && !paused) {
			stepCounter++;
			stopped = !this.controlUnit.runCycle();
			setChanged();
			notifyObservers();
		}
	}

	public int getStepCounter() {
		return stepCounter;
	}

	public boolean isStopped() {
		return stopped;
	}
}
