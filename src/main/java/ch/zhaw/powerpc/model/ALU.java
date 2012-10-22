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
		int newAccu = curAccu + number;
		this.carryFlag = isOverflow(newAccu);
		registers[0].write((short) newAccu);
	}

	private static boolean isOverflow(int val) {
		return ((int) (short) val) != val;
	}

}
