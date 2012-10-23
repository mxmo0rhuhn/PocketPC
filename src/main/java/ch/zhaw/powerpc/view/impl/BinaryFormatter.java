package ch.zhaw.powerpc.view.impl;

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
	public String formatNumber(int n) {
		char[] bin = Integer.toBinaryString(n).toCharArray();
		char[] resArr = new char[16];
		// Fuelle vorne mit Nullen
		for (int i = 0; i < 16 - bin.length; i++) {
			resArr[i] = '0';
		}
		System.arraycopy(bin, 0, resArr, (16 - bin.length), bin.length);
		return String.valueOf(resArr);
	}

	@Override
	public String formatInstruction(int n) {
		// TODO Auto-generated method stub
		return null;
	}

}
