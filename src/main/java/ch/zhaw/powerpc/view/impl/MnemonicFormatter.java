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

package ch.zhaw.powerpc.view.impl;

import ch.zhaw.powerpc.view.Formatter;

/**
 * Formatiert die Nummern im Dezimalformat und Instruktionen Mnemonisch.
 * 
 * @author Reto
 * 
 */
public class MnemonicFormatter implements Formatter {

	@Override
	public String formatNumber(int n, int chars) {
		return String.format("%"+chars+"d", n);
	}

}
