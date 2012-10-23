package ch.zhaw.powerpc.view;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import ch.zhaw.powerpc.view.impl.MnemonicFormatter;

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
	
	@Test
	public void shouldOutputFixedLengthMnemonics() {
		MnemonicFormatter fmt = new MnemonicFormatter();
		
		assertEquals("ADD       0   ", fmt.formatInstruction(Integer.parseInt("0000001110000000", 2)));
		assertEquals("ADDD #16383   ", fmt.formatInstruction(Integer.parseInt("1011111111111111", 2)));
		assertEquals("END           ", fmt.formatInstruction(Integer.parseInt("0000000000000000", 2)));
	}
}
