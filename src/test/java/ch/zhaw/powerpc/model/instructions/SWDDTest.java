package ch.zhaw.powerpc.model.instructions;

import org.junit.Test;

import static org.junit.Assert.*;
import static ch.zhaw.powerpc.model.instructions.TestUtil.*;

import ch.zhaw.powerpc.model.ControlUnit;
import ch.zhaw.powerpc.model.MainMemory;

public class SWDDTest {

	@Test(expected=InvalidInstructionException.class)
	public void shouldNotAcceptOddMemoryAddress() {
		new SWDD(0, 501);
	}
	
	@Test(expected=InvalidInstructionException.class)
	public void shouldNotAcceptInvalidRegister() {
		new SWDD(4, 502);
	}
	
	@Test
	public void shouldWriteFirstRegisterAtSpecifiedPosition() {
		ControlUnit cu = new ControlUnit(new MainMemory());
		cu.getRegisters()[1].write(42);
		
		SWDD swdd = new SWDD(1, 500);
		swdd.run(cu);
		
		assertEquals(42, cu.getMemory().readData(500));
	}
	
	@Test
	public void shouldWriteSecondRegisterAtSpecifiedPosition() {
		ControlUnit cu = new ControlUnit(new MainMemory());
		cu.getRegisters()[2].write(1111);
		
		SWDD swdd = new SWDD(2, 500);
		swdd.run(cu);
		
		assertEquals(1111, cu.getMemory().readData(500));
	}
	
	@Test
	public void shouldWriteThirdRegisterAtSpecifiedPosition() {
		ControlUnit cu = new ControlUnit(new MainMemory());
		cu.getRegisters()[3].write(-1);
		
		SWDD swdd = new SWDD(3, 500);
		swdd.run(cu);
		
		assertEquals(-1, cu.getMemory().readData(500));
	}
	
	@Test
	public void shouldWriteAkkuAtSpecifiedPosition() {
		ControlUnit cu = new ControlUnit(new MainMemory());
		cu.getRegisters()[0].write(73);
		
		SWDD swdd = new SWDD(0, 500);
		swdd.run(cu);
		
		assertEquals(73, cu.getMemory().readData(500));
	}
	
	@Test
	public void testToString() {
		assertEquals("SWDD 0 #500", new SWDD(0, 500).toString());
		assertEquals("SWDD 1 #502", new SWDD(1, 502).toString());
		assertEquals("SWDD 2 #504", new SWDD(2, 504).toString());
		assertEquals("SWDD 3 #506", new SWDD(3, 506).toString());
	}
	
	@Test
	public void testGetBinary() {
		binEquals("0110000111110100", new SWDD(0, 500).getBinary());
		binEquals("0110010111110110", new SWDD(1, 502).getBinary());
		binEquals("0110100111111000", new SWDD(2, 504).getBinary());
		binEquals("0110110111111010", new SWDD(3, 506).getBinary());
	}
}
