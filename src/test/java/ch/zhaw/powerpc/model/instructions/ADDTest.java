package ch.zhaw.powerpc.model.instructions;

import static ch.zhaw.powerpc.model.instructions.TestUtil.binEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import ch.zhaw.powerpc.model.ControlUnit;
import ch.zhaw.powerpc.model.MainMemory;

public class ADDTest {
	
	@Test
	public void identityInZero() {
		ControlUnit cu = new ControlUnit(new MainMemory());
		cu.getRegisters()[0].write(0);
		
		ADD add = new ADD(0);
		add.run(cu);
		
		
		assertFalse(cu.getAlu().isCarryFlag());
		assertEquals(0, cu.getRegisters()[0].read());
	}

	@Test
	public void identityInOne() {
		ControlUnit cu = new ControlUnit(new MainMemory());
		cu.getRegisters()[1].write(0);
		
		ADD add = new ADD(1);
		add.run(cu);
		
		assertFalse(cu.getAlu().isCarryFlag());
		assertEquals(0, cu.getRegisters()[1].read());
	}

	@Test
	public void identityInTwo() {
		ControlUnit cu = new ControlUnit(new MainMemory());
		cu.getRegisters()[2].write(0);
		
		ADD add = new ADD(2);
		add.run(cu);
		
		assertFalse(cu.getAlu().isCarryFlag());
		assertEquals(0, cu.getRegisters()[2].read());
	}

	@Test
	public void identityInThree() {
		ControlUnit cu = new ControlUnit(new MainMemory());
		cu.getRegisters()[3].write(0);
		
		ADD add = new ADD(3);
		add.run(cu);
		
		assertFalse(cu.getAlu().isCarryFlag());
		assertEquals(0, cu.getRegisters()[3].read());
	}
	
	@Test
	public void addZero() {
		ControlUnit cu = new ControlUnit(new MainMemory());
		cu.getRegisters()[0].write(0);
		cu.getRegisters()[3].write(0);
		
		ADD add = new ADD(3);
		add.run(cu);
		
		assertFalse(cu.getAlu().isCarryFlag());
		assertEquals(0, cu.getRegisters()[0].read());
	}
	
	@Test
	public void addOne() {
		ControlUnit cu = new ControlUnit(new MainMemory());
		cu.getRegisters()[0].write(0);
		cu.getRegisters()[3].write(1);
		
		ADD add = new ADD(3);
		add.run(cu);
		
		assertFalse(cu.getAlu().isCarryFlag());
		assertEquals(1, cu.getRegisters()[0].read());
	}
	
	@Test
	public void addTwo() {
		ControlUnit cu = new ControlUnit(new MainMemory());
		cu.getRegisters()[0].write(0);
		cu.getRegisters()[2].write(2);
		
		ADD add = new ADD(2);
		add.run(cu);
		
		assertFalse(cu.getAlu().isCarryFlag());
		assertEquals(2, cu.getRegisters()[0].read());
	}
	
	@Test
	public void addFortyTwo() {
		ControlUnit cu = new ControlUnit(new MainMemory());
		cu.getRegisters()[0].write(0);
		cu.getRegisters()[2].write(42);
		
		ADD add = new ADD(2);
		add.run(cu);
		
		assertFalse(cu.getAlu().isCarryFlag());
		assertEquals(42, cu.getRegisters()[0].read());
	}
	
	@Test
	public void testNegative() {
		ControlUnit cu = new ControlUnit(new MainMemory());
		cu.getRegisters()[0].write(-1);
		cu.getRegisters()[2].write(1);
		
		ADD add = new ADD(2);
		add.run(cu);
		
		assertFalse(cu.getAlu().isCarryFlag());
		assertEquals(0, cu.getRegisters()[0].read());
	}

	@Test
	public void addSeventyThreeToFortyTwo() {
		ControlUnit cu = new ControlUnit(new MainMemory());
		cu.getRegisters()[0].write(42);
		cu.getRegisters()[2].write(73);
		
		ADD add = new ADD(2);
		add.run(cu);
		
		assertFalse(cu.getAlu().isCarryFlag());
		assertEquals(115, cu.getRegisters()[0].read());
	}
	
	@Test
	public void testOverflow() {
		ControlUnit cu = new ControlUnit(new MainMemory());
		cu.getRegisters()[0].write(21000);
		cu.getRegisters()[2].write(21000);
		
		ADD add = new ADD(2);
		add.run(cu);
		
		assertFalse(cu.getAlu().isCarryFlag());
		assertEquals(-23536, cu.getRegisters()[0].read());
	}
	
	@Test
	public void testToString() {
		assertEquals("ADD 0", new ADD(0).toString());
		assertEquals("ADD 1", new ADD(1).toString());
		assertEquals("ADD 2", new ADD(2).toString());
		assertEquals("ADD 3", new ADD(3).toString());
	}
	
	@Test
	public void testBinary() {
		binEquals("0000001110000000", new ADD(0).getBinary());
		binEquals("0000011110000000", new ADD(1).getBinary());
		binEquals("0000101110000000", new ADD(2).getBinary());
		binEquals("0000111110000000", new ADD(3).getBinary());
	}

}
