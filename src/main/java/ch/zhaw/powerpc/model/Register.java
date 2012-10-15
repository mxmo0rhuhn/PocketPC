package ch.zhaw.powerpc.model;

import static ch.zhaw.powerpc.model.ControlUnit.BUS_SIZE;

/**
 * Diese Klasse repräsentiert ein Register. Das Register selbst weiss nicht, welches Register es ist - das weiss nur die ControlUnit.
 * 
 * @author Max / Reto
 *
 */
public final class Register {
	
	private char[] data;
	
	Register() {
		// klasse muss nur von ControlUnit instanziert werden. deshalb default access modifier
	}
	
	/**
	 * Liest den Inhalt aus diesem Register.
	 */
	public char[] read() {
		return data;
	}
	
	/**
	 * Schreibt die Daten in dieses Register.
	 */
	public void write(char[] data) {
		if (data.length > BUS_SIZE) {
			throw new IllegalStateException("Data for Register must not exceed " + BUS_SIZE);
		}
		this.data = data;
	}

}
