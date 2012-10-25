package ch.zhaw.powerpc.model.instructions;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import ch.zhaw.powerpc.model.ControlUnit;

/**
 * In das Register mit der Nummer xx (00 bis 11 fuer Akku, R-1, R-2, R-3) wird der Inhalt der Speicherzellen Adr und Adr
 * + 1 (1 Wort = 2 Byte) geladen. Mit 10 Bit koennen 1KiB Speicher adressiert werden.
 * 
 * @author Reto
 * 
 */
public class LWDD extends AbstractInstruction {

	private final int register;

	private final int address;

	public LWDD(int register, int address) {
		checkRegisterBounds(register);
		checkAddressBoundsData(address);
		this.register = register;
		this.address = address;
	}

	@Override
	public int run(ControlUnit controlUnit) {
		byte upper = controlUnit.getMemory().read(this.address);
		byte lower = controlUnit.getMemory().read(this.address + 1);
		controlUnit.getRegisters()[this.register].write(combineBytes(upper, lower));
		return controlUnit.getProgramCounter() + 1;
	}

	@Override
	public String toString() {
		return "LWDD " + this.register + " #" + this.address;
	}
	
	@Override
	public char getBinary() {
		return genBin("0100", reg(this.register), adr(this.address));
	}

}
