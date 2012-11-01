package ch.zhaw.powerpc.model.instructions;

import static ch.zhaw.powerpc.model.instructions.TestUtil.binEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

import ch.zhaw.powerpc.model.ControlUnit;
import ch.zhaw.powerpc.model.MainMemory;

public class SRATest {

	@Test
	public void shouldSRADataNormal() {
		
		ControlUnit cu = new ControlUnit(new MainMemory());
		
		short[] validData = new short[] { 32767 };
		cu.getMemory().writeData(500, validData[0]);
		new LWDD(0, 500).run(cu);
		new SRA().run(cu);
		Integer.toBinaryString(cu.getRegisters()[0].read());
		assertEquals("11111111111111", Integer.toBinaryString(cu.getRegisters()[0].read()));
	
	}
	
	
	// Carry Flag setzen in ALU , Vorzeichenbit bleibt, in letztes Bit wird 0 geschrieben
	
	@Test
	public void shouldSRADataNegativeNumberPrefixTest() {
		
		ControlUnit cu = new ControlUnit(new MainMemory());
		
		short[] validData = new short[] { -8198 };
		cu.getMemory().writeData(500, validData[0]);;
		new LWDD(0, 500).run(cu);
		new SRA().run(cu);

		assertEquals("11111111111111111110111111111101", Integer.toBinaryString(cu.getRegisters()[0].read()));
	
	}
	
	
	@Test
	public void shouldSetCarryFlagIfLsbIsOne() {
		ControlUnit cu = new ControlUnit(new MainMemory());
		cu.getRegisters()[0].write(-1);
		
		SRA sra = new SRA();
		sra.run(cu);
		
		assertTrue(cu.getAlu().isCarryFlag());
	}
	
	@Test
	public void shouldSetCarryFlatForOddNumbers() {
		ControlUnit cu = new ControlUnit(new MainMemory());
		SRA sra = new SRA();
		for (int i = 0; i < 33333; i++) {
			cu.getRegisters()[0].write(i);
			sra.run(cu);
			if (i % 2 == 0) {
				assertFalse(cu.getAlu().isCarryFlag());
			} else {
				assertTrue(cu.getAlu().isCarryFlag());				
			}
		}
	}
	
	@Test
	public void testToString() {
		assertEquals("SRA", new SRA().toString());
	}
	
	@Test
	public void testGetBinary() {
		binEquals("0000101000000000", new SRA().getBinary());
	}

	
}
