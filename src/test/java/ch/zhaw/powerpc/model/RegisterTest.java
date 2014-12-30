/*
 * Copyright (c) 2012 - Reto Hablützel, Max Schrimpf, Désirée Sacher
 *
 * Licensed under the EUPL, Version 1.1 or – as soon they will be approved by the European Commission - subsequent versions of the EUPL (the "Licence");
 * You may not use this work except in compliance with the Licence.
 * You may obtain a copy of the Licence at:
 *
 * https://joinup.ec.europa.eu/software/page/eupl5
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the Licence is distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Licence for the specific language governing permissions and limitations under the Licence.
 */

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

		reg.write(66666);
		Assert.assertEquals(1130, reg.read());

		reg.write(666666);
		Assert.assertEquals(11306, reg.read());

		reg.write(6666666);
		Assert.assertEquals(-18006, reg.read());

		reg.write(66666666);
		Assert.assertEquals(16554, reg.read());
	}

	@Test
	public void testWriteAndRead() {
		Register reg = new Register();
		// Zahlen in diesem Bereich sollten nicht maskiert werden
		for (int i = -32768; i < 32767; i++) {
			reg.write(i);
			Assert.assertEquals(i + " should not be masked.", i, reg.read());
		}
	}

}
