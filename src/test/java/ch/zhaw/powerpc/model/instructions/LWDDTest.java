package ch.zhaw.powerpc.model.instructions;

import static ch.zhaw.powerpc.model.instructions.TestUtil.binEquals;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import ch.zhaw.powerpc.model.ControlUnit;
import ch.zhaw.powerpc.model.MainMemory;

public class LWDDTest {

	@Test
	public void testLoad() {
		short[] validData = new short[] { -32768, -11111, -73, -42, -11, -2, -1, 0, 1, 2, 3, 11, 42, 73, 128, 1023,
				1024, 11111, 32767 };
		for (short s : validData) {
			ControlUnit cu = new ControlUnit(new MainMemory());
			cu.getMemory().writeData(500, s);
			new LWDD(0, 500).run(cu);
			assertEquals(s, cu.getRegisters()[0].read());
		}
	}

	@Test
	public void binary1() {
		binEquals("0100000111110100", new LWDD(0, 500).getBinary());
		binEquals("0100010111110110", new LWDD(1, 502).getBinary());
		binEquals("0100101000101010", new LWDD(2, 554).getBinary());
		binEquals("0100111111111110", new LWDD(3, 1022).getBinary());
	}
}
