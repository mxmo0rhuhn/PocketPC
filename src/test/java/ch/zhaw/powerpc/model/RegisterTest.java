package ch.zhaw.powerpc.model;

import org.junit.Assert;
import org.junit.Test;

public class RegisterTest {

	@Test
	public void testMasking() {
		Register accu = new Register();
		accu.write(65536);
		Assert.assertEquals("Accu should be 0 after setting it to 65536 since that's a 1 followed by 16 * 0", 0,
				accu.read());
	}
}
