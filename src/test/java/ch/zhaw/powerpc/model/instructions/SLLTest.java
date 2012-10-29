package ch.zhaw.powerpc.model.instructions;

import static ch.zhaw.powerpc.model.instructions.TestUtil.binEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import ch.zhaw.powerpc.model.ControlUnit;
import ch.zhaw.powerpc.model.MainMemory;

public class SLLTest {

	@Test
	public void shouldShiftAllPositions() {
		ControlUnit cu = new ControlUnit(new MainMemory());
		cu.getRegisters()[0].write(-21846);
		
		SLL sll = new SLL();
		sll.run(cu);
		
		assertEquals(21845, cu.getRegisters()[0].read());
	}
	
	@Test
	public void shouldShiftAllPositions2() {
		ControlUnit cu = new ControlUnit(new MainMemory());
		cu.getRegisters()[0].write(21845);
		
		SLL sll = new SLL();
		sll.run(cu);
		
		assertEquals(-21846, cu.getRegisters()[0].read());
	}
	
	@Test
	public void shouldFillZeroesToLsb2() {
		ControlUnit cu = new ControlUnit(new MainMemory());
		cu.getRegisters()[0].write(-1);
		
		SLL sll = new SLL();
		sll.run(cu);
		
		assertEquals(-2, cu.getRegisters()[0].read());
	}
	
	@Test
	public void shouldFillZeroesToLsb() {
		ControlUnit cu = new ControlUnit(new MainMemory());
		cu.getRegisters()[0].write(0);
		
		SLL sll = new SLL();
		sll.run(cu);
		
		assertEquals(0, cu.getRegisters()[0].read());
	}
	
	@Test
	public void shouldNotSetCarryFlatIfMsbIsZero() {
		ControlUnit cu = new ControlUnit(new MainMemory());
		cu.getRegisters()[0].write(0);
		
		SLL sll = new SLL();
		sll.run(cu);
		
		assertFalse(cu.getAlu().isCarryFlag());
	}

	@Test
	public void shouldSetCarryFlagIfMsbIsOne() {
		ControlUnit cu = new ControlUnit(new MainMemory());
		cu.getRegisters()[0].write(-1);
		
		SLL sll = new SLL();
		sll.run(cu);
		
		assertTrue(cu.getAlu().isCarryFlag());
	}
	
	@Test
	public void testToString() {
		assertEquals("SLL", new SLL().toString());
	}
	
	@Test
	public void testGetBinary() {
		binEquals("0000110000000000", new SLL().getBinary());
	}

}
