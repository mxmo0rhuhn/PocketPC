package ch.zhaw.powerpc.model.instructions;

import static ch.zhaw.powerpc.model.instructions.TestUtil.initCU;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import ch.zhaw.powerpc.model.ControlUnit;

public class LWDDTest {

	@Test
	public void testLoad() {
		ControlUnit cu = initCU(new int[]{504, 0, 505, 1});
		new LWDD(0, 504).run(cu);
		assertEquals(1, cu.getRegisters()[0].read());
	}
	
	@Test
	public void testLoad2() {
		ControlUnit cu = initCU(new int[]{504, 1, 505, 1});
		new LWDD(0, 504).run(cu);
		assertEquals(257, cu.getRegisters()[0].read());
	}
	
	@Test
	public void testLoad3() {
		ControlUnit cu = initCU(new int[]{504, 255, 505, 255});
		new LWDD(0, 504).run(cu);
		assertEquals(-1, cu.getRegisters()[0].read());
	}
	
	@Test
	public void testLoad4() {
		ControlUnit cu = initCU(new int[]{504, 0, 505, 0});
		new LWDD(0, 504).run(cu);
		assertEquals(0, cu.getRegisters()[0].read());
	}
	
	@Test
	public void testLoad5() {
		ControlUnit cu = initCU(new int[]{504, 0, 505, 255});
		new LWDD(0, 504).run(cu);
		assertEquals(255, cu.getRegisters()[0].read());
	}
}
