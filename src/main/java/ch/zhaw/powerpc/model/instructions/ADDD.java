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
 * Addition zweier 16-Bit-Zahlen (Zahl im Akku und Zahl im Register 'Rxx'; 00 bis 11 für Akku, R-1, R-2, R-3) im 2-er
 * Komplement; bei Überlauf wird das Carry-Flag gesetzt (=1), sonst auf den Wert 0.
 * 
 * @author Reto
 * 
 */
public class ADDD extends AbstractInstruction {

	private final short number;

	public ADDD(int number) {
		this.number = (short) number;
	}

	@Override
	public int run(ControlUnit controlUnit) {
		controlUnit.getAlu().addToAccu(this.number);
		return controlUnit.getProgramCounter() + 2;
	}

	@Override
	public String toString() {
		return "ADDD #" + this.number;
	}

	@Override
	public char getBinary() {
		return genBin("1", num(this.number));
	}

}
