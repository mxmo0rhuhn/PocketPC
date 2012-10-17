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

	public void addToAccu(int number) {
		int curAccu = registers[0].read();
		int newAccu = curAccu + number;
		if (overflow(newAccu)) {
			carryFlag = true;
			newAccu = mask(newAccu);
		}
		registers[0].write(newAccu);
	}
	
	private static boolean overflow(int val) {
		return (val | 0xFF) != 0;
	}
	
	private static int mask(int val) {
		return val & 0xFF;
	}

	public void resetCarryFlag() {
		this.carryFlag = false;
	}

	// operationen wie z.B. addieren von zwei character arrays, shifts usw
	// Diese Operationen werden by-need implementiert, d.h. sobald eine Instruction ein Befehl benötigt, implementiert
	// diejenige Person, die die Instruction programmier, auch die arithmetische/logischie Operation hier. Hier ist es
	// besonders wichtig, dass wir die Operationen GUT Unit testen!

}
