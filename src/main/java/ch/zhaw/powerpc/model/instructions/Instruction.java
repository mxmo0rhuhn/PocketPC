package ch.zhaw.powerpc.model.instructions;

import ch.zhaw.powerpc.model.ControlUnit;

/**
 * Eine Instruktion ist ein Befehl für unseren PowerPC. Pro Befehl wird ein Objekt instanziert, welcher dann anhand der
 * ControlUnit ausgeführt werden kann (Methode run).
 * 
 * @author Max / Reto
 * 
 */
public interface Instruction {

	/**
	 * Zu diesem Zeitpunkt muss die Instruktion wissen, welche Operanden Sie benötigt und diese entsprechend aus dem
	 * Hauptspeicher lesen können. Auf den Hauptspeicher kann über die ControlUnit zugegriffen werden.
	 * 
	 * Danach werden allfällige Werte aus dem Register gelesen (über die ControlUnit).
	 * 
	 * Schlussendlich wird die eigentliche Operation mit den Operanden ausgeführt.
	 * 
	 * @param controlUnit
	 *            Für Zugriff auf Hauptspeicher, Program Counter, Register, usw
	 * @return Der neue (absolute!) Programm Counter
	 */
	int run(ControlUnit controlUnit);
}
