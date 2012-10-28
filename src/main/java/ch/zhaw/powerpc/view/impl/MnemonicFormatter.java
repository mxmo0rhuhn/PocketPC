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
