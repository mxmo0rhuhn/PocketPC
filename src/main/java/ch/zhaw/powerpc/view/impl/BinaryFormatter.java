package ch.zhaw.powerpc.view.impl;

import ch.zhaw.powerpc.model.instructions.AbstractInstruction;
import ch.zhaw.powerpc.view.Formatter;

/**
 * 
 * Formatiert die Ausgaben im Binaerformat
 * 
 * @author Reto
 * 
 */
public class BinaryFormatter implements Formatter {

	@Override
	public String formatNumber(int n, int nchars) {
		return AbstractInstruction.tailor(n, nchars);
	}
}
