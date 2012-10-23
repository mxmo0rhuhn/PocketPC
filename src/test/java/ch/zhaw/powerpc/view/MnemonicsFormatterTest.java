package ch.zhaw.powerpc.view;

import org.junit.Test;

import ch.zhaw.powerpc.view.impl.MnemonicFormatter;

import static org.junit.Assert.*;

public class MnemonicsFormatterTest {
	
	@Test
	public void shouldOutputNumbersWithPadding16() {
		MnemonicFormatter fmt = new MnemonicFormatter();
		
		assertEquals("               0", fmt.formatNumber(0));
		assertEquals("               1", fmt.formatNumber(1));
		assertEquals("               2", fmt.formatNumber(2));
		assertEquals("              42", fmt.formatNumber(42));
		assertEquals("              73", fmt.formatNumber(73));
		assertEquals("             169", fmt.formatNumber(169));
		assertEquals("            1024", fmt.formatNumber(1024));
		assertEquals("              -1", fmt.formatNumber(-1));
		assertEquals("              -2", fmt.formatNumber(-2));
		assertEquals("             -42", fmt.formatNumber(-42));
		assertEquals("             -73", fmt.formatNumber(-73));
		assertEquals("            -169", fmt.formatNumber(-169));
		assertEquals("           -1024", fmt.formatNumber(-1024));
	}

}
