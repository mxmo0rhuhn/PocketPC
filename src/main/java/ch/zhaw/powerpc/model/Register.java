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
 * Diese Klasse repräsentiert ein Register. Das Register selbst weiss nicht, welches Register es ist - das weiss nur die
 * ControlUnit.
 * 
 * @author Max / Reto
 * 
 */
public final class Register {

	private short val;

	Register() {
		// klasse muss nur von ControlUnit instanziert werden. deshalb default access modifier
	}

	/**
	 * Liest den Inhalt aus diesem Register.
	 */
	public short read() {
		return val;
	}

	/**
	 * Schreibt die Daten in dieses Register. Maximal 16 Bit!
	 */
	public boolean write(int val) {
		this.val = (short) val;
		return ((int) this.val) != val;
	}

	@Override
	public String toString() {
		return "val:  " + val;
	}

}
