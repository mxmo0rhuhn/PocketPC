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
		this.carryFlag = checkOverflow(curAccu, number);
		int newAccu = curAccu + number;
		registers[0].write((short) newAccu);
	}

	public static boolean checkOverflow(short a, short b) {
		char[] aC = new StringBuilder(Integer.toBinaryString(a)).reverse().toString().toCharArray();
		char[] bC = new StringBuilder(Integer.toBinaryString(b)).reverse().toString().toCharArray();
		int uebertrag = 0;
		for (int i = 0; i < aC.length && i < bC.length; i++) {
			int sum = (aC[i] == '1' ? 1 : 0) + (bC[i] == '1' ? 1 : 0) + uebertrag;
			uebertrag = (sum > 1) ? 1 : 0;
			if (i == 15 && uebertrag == 1) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String toString() {
		return "carryFlag: " + (carryFlag ? 1 : 0);
	}

}
