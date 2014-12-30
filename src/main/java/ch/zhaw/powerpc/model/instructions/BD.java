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

/**
 * 
 */
package ch.zhaw.powerpc.model.instructions;

import ch.zhaw.powerpc.model.ControlUnit;

/**
 * Verzweige an die durch den Operanden angegebene Speicheradresse. Mit 10 Bit können 1KiB Speicher adressiert werden.
 * 
 * @author Max
 * 
 */
public class BD extends AbstractInstruction {

	private final int address;

	/**
	 * Erstellt einen unbedingten Sprungbefehl auf eine Speicheradresse
	 * 
	 * @param register
	 *            eine Adresse auf die gesprungen werden soll ... ACHTUNG es wird keineswegs geprüft ob
	 *            der Inhalt des Speichers Sinn ergibt für den Sprung => dies kann schnell im NIRVANA enden... höchst
	 *            kritisch ...
	 */
	public BD(int address) {
		checkAddressBoundsInstruction(address);
		this.address = address;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.zhaw.powerpc.model.instructions.Instruction#run(ch.zhaw.powerpc.model.ControlUnit)
	 */
	@Override
	public int run(ControlUnit controlUnit) {
		return (int) address;
	}
	
	@Override
	public String toString() {
		return "BD #" + this.address;
	}

	@Override
	public char getBinary() {
		return genBin("00100", "0", adr(this.address));
	}
}
