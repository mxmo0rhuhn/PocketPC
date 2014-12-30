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
 * Schieben arithmetisch nach links: der Inalt des Akkus wird um eine STelle nach links verschoben;
 * der Inhalt vom 2.Bit des MSB (das 2. Bit des Wortes) wird als Carry Flag gesetzt, Dabei bleibt 
 * das MSb des MSB (Vorzeichenbit) erhalten. In das LSb des LSB (das letzte Bit des Wortes) wird 
 * eine 0 geschrieben.
 * 
 * @author des
 *
 */

public class SLA extends AbstractInstruction{
	public SLA() {
		// keine Operanden 
	}

	@Override
	public int run(ControlUnit controlUnit) {
		Register akku = controlUnit.getRegisters()[0];
		short curAkku = akku.read();
		controlUnit.getAlu().setCarryFlag((curAkku & 16384) > 0);
		akku.write(curAkku << 1);
		
		return controlUnit.getProgramCounter() + 2;
	}

	@Override
	public char getBinary() {		
		return genBin("0000100000000000");
	}
	
	@Override
	public String toString() {
		return "SLA";
		
	}

}
