package ch.zhaw.powerpc.view;

import org.junit.Test;

import ch.zhaw.powerpc.view.impl.BinaryFormatter;

import static org.junit.Assert.*;

public class BinaryFormatterTest {

	@Test
	public void shouldOutputNumbersWithPadding16() {
		BinaryFormatter fmt = new BinaryFormatter();
		
		assertEquals("0000000000000000", fmt.formatNumber(0));
		assertEquals("0000000000000001", fmt.formatNumber(1));
		assertEquals("0000000000000010", fmt.formatNumber(2));
		assertEquals("0000000000101010", fmt.formatNumber(42));
		assertEquals("0000000000000073", fmt.formatNumber(73));
		assertEquals("0000000000000169", fmt.formatNumber(169));
		assertEquals("0000000000001024", fmt.formatNumber(1024));
		assertEquals("0000000000000000", fmt.formatNumber(-1));
		assertEquals("0000000000000000", fmt.formatNumber(-2));
		assertEquals("0000000000000000", fmt.formatNumber(-42));
		assertEquals("0000000000000000", fmt.formatNumber(-73));
		assertEquals("0000000000000000", fmt.formatNumber(-169));
		assertEquals("0000000000000000", fmt.formatNumber(-1024));
	}
}
