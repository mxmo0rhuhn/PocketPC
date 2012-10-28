package ch.zhaw.powerpc.view;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import ch.zhaw.powerpc.view.impl.MnemonicFormatter;

public class MnemonicsFormatterTest {

	@Test
	public void shouldOutputNumbersWithPadding16() {
		MnemonicFormatter fmt = new MnemonicFormatter();

		assertEquals("               0", fmt.formatNumber(0, 16));
		assertEquals("               1", fmt.formatNumber(1, 16));
		assertEquals("               2", fmt.formatNumber(2, 16));
		assertEquals("              42", fmt.formatNumber(42, 16));
		assertEquals("              73", fmt.formatNumber(73, 16));
		assertEquals("             169", fmt.formatNumber(169, 16));
		assertEquals("            1024", fmt.formatNumber(1024, 16));
		assertEquals("              -1", fmt.formatNumber(-1, 16));
		assertEquals("              -2", fmt.formatNumber(-2, 16));
		assertEquals("             -42", fmt.formatNumber(-42, 16));
		assertEquals("             -73", fmt.formatNumber(-73, 16));
		assertEquals("            -169", fmt.formatNumber(-169, 16));
		assertEquals("           -1024", fmt.formatNumber(-1024, 16));
	}

}