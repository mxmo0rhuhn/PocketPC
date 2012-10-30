package ch.zhaw.powerpc.view;

import ch.zhaw.powerpc.model.ControlUnit;

/**
 * Klasse um den Status vom PocketPC auszugeben.
 * 
 * @author Reto
 * 
 */
public interface Printer {

	/**
	 * Printet den gesamten Inhalt der Control Unit nach Stern'scher Spezifikation:
	 * 
	 * <ul>
	 * <li>Befehlszähler, Befehlsregister</li>
	 * <li>Akkumulator, Carry-Bit</li>
	 * <li>Reg-1, Reg-2, Reg-3</li>
	 * <li>Optional: Alle Werte auch als Binär-Werte</li>
	 * <li>Der aktuell decodierte Befehl aus dem Befehlsregister (mnemonisch)</li>
	 * <li>5 Befehle vor bis 10 Befehle nach dem aktuellen Befehl</li>
	 * <li>Der Inhalt der Speicherzellen 500 bis 529 (wortweise)</li>
	 * <li>Auch für den Speicher: Alle Werte als Binär-Werte</li>
	 * <li>Die Anzahl der durchgeführten Befehle (zum Programmstart 0)</li>
	 * </ul>
	 * 
	 * @param controlUnit
	 */
	void print(ControlUnit controlUnit, int steps);

}
