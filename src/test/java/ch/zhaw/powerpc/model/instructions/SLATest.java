package ch.zhaw.powerpc.model.instructions;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import ch.zhaw.powerpc.model.ControlUnit;
import ch.zhaw.powerpc.model.MainMemory;

public class SLATest {
	
	@Test
	public void shouldSLAData() {
		
		ControlUnit cu = new ControlUnit(new MainMemory());
		
		short[] validData = new short[] { 42 };
		cu.getMemory().writeData(500, validData[0]);
		new LWDD(0, 500).run(cu);
		new SLA().run(cu);
		Integer.toBinaryString(cu.getRegisters()[0].read());
		assertEquals("1010100", Integer.toBinaryString(cu.getRegisters()[0].read()));

		
		
	}
	
	// Carry Flag setzen in ALU , Vorzeichenbit bleibt, in letztes Bit wird 0 geschrieben
	

}
