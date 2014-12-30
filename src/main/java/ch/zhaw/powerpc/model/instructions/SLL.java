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
import ch.zhaw.powerpc.model.Register;

/**
 * Schieben logisch nach links: der Inhalt des Akkus wird um eine Stelle nach links geschoben; der Inhalt vom LSB (das
 * rechte Bit des Worts) wird mit 0 aufgefüllt, das MSb des MSB (das 1. Bit des Wortes) wird als Carry-Flag gesetzt.
 * 
 * @author Reto
 * 
 */
public class SLL extends AbstractInstruction {
	
	public SLL() {
		// keine Operanden
	}

	@Override
	public int run(ControlUnit controlUnit) {
		Register accu = controlUnit.getRegisters()[0];
		short curAccu = accu.read();
		controlUnit.getAlu().setCarryFlag(curAccu < 0);
		accu.write(curAccu << 1);
		return controlUnit.getProgramCounter() + 2;
	}

	@Override
	public char getBinary() {
		return genBin("0000110000000000");
	}
	
	@Override
	public String toString() {
		return "SLL";
	}

}
