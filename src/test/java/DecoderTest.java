import org.junit.Assert;
import org.junit.Test;

import ch.zhaw.powerpc.model.Decoder;

public class DecoderTest {

	@Test
	public void decodeCLR() {
		Assert.assertEquals("CLR 0", d("0000001010000000"));
		Assert.assertEquals("CLR 1", d("0000011011010101"));
		Assert.assertEquals("CLR 2", d("0000101011111111"));
		Assert.assertEquals("CLR 3", d("0000111011110000"));
	}
	
	@Test
	public void decodeADD() {
		Assert.assertEquals("ADD 0", d("0000001110000000"));
		Assert.assertEquals("ADD 1", d("0000011111010101"));
		Assert.assertEquals("ADD 2", d("0000101111111111"));
		Assert.assertEquals("ADD 3", d("0000111111110000"));
	}

	@Test
	public void decodeADDD() {
		Assert.assertEquals("ADDD #0", d("1000000000000000"));
		Assert.assertEquals("ADDD #1", d("1000000000000001"));
		Assert.assertEquals("ADDD #1000", d("1000001111101000"));
		Assert.assertEquals("ADDD #12345", d("1011000000111001"));
		Assert.assertEquals("ADDD #16383", d("1011111111111111"));
		Assert.assertEquals("ADDD #-1", d("1111111111111111"));
		Assert.assertEquals("ADDD #-65", d("1111111110111111"));
		Assert.assertEquals("ADDD #-666", d("1111110101100110"));
		Assert.assertEquals("ADDD #-10923", d("1101010101010101"));
		Assert.assertEquals("ADDD #-13654", d("1100101010101010"));
		Assert.assertEquals("ADDD #-15888", d("1100000111110000"));
		Assert.assertEquals("ADDD #-16384", d("1100000000000000"));
	}

	private static String d(String bin) {
		if (bin.length() != 16) {
			throw new IllegalArgumentException("I will only ever decode 16 Bit Strings");
		}
		return Decoder.instructionDecode(Integer.parseInt(bin, 2)).toString();
	}

}
