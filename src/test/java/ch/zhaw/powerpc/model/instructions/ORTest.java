package ch.zhaw.powerpc.model.instructions;

import org.junit.Test;
import static org.junit.Assert.*;
import static ch.zhaw.powerpc.model.instructions.TestUtil.*;

import ch.zhaw.powerpc.model.ControlUnit;
import ch.zhaw.powerpc.model.MainMemory;

public class ORTest {

	/**
	 * Akku und Register xx (00 bis 11 für Akku, R-1, R-2 bzw R-3) werden bitweise logisch mit ODe veknüpft.
	 * 
	 */

	@Test
	public void shouldOrSetAllBits() {
		ControlUnit cu = new ControlUnit(new MainMemory());
		cu.getRegisters()[0].write(-1);
		cu.getRegisters()[3].write(21845);

		OR or = new OR(2);
		or.run(cu);

		assertEquals(-1, cu.getRegisters()[0].read());
	}

	@Test
	public void shouldOrWithThirdRegister() {
		ControlUnit cu = new ControlUnit(new MainMemory());
		cu.getRegisters()[0].write(0);
		cu.getRegisters()[3].write(21845);

		OR or = new OR(2);
		or.run(cu);

		assertEquals(21845, cu.getRegisters()[0].read());
	}

	@Test
	public void shouldOrWithSecondRegister() {
		ControlUnit cu = new ControlUnit(new MainMemory());
		cu.getRegisters()[0].write(-1);
		cu.getRegisters()[2].write(-1);

		OR or = new OR(2);
		or.run(cu);

		assertEquals(-1, cu.getRegisters()[0].read());
	}

	@Test
	public void shouldOrWithFirstRegister() {
		ControlUnit cu = new ControlUnit(new MainMemory());
		cu.getRegisters()[0].write(0);
		cu.getRegisters()[1].write(-1);

		OR or = new OR(1);
		or.run(cu);

		assertEquals(-1, cu.getRegisters()[0].read());
	}

	@Test
	public void shouldLeaveZeroAsIs() {
		ControlUnit cu = new ControlUnit(new MainMemory());
		cu.getRegisters()[0].write(0);

		OR or = new OR(0);
		or.run(cu);

		assertEquals(0, cu.getRegisters()[0].read());
	}

	@Test
	public void shouldLeaveZeroAsIs2() {
		ControlUnit cu = new ControlUnit(new MainMemory());
		cu.getRegisters()[0].write(0);
		cu.getRegisters()[1].write(0);

		OR or = new OR(1);
		or.run(cu);

		assertEquals(0, cu.getRegisters()[0].read());
	}

	@Test
	public void shouldLeaveZeroAsIs3() {
		ControlUnit cu = new ControlUnit(new MainMemory());
		cu.getRegisters()[0].write(0);
		cu.getRegisters()[2].write(0);

		OR or = new OR(2);
		or.run(cu);

		assertEquals(0, cu.getRegisters()[0].read());
	}

	@Test
	public void shouldLeaveZeroAsIs4() {
		ControlUnit cu = new ControlUnit(new MainMemory());
		cu.getRegisters()[0].write(0);
		cu.getRegisters()[3].write(0);

		OR or = new OR(3);
		or.run(cu);

		assertEquals(0, cu.getRegisters()[0].read());
	}

	@Test
	public void testToString() {
		assertEquals("OR 0", new OR(0).toString());
		assertEquals("OR 1", new OR(1).toString());
		assertEquals("OR 2", new OR(2).toString());
		assertEquals("OR 3", new OR(3).toString());
	}

	@Test
	public void testGetBinary() {
		binEquals("0000001100000000", new OR(0).getBinary());
		binEquals("0000011100000000", new OR(1).getBinary());
		binEquals("0000101100000000", new OR(2).getBinary());
		binEquals("0000111100000000", new OR(3).getBinary());
	}

}
