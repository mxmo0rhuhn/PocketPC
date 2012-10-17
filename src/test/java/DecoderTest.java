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
	public void decodeADDD() {
		Assert.assertEquals("ADDD #0", d("1000000000000000"));
		Assert.assertEquals("ADDD #1", d("1000000000000001"));
		Assert.assertEquals("ADDD #999", d("1011111111111111"));
		Assert.assertEquals("ADDD #-999", d("1100000000000000"));
		Assert.assertEquals("ADDD #-1", d("1111111111111111"));
	}

	private static String d(String bin) {
		return Decoder.instructionDecode(Integer.parseInt(bin, 2)).toString();
	}

}
