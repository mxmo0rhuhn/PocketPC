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
 * In das Register mit der Nummer xx (00 bis 11 fuer Akku, R-1, R-2, R-3) wird der Inhalt der Speicherzellen Adr und Adr
 * + 1 (1 Wort = 2 Byte) geladen. Mit 10 Bit koennen 1KiB Speicher adressiert werden.
 * 
 * @author Reto
 * 
 */
public class LWDD extends AbstractInstruction {

	private final int register;

	private final int address;

	public LWDD(int register, int address) {
		checkRegisterBounds(register);
		checkAddressBoundsData(address);
		this.register = register;
		this.address = address;
	}

	@Override
	public int run(ControlUnit controlUnit) {
		int val = controlUnit.getMemory().readData(this.address);
		controlUnit.getRegisters()[this.register].write(val);
		return controlUnit.getProgramCounter() + 2;
	}

	@Override
	public String toString() {
		return "LWDD " + this.register + " #" + this.address;
	}

	@Override
	public char getBinary() {
		return genBin("0100", reg(this.register), adr(this.address));
	}

}
