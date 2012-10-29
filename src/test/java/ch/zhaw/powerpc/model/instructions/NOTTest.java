package ch.zhaw.powerpc.model.instructions;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import static ch.zhaw.powerpc.model.instructions.TestUtil.*;

import ch.zhaw.powerpc.model.ControlUnit;
import ch.zhaw.powerpc.model.MainMemory;
import ch.zhaw.powerpc.model.Register;

public class NOTTest {

	@Test
	public void invertZero() {
		ControlUnit cu = new ControlUnit(new MainMemory());
		Register accu = cu.getRegisters()[0];

		accu.write(0);
		new NOT().run(cu);
		assertEquals(-1, accu.read());
	}

	@Test
	public void invertOne() {
		ControlUnit cu = new ControlUnit(new MainMemory());
		Register accu = cu.getRegisters()[0];

		accu.write(-1);
		new NOT().run(cu);
		assertEquals(0, accu.read());
	}
	
	@Test
	public void invertZeroOne() {
		ControlUnit cu = new ControlUnit(new MainMemory());
		Register accu = cu.getRegisters()[0];

		accu.write(Short.parseShort("0101010101010101", 2));
		new NOT().run(cu);
		
		// 1010101010101010
		assertEquals(-21846, accu.read());
	}
	
	@Test
	public void testGetBinary() {
		NOT not = new NOT();
		binEquals("0000000010000000", not.getBinary());
	}
	
	@Test
	public void testToString() {
		NOT not = new NOT();
		assertEquals("NOT", not.toString());
	}

}
