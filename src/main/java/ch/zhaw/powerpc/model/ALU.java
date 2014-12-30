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

package ch.zhaw.powerpc.model;

/**
 * Diese Klasse repräsentiert die arithemtisch-logische Einheit.
 * 
 * @author Max / Reto
 * 
 */
public final class ALU {

	/**
	 * Das Carry Flag wird nur von der ALU gesetzt!
	 */
	private boolean carryFlag;

	private Register[] registers;

	public ALU(Register[] registers) {
		this.registers = registers;
		this.carryFlag = false;
	}

	public boolean isCarryFlag() {
		return carryFlag;
	}

	public void setCarryFlag(boolean newFlag) {
		this.carryFlag = newFlag;
	}

	public void addToAccu(short number) {
		short curAccu = registers[0].read();
		this.carryFlag = checkOverflow(curAccu, number);
		int newAccu = curAccu + number;
		registers[0].write((short) newAccu);
	}

	public static boolean checkOverflow(short a, short b) {
		char[] aC = new StringBuilder(Integer.toBinaryString(a)).reverse().toString().toCharArray();
		char[] bC = new StringBuilder(Integer.toBinaryString(b)).reverse().toString().toCharArray();
		int uebertrag = 0;
		for (int i = 0; i < 16; i++) {
			int sum = (i < aC.length && aC[i] == '1' ? 1 : 0) + (i < bC.length && bC[i] == '1' ? 1 : 0) + uebertrag;
			uebertrag = (sum > 1) ? 1 : 0;
			if (i == 15 && uebertrag == 1) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String toString() {
		return "carryFlag: " + (carryFlag ? 1 : 0);
	}

}
