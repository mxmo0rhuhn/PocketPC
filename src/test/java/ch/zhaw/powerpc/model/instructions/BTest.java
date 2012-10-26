/**
 * 
 */
package ch.zhaw.powerpc.model.instructions;

import static ch.zhaw.powerpc.model.instructions.TestUtil.initCU;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import ch.zhaw.powerpc.model.ControlUnit;

/**
 * Testet den unbedingten Sprung
 * 
 * @author Max
 * 
 */
public class BTest {

	@Test
	public void testJumpRegisterOne() {

		ControlUnit cu = initCU(new int[] { 100, });
		cu.getRegisters()[01].write(121);

		assertEquals(100, cu.getProgramCounter());

		new B(01).run(cu);

		assertEquals(121, cu.getProgramCounter());
	}

	@Test
	public void testJumpRegisterTwo() {

		ControlUnit cu = initCU(new int[] {});
		cu.getRegisters()[02].write(133);

		assertEquals(100, cu.getProgramCounter());

		assertEquals(133, cu.getProgramCounter());
	}
}
