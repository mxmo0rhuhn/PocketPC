package ch.zhaw.powerpc.model;

import org.junit.Assert;
import org.junit.Test;

public class ALUTest {

	@Test
	public void testOverFlowCarryFlag() {
		ALU alu = new ALU(new Register[] { new Register(), new Register(), new Register(), new Register() });
		for (int i = 0; i < 65535; i++) {
			alu.addToAccu(1);
			Assert.assertFalse("Carry Flag sollte erst nach 65536 gesetzt sein, ist aber schon bei: " + (i + 1),
					alu.isCarryFlag());
		}
		alu.addToAccu(1);
		Assert.assertTrue(alu.isCarryFlag());
	}
	

}
