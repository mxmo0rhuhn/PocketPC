package ch.zhaw.powerpc.model;

import org.junit.Assert;
import org.junit.Test;

public class RegisterTest {

	@Test
	public void testMasking() {
		Register reg = new Register();
		reg.write(65536);
		Assert.assertEquals("Register should be 0 after setting it to 65536 since that's a 1 followed by 16 * 0", 0,
				reg.read());
	}
	
	@Test
	public void testWriteAndRead() {
		Register reg = new Register();
		for (int i = -32768; i < 32767; i++) {
			reg.write(i);
			Assert.assertEquals(i + " should not be masked.", i, reg.read());
		}
	}
}
