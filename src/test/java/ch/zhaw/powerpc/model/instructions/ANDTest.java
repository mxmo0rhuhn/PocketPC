package ch.zhaw.powerpc.model.instructions;

import static ch.zhaw.powerpc.model.instructions.TestUtil.binEquals;
import static org.junit.Assert.assertEquals;


import org.junit.Test;

import ch.zhaw.powerpc.model.ControlUnit;
import ch.zhaw.powerpc.model.MainMemory;



public class ANDTest {
		
	@Test
	public void shouldAndBitwise() {
		
		ControlUnit cu = new ControlUnit(new MainMemory());
		
		short[] validData = new short[] { 42, 1023};
			cu.getMemory().writeData(500, validData[0]);
			cu.getMemory().writeData(502, validData[1]);
			new LWDD(0, 500).run(cu);
			new LWDD(1, 502).run(cu);
			new AND(1).run(cu);
			Integer.toBinaryString(cu.getRegisters()[0].read());
			assertEquals("101010", Integer.toBinaryString(cu.getRegisters()[0].read()));
	
	}
	
	@Test
	public void shouldGetBinary() {
		binEquals("0000011000010010", (new AND(1).getBinary()));
		binEquals("0000101000010010", (new AND(2).getBinary()));
		binEquals("0000111000010010", new AND(3).getBinary());
		binEquals("0000001000010010", new AND(0).getBinary());
		
	}
	

}
