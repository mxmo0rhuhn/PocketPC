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

package ch.zhaw.powerpc.view;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import ch.zhaw.powerpc.view.impl.BinaryFormatter;

public class BinaryFormatterTest {

	@Test
	public void shouldOutputNumbersWithPadding16() {
		BinaryFormatter fmt = new BinaryFormatter();

		assertEquals("0000000000000000", fmt.formatNumber(0, 16));
		assertEquals("0000000000000001", fmt.formatNumber(1, 16));
		assertEquals("0000000000000010", fmt.formatNumber(2, 16));
		assertEquals("0000000000101010", fmt.formatNumber(42, 16));
		assertEquals("0000000001001001", fmt.formatNumber(73, 16));
		assertEquals("0000000010101001", fmt.formatNumber(169, 16));
		assertEquals("0000010000000000", fmt.formatNumber(1024, 16));
		assertEquals("1111111111111111", fmt.formatNumber(-1, 16));
		assertEquals("1111111111111110", fmt.formatNumber(-2, 16));
		assertEquals("1111111111010110", fmt.formatNumber(-42, 16));
		assertEquals("1111111110110111", fmt.formatNumber(-73, 16));
		assertEquals("1111111101010111", fmt.formatNumber(-169, 16));
		assertEquals("1111110000000000", fmt.formatNumber(-1024, 16));
	}
}
