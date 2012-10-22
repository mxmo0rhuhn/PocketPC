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
	 *  <li>Befehlszähler, Befehlsregister</li>
	 *  <li>Akkumulator, Carry-Bit</li>
	 *  <li>Reg-1, Reg-2, Reg-3</li>
	 *  <li>Optional: Alle Werte auch als Binär-Werte</li>
	 *  <li>Der aktuell decodierte Befehl aus dem Befehlsregister (mnemonisch)</li>
	 * </ul>
	 * 
	 * @param controlUnit
	 */
	void print(ControlUnit controlUnit);

}
