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
