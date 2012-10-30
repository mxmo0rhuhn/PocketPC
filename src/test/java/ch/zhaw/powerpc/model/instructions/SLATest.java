package ch.zhaw.powerpc.model.instructions;

import static ch.zhaw.powerpc.model.instructions.TestUtil.binEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import ch.zhaw.powerpc.model.ControlUnit;
import ch.zhaw.powerpc.model.MainMemory;

public class SLATest {
	
	@Test
	public void shouldSLADataNormal() {
		
		ControlUnit cu = new ControlUnit(new MainMemory());
		
		short[] validData = new short[] { 42 };
		cu.getMemory().writeData(500, validData[0]);
		new LWDD(0, 500).run(cu);
		new SLA().run(cu);
		Integer.toBinaryString(cu.getRegisters()[0].read());
		assertEquals("1010100", Integer.toBinaryString(cu.getRegisters()[0].read()));
	
	}
	
	
	// Carry Flag setzen in ALU , Vorzeichenbit bleibt, in letztes Bit wird 0 geschrieben
	
	@Test
	public void shouldSLADataNegativeNumber() {
		
		ControlUnit cu = new ControlUnit(new MainMemory());
		
		short[] validData = new short[] { -42 };
		cu.getMemory().writeData(500, validData[0]);
		new LWDD(0, 500).run(cu);
		new SLA().run(cu);
		Integer.toBinaryString(cu.getRegisters()[0].read());
		assertEquals("11111111111111111111111110101100", Integer.toBinaryString(cu.getRegisters()[0].read()));
	
	}
	
	@Test
	public void shouldSLADataNegativeNumberKeepPrefix() {
		
		ControlUnit cu = new ControlUnit(new MainMemory());
		
		//-1073741866 to Binary: 10111111111111111111111111010110
		int[] validData = new int[] { -1073741866 };
		cu.getMemory().writeData(500, (short)validData[0]);
		new LWDD(0, 500).run(cu);
		new SLA().run(cu);
		Integer.toBinaryString(cu.getRegisters()[0].read());
		assertEquals("11111111111111111111111110101100", Integer.toBinaryString(cu.getRegisters()[0].read()));
	
	}
	
	@Test
	public void shouldSetCarryFlagIfMsbIsOne() {
		ControlUnit cu = new ControlUnit(new MainMemory());
		cu.getRegisters()[0].write(-1);
		
		SLA sla = new SLA();
		sla.run(cu);
		
		assertTrue(cu.getAlu().isCarryFlag());
	}
	
	@Test
	public void testToString() {
		assertEquals("SLA", new SLA().toString());
	}
	
	@Test
	public void testGetBinary() {
		binEquals("0000100000000000", new SLA().getBinary());
	}

}
