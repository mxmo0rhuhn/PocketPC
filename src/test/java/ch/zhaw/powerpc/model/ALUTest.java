package ch.zhaw.powerpc.model;

import org.junit.Assert;
import org.junit.Test;

public class ALUTest {

	@Test
	public void testOverFlowCarryFlag() {
		ALU alu = new ALU(new Register[] { new Register(), new Register(), new Register(), new Register() });
		for (int i = 0; i < 32767; i++) {
			alu.addToAccu((short) 1);
			Assert.assertFalse("Carry Flag sollte erst nach 32769 gesetzt sein, ist aber schon bei: " + (i + 1),
					alu.isCarryFlag());
		}
		alu.addToAccu((short) 1);
		Assert.assertTrue(alu.isCarryFlag());
	}

}
