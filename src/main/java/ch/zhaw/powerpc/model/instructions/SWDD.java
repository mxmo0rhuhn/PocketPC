package ch.zhaw.powerpc.model.instructions;

import ch.zhaw.powerpc.model.ControlUnit;
import ch.zhaw.powerpc.model.Register;

/**
 * In die über Adr und Adr + 1 adressierten Speicherzellen wird der Inhalt des Rgiesters xx (00 bis 11 für Akku, R-1,
 * R-2, R-3) geschrieben. Mit 10 Bit können 1KiB Speicher adressiert werden.
 * 
 * @author Reto
 * 
 */
public class SWDD extends AbstractInstruction {
	
	private final int register;
	
	private final int address;
	
	public SWDD(int register, int address) {
		checkRegisterBounds(register);
		checkAddressBoundsData(address);
		this.register = register;
		this.address = address;
	}

	@Override
	public int run(ControlUnit controlUnit) {
		Register register = controlUnit.getRegisters()[this.register];
		controlUnit.getMemory().writeData(this.address, register.read());
		return controlUnit.getProgramCounter() + 2;
	}

	@Override
	public char getBinary() {
		return genBin("0110", reg(this.register), adr(this.address));
	}
	
	@Override
	public String toString() {
		return "SWDD " + this.register + " #" + this.address;
	}

}
