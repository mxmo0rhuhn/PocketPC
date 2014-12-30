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

package ch.zhaw.powerpc.model.instructions;

import ch.zhaw.powerpc.model.ControlUnit;

/**
 * "Hilfsbefehl" für die Realisierung: Tatsächlich arbeitet der Prozessor ja immer weiter; er muss sogesehen in eine
 * Schleife eintreten, in der er keine Werte verändert und die er durch einen Interrupt (z. B. für einen
 * Programmneustart) wieder verlassen kann. Technisch wird das Ende realisiert indem der Befehlszähler auf -1 gesetzt
 * wird.
 * 
 * @author Max
 * 
 */
public class END extends AbstractInstruction {

	@Override
	public int run(ControlUnit controlUnit) {
		throw new NoMoreInstructionsException();
	}

	@Override
	public String toString() {
		return "END";
	}

	@Override
	public char getBinary() {
		return genBin("0000000000000000");
	}
}
