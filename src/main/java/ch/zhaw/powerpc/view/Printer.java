/*
 * Copyright (c) 2012 - Reto Hablützel, Max Schrimpf, Désirée Sacher
 *
 * Licensed under the EUPL, Version 1.1 or – as soon they will be approved by the European Commission - subsequent versions of the EUPL (the "Licence");
 * You may not use this work except in compliance with the Licence.
 * You may obtain a copy of the Licence at:
 *
 * https://joinup.ec.europa.eu/software/page/eupl5
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the Licence is distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Licence for the specific language governing permissions and limitations under the Licence.
 */

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
