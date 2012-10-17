import org.junit.Assert;
import org.junit.Test;

import ch.zhaw.powerpc.model.Decoder;
import ch.zhaw.powerpc.model.instructions.ADDD;

public class DecoderTest {

	@Test
	public void decodeCLR() {
		// Assert.assertEquals(new CLR(0), Decoder.instructionDecode(p("0000001010000000")));
		// Assert.assertEquals(new CLR(1), Decoder.instructionDecode(p("0000011011010101")));
		// Assert.assertEquals(new CLR(2), Decoder.instructionDecode(p("0000101011111111")));
		// Assert.assertEquals(new CLR(3), Decoder.instructionDecode(p("0000111011110000")));
	}

	@Test
	public void decodeADDD() {
		Assert.assertEquals(new ADDD(0), Decoder.instructionDecode(p("1000000000000000")));
		Assert.assertEquals(new ADDD(1), Decoder.instructionDecode(p("1000000000000001")));
		Assert.assertEquals(new ADDD(999), Decoder.instructionDecode(p("1011111111111111")));
		Assert.assertEquals(new ADDD(-999), Decoder.instructionDecode(p("1100000000000000")));
		Assert.assertEquals(new ADDD(-1), Decoder.instructionDecode(p("1111111111111111")));

	}

	private static int p(String bin) {
		return Integer.parseInt(bin, 2);
	}

}
