package ch.zhaw.powerpc.model.instructions;

import static ch.zhaw.powerpc.model.instructions.TestUtil.binEquals;
import static ch.zhaw.powerpc.model.instructions.TestUtil.initCU;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import ch.zhaw.powerpc.model.ControlUnit;

public class LWDDTest {

	@Test
	public void testLoad() {
		ControlUnit cu = initCU(new int[] { 504, 0, 505, 1 });
		new LWDD(0, 504).run(cu);
		assertEquals(1, cu.getRegisters()[0].read());
	}

	@Test
	public void testLoad2() {
		ControlUnit cu = initCU(new int[] { 504, 1, 505, 1 });
		new LWDD(0, 504).run(cu);
		assertEquals(257, cu.getRegisters()[0].read());
	}

	@Test
	public void testLoad3() {
		ControlUnit cu = initCU(new int[] { 504, 255, 505, 255 });
		new LWDD(0, 504).run(cu);
		assertEquals(-1, cu.getRegisters()[0].read());
	}

	@Test
	public void testLoad4() {
		ControlUnit cu = initCU(new int[] { 504, 0, 505, 0 });
		new LWDD(0, 504).run(cu);
		assertEquals(0, cu.getRegisters()[0].read());
	}

	@Test
	public void testLoad5() {
		ControlUnit cu = initCU(new int[] { 504, 0, 505, 255 });
		new LWDD(0, 504).run(cu);
		assertEquals(255, cu.getRegisters()[0].read());
	}

	@Test
	public void testLoad6() {
		// 10000000 + 11111111
		ControlUnit cu = initCU(new int[] { 506, -128, 507, -1 });
		new LWDD(0, 506).run(cu);
		assertEquals(-32513, cu.getRegisters()[0].read());
	}

	@Test
	public void testLoad7() {
		// 11111111 + 11111111
		ControlUnit cu = initCU(new int[] { 506, -1, 507, -1 });
		new LWDD(0, 506).run(cu);
		assertEquals(-1, cu.getRegisters()[0].read());
	}

	@Test
	public void testLoad8() {
		// 10000000 + 00000000
		ControlUnit cu = initCU(new int[] { 506, -128, 507, 0 });
		new LWDD(0, 506).run(cu);
		assertEquals(-32768, cu.getRegisters()[0].read());
	}

	@Test
	public void binary1() {
		binEquals("0100000111110100", new LWDD(0, 500).getBinary());
		binEquals("0100010111110101", new LWDD(1, 501).getBinary());
		binEquals("0100101000101011", new LWDD(2, 555).getBinary());
		binEquals("0100111111111111", new LWDD(3, 1023).getBinary());
	}
}
