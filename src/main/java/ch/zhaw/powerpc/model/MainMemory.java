package ch.zhaw.powerpc.model;

import static ch.zhaw.powerpc.model.ControlUnit.BUS_SIZE;

/**
 * Diese Klasse repräsentiert den Hauptspeicher (RAM). Hier liegen Befehle und Daten.
 * 
 * @author Max / Reto
 * 
 */
public final class MainMemory {

	private final char[][] data;

	/**
	 * Die Klasse wird mit einem initialen Set an Daten instanziert. Hier muss z.B. schon das Programm in den Daten
	 * sein, sowie allenfalls notwendige Daten für das Programm.
	 * 
	 * Achtung: Das Array muss hier schon die finale grösse haben, weil Arrays können später in der Grösse nicht mehr verändert werden.
	 */
	public MainMemory(char[][] initialData) {
		this.data = initialData;
	}

	/**
	 * Lese einen Wert im Speicher von eine bestimmten Adresse.
	 * 
	 * @throws IllegalArgumentException
	 *             wenn die Adresse zu gross oder zu klein ist.
	 */
	public char[] read(int address) {
		checkBounds(address);
		return this.data[address];
	}

	/**
	 * Schreibe einen Wert in den Speicher an einer bestimmten Adresse.
	 * 
	 * @throws IllegalArgumentException
	 *             wenn die Adresse zu gross oder zu klein ist.
	 */
	public void write(int address, char[] data) {
		checkBounds(address);
		if (data.length > BUS_SIZE) {
			throw new IllegalStateException("Data Length must not exceed " + BUS_SIZE);
		}
		this.data[address] = data;
	}

	private void checkBounds(int address) {
		if (address < 0) {
			throw new IllegalArgumentException("Memory Address must be positive");
		} else if (this.data.length < address) {
			throw new IllegalArgumentException("Memory Address [" + address + "] does not exist!");
		}
	}
}
