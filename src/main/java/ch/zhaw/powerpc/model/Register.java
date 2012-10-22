package ch.zhaw.powerpc.model;

/**
 * Diese Klasse repräsentiert ein Register. Das Register selbst weiss nicht, welches Register es ist - das weiss nur die
 * ControlUnit.
 * 
 * @author Max / Reto
 * 
 */
public final class Register {

	private short val;

	Register() {
		// klasse muss nur von ControlUnit instanziert werden. deshalb default access modifier
	}

	/**
	 * Liest den Inhalt aus diesem Register.
	 */
	public short read() {
		return val;
	}

	/**
	 * Schreibt die Daten in dieses Register. Maximal 16 Bit!
	 */
	public boolean write(int val) {
		this.val = (short) val;
		return ((int) this.val) != val;
	}

}
