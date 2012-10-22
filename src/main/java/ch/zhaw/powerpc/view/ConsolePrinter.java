package ch.zhaw.powerpc.view;

import ch.zhaw.powerpc.model.ControlUnit;

/**
 * 
 * @author Reto
 * 
 */
public class ConsolePrinter implements Printer {

	/**
	 * Ich glaube, die Ausgabe muesste jedes mal +/- 10 die gleiche Anzahl Zeichen haben. Deshalb waere es sinnvoll
	 * diese Zahl vorzumerken. Wuerde dann auch ohne berechnen gehen..
	 */
	private int sblen = 42;

	@Override
	public void print(ControlUnit controlUnit) {
		StringBuilder sb = new StringBuilder(sblen);

		sblen = sb.length();
		System.out.println(sb.toString());
	}

}
