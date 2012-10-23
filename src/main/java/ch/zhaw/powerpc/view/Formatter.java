package ch.zhaw.powerpc.view;

/**
 * 
 * Abstraktion fuer formatierte Ausgabe
 * 
 * @author Reto
 *
 */
public interface Formatter {
	
	/**
	 * Formatiert eine Nummer zur Ausgabe
	 */
	public String formatNumber(int n);
	
	/**
	 * Formatiert eine Instruktion zur Ausgabe
	 */
	public String formatInstruction(int n);

}
