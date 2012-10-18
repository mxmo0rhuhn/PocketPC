package ch.zhaw.powerpc.model;

/**
 * Diese Klasse repräsentiert ein Register. Das Register selbst weiss nicht, welches Register es ist - das weiss nur die
 * ControlUnit.
 * 
 * @author Max / Reto
 * 
 */
public final class Register {

	private int val;

	Register() {
		// klasse muss nur von ControlUnit instanziert werden. deshalb default access modifier
	}

	/**
	 * Liest den Inhalt aus diesem Register.
	 */
	public int read() {
		return val;
	}

	/**
	 * Schreibt die Daten in dieses Register. Maximal 16 Bit!
	 */
	public void write(int val) {
		/*
		 * Bei positiven Zahlen werden die ersten 16 Bit auf 0 gesetzt und bei negativen Zahlen die ersten 16 Bit auf 1.
		 */
		if (val >= 0) {
			this.val = (0x0000FFFF & val);
		} else if (val < 0) {
			this.val = (0xFFFF0000 | val);
		} 
	}

}
