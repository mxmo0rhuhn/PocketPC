package ch.zhaw.powerpc.controller;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import ch.zhaw.powerpc.model.instructions.InvalidInstructionException;

public class AssemblerTest {
	
	private final Assembler assembler = new Assembler();
	
	
	@Test(expected=InvalidInstructionException.class)
	public void inexistent() {
		assemble("ADDDD #33");
	}
	
	@Test(expected=InvalidInstructionException.class)
	public void inexistent2() {
		assemble("FOO");
	}
	
	@Test(expected=InvalidInstructionException.class)
	public void inexistent3() {
		assemble("BAR 0");
	}

	@Test
	public void testCLR() {
		assertEquals("CLR 0", assemble("CLR 0"));
		assertEquals("CLR 1", assemble("CLR 1"));
		assertEquals("CLR 2", assemble("CLR 2"));
		assertEquals("CLR 3", assemble("CLR 3"));
	}
	
	@Test(expected=InvalidInstructionException.class)
	public void invalidCLR() {
		assemble("CLR 4");
	}
	
	@Test(expected=InvalidInstructionException.class)
	public void invalidCLR2() {
		assemble("CLR -1");
	}
	
	@Test
	public void testADD() {
		assertEquals("ADD 0", assemble("ADD 0"));
		assertEquals("ADD 1", assemble("ADD 1"));
		assertEquals("ADD 2", assemble("ADD 2"));
		assertEquals("ADD 3", assemble("ADD 3"));
	}
	
	@Test(expected=InvalidInstructionException.class)
	public void invlidAdd() {
		assemble("ADD -1");
	}
	
	@Test(expected=InvalidInstructionException.class)
	public void invlidAdd2() {
		assemble("ADD 4");
	}
	
	@Test
	public void testADDD() {
		assertEquals("ADDD #-1000", assemble("ADDD #-1000"));
		assertEquals("ADDD #-73", assemble("ADDD #-73"));
		assertEquals("ADDD #-42", assemble("ADDD #-42"));
		assertEquals("ADDD #-1", assemble("ADDD #-1"));
		assertEquals("ADDD #0", assemble("ADDD #0"));
		assertEquals("ADDD #1", assemble("ADDD #1"));
		assertEquals("ADDD #2", assemble("ADDD #2"));
		assertEquals("ADDD #42", assemble("ADDD #42"));
		assertEquals("ADDD #73", assemble("ADDD #73"));
		assertEquals("ADDD #1111", assemble("ADDD #1111"));
	}
	
	@Test(expected=InvalidInstructionException.class)
	public void invalidADDD() {
		assemble("ADDD 1");
	}
	
	@Test
	public void testINC() {
		assertEquals("INC", assemble("INC"));
	}
	
	@Test(expected=InvalidInstructionException.class)
	public void invalidINC() {
		assemble("INC #1");
	}
	
	@Test(expected=InvalidInstructionException.class)
	public void invalidINC2() {
		assemble("INC 1");
	}
	
	@Test
	public void testDEC() {
		assertEquals("DEC", assemble("DEC"));
	}
	
	@Test(expected=InvalidInstructionException.class)
	public void invalidDEC() {
		assemble("DEC #1");
	}
	
	@Test(expected=InvalidInstructionException.class)
	public void invalidDEC2() {
		assemble("DEC 1");
	}
	
	@Test
	public void testLWDD() {
		assertEquals("LWDD 0 #0", assemble("LWDD 0 #0"));
		assertEquals("LWDD 1 #0", assemble("LWDD 1 #0"));
		assertEquals("LWDD 2 #0", assemble("LWDD 2 #0"));
		assertEquals("LWDD 3 #0", assemble("LWDD 3 #0"));
		assertEquals("LWDD 0 #1", assemble("LWDD 0 #1"));
		assertEquals("LWDD 1 #1", assemble("LWDD 1 #1"));
		assertEquals("LWDD 2 #1", assemble("LWDD 2 #1"));
		assertEquals("LWDD 3 #1", assemble("LWDD 3 #1"));
		assertEquals("LWDD 0 #73", assemble("LWDD 0 #73"));
		assertEquals("LWDD 1 #73", assemble("LWDD 1 #73"));
		assertEquals("LWDD 2 #73", assemble("LWDD 2 #73"));
		assertEquals("LWDD 3 #73", assemble("LWDD 3 #73"));
	}
	
	@Test(expected=InvalidInstructionException.class)
	public void invalidLWDD() {
		assemble("LWDD 3 #-73");
	}
	
	@Test(expected=InvalidInstructionException.class)
	public void invalidLWDD2() {
		assemble("LWDD 4 #73");
	}
	
	@Test(expected=InvalidInstructionException.class)
	public void invalidLWDD3() {
		assemble("LWDD #33");
	}
	
	@Test(expected=InvalidInstructionException.class)
	public void invalidLWDD4() {
		assemble("LWDD 4");
	}
	
	@Test
	public void testSWDD() {
		assertEquals("SWDD 0 #0", assemble("SWDD 0 #0"));
		assertEquals("SWDD 1 #0", assemble("SWDD 1 #0"));
		assertEquals("SWDD 2 #0", assemble("SWDD 2 #0"));
		assertEquals("SWDD 3 #0", assemble("SWDD 3 #0"));
		assertEquals("SWDD 0 #1", assemble("SWDD 0 #1"));
		assertEquals("SWDD 1 #1", assemble("SWDD 1 #1"));
		assertEquals("SWDD 2 #1", assemble("SWDD 2 #1"));
		assertEquals("SWDD 3 #1", assemble("SWDD 3 #1"));
		assertEquals("SWDD 0 #73", assemble("SWDD 0 #73"));
		assertEquals("SWDD 1 #73", assemble("SWDD 1 #73"));
		assertEquals("SWDD 2 #73", assemble("SWDD 2 #73"));
		assertEquals("SWDD 3 #73", assemble("SWDD 3 #73"));
	}
	
	@Test(expected=InvalidInstructionException.class)
	public void invalidSWDD() {
		assemble("SWDD 3 #-73");
	}
	
	@Test(expected=InvalidInstructionException.class)
	public void invalidSWDD2() {
		assemble("SWDD 4 #73");
	}
	
	@Test(expected=InvalidInstructionException.class)
	public void invalidSWDD3() {
		assemble("SWDD #33");
	}
	
	@Test(expected=InvalidInstructionException.class)
	public void invalidSWDD4() {
		assemble("SWDD 4");
	}
	
	@Test
	public void testSRA() {
		assertEquals("SRA", assemble("SRA"));
	}
	
	@Test(expected=InvalidInstructionException.class)
	public void invalidSRA() {
		assemble("SRA 1");
	}
	
	@Test(expected=InvalidInstructionException.class)
	public void invalidSRA1() {
		assemble("SRA #1");
	}
	
	@Test
	public void testSLA() {
		assertEquals("SLA", assemble("SLA"));
	}
	
	@Test(expected=InvalidInstructionException.class)
	public void invalidSLA() {
		assemble("SLA 1");
	}
	
	@Test(expected=InvalidInstructionException.class)
	public void invalidSLA1() {
		assemble("SLA #1");
	}
	@Test
	public void testSRL() {
		assertEquals("SRL", assemble("SRL"));
	}
	
	@Test(expected=InvalidInstructionException.class)
	public void invalidSRL() {
		assemble("SRL 1");
	}
	
	@Test(expected=InvalidInstructionException.class)
	public void invalidSRL1() {
		assemble("SRL #1");
	}
	@Test
	public void testSLL() {
		assertEquals("SLL", assemble("SLL"));
	}
	
	@Test(expected=InvalidInstructionException.class)
	public void invalidSLL() {
		assemble("SLL 1");
	}
	
	@Test(expected=InvalidInstructionException.class)
	public void invalidSLL1() {
		assemble("SLL #1");
	}
	
	@Test
	public void testAND() {
		assertEquals("AND 0", assemble("AND 0"));
		assertEquals("AND 1", assemble("AND 1"));
		assertEquals("AND 2", assemble("AND 2"));
		assertEquals("AND 3", assemble("AND 3"));
	}
	
	@Test(expected=InvalidInstructionException.class)
	public void invalidAND() {
		assemble("AND");
	}
	
	@Test(expected=InvalidInstructionException.class)
	public void invalidAND2() {
		assemble("AND #1");
	}
	
	@Test(expected=InvalidInstructionException.class)
	public void invalidAND3() {
		assemble("AND -1");
	}
	
	@Test
	public void testOR() {
		assertEquals("OR 0", assemble("OR 0"));
		assertEquals("OR 1", assemble("OR 1"));
		assertEquals("OR 2", assemble("OR 2"));
		assertEquals("OR 3", assemble("OR 3"));
	}
	
	@Test(expected=InvalidInstructionException.class)
	public void invalidOR() {
		assemble("OR");
	}
	
	@Test(expected=InvalidInstructionException.class)
	public void invalidOR2() {
		assemble("OR #1");
	}
	
	@Test(expected=InvalidInstructionException.class)
	public void invalidOR3() {
		assemble("OR -1");
	}
	
	@Test
	public void testNOT() {
		assertEquals("NOT", assemble("NOT"));
	}
	
	@Test(expected=InvalidInstructionException.class)
	public void invalidNOT() {
		assemble("NOT #1");
	}
	
	@Test(expected=InvalidInstructionException.class)
	public void invalidNOT2() {
		assemble("NOT 1");
	}
	
	@Test
	public void testBZ() {
		assertEquals("BZ 0", assemble("BZ 0"));
		assertEquals("BZ 1", assemble("BZ 1"));
		assertEquals("BZ 2", assemble("BZ 2"));
		assertEquals("BZ 3", assemble("BZ 3"));
	}
	
	@Test(expected=InvalidInstructionException.class)
	public void invalidBZ() {
		assemble("BZ -1");
	}
	
	@Test(expected=InvalidInstructionException.class)
	public void invalidBZ2() {
		assemble("BZ #1");
	}
	
	@Test(expected=InvalidInstructionException.class)
	public void invalidBZ3() {
		assemble("BZ");
	}
	
	@Test
	public void testBNZ() {
		assertEquals("BNZ 0", assemble("BNZ 0"));
		assertEquals("BNZ 1", assemble("BNZ 1"));
		assertEquals("BNZ 2", assemble("BNZ 2"));
		assertEquals("BNZ 3", assemble("BNZ 3"));
	}
	
	@Test(expected=InvalidInstructionException.class)
	public void invalidBNZ() {
		assemble("BNZ -1");
	}
	
	@Test(expected=InvalidInstructionException.class)
	public void invalidBNZ2() {
		assemble("BNZ #1");
	}
	
	@Test(expected=InvalidInstructionException.class)
	public void invalidBNZ3() {
		assemble("BNZ");
	}
	
	@Test
	public void testBC() {
		assertEquals("BC 0", assemble("BC 0"));
		assertEquals("BC 1", assemble("BC 1"));
		assertEquals("BC 2", assemble("BC 2"));
		assertEquals("BC 3", assemble("BC 3"));
	}
	
	@Test(expected=InvalidInstructionException.class)
	public void invalidBC() {
		assemble("BC -1");
	}
	
	@Test(expected=InvalidInstructionException.class)
	public void invalidBC2() {
		assemble("BC #1");
	}
	
	@Test(expected=InvalidInstructionException.class)
	public void invalidBC3() {
		assemble("BC");
	}
	
	@Test
	public void testB() {
		assertEquals("B 0", assemble("B 0"));
		assertEquals("B 1", assemble("B 1"));
		assertEquals("B 2", assemble("B 2"));
		assertEquals("B 3", assemble("B 3"));
	}
	
	@Test(expected=InvalidInstructionException.class)
	public void invalidB() {
		assemble("B -1");
	}
	
	@Test(expected=InvalidInstructionException.class)
	public void invalidB2() {
		assemble("B #1");
	}
	
	@Test(expected=InvalidInstructionException.class)
	public void invalidB3() {
		assemble("B");
	}
	
	@Test
	public void testBZD() {
		assertEquals("BZD #0", assemble("BZD #0"));
		assertEquals("BZD #1", assemble("BZD #1"));
		assertEquals("BZD #42", assemble("BZD #42"));
		assertEquals("BZD #73", assemble("BZD #73"));
		assertEquals("BZD #1111", assemble("BZD #1111"));
	}
	
	@Test(expected=InvalidInstructionException.class)
	public void invalidBZD() {
		assemble("BZD");
	}
	
	@Test(expected=InvalidInstructionException.class)
	public void invalidBZD2() {
		assemble("BZD #-1");
	}
	
	@Test(expected=InvalidInstructionException.class)
	public void invalidBZD3() {
		assemble("BZD 2");
	}
	
	@Test
	public void testBNZD() {
		assertEquals("BNZD #0", assemble("BNZD #0"));
		assertEquals("BNZD #1", assemble("BNZD #1"));
		assertEquals("BNZD #42", assemble("BNZD #42"));
		assertEquals("BNZD #73", assemble("BNZD #73"));
		assertEquals("BNZD #1111", assemble("BNZD #1111"));
	}
	
	@Test(expected=InvalidInstructionException.class)
	public void invalidBNZD() {
		assemble("BNZD");
	}
	
	@Test(expected=InvalidInstructionException.class)
	public void invalidBNZD2() {
		assemble("BNZD #-1");
	}
	
	@Test(expected=InvalidInstructionException.class)
	public void invalidBNZD3() {
		assemble("BNZD 2");
	}
	
	@Test
	public void testBCD() {
		assertEquals("BCD #0", assemble("BCD #0"));
		assertEquals("BCD #1", assemble("BCD #1"));
		assertEquals("BCD #42", assemble("BCD #42"));
		assertEquals("BCD #73", assemble("BCD #73"));
		assertEquals("BCD #1111", assemble("BCD #1111"));
	}
	
	@Test(expected=InvalidInstructionException.class)
	public void invalidBCD() {
		assemble("BCD");
	}
	
	@Test(expected=InvalidInstructionException.class)
	public void invalidBCD2() {
		assemble("BCD #-1");
	}
	
	@Test(expected=InvalidInstructionException.class)
	public void invalidBCD3() {
		assemble("BCD 2");
	}
	
	@Test
	public void testBD() {
		assertEquals("BD #0", assemble("BD #0"));
		assertEquals("BD #1", assemble("BD #1"));
		assertEquals("BD #42", assemble("BD #42"));
		assertEquals("BD #73", assemble("BD #73"));
		assertEquals("BD #1111", assemble("BD #1111"));
	}
	
	@Test(expected=InvalidInstructionException.class)
	public void invalidBD() {
		assemble("BD");
	}
	
	@Test(expected=InvalidInstructionException.class)
	public void invalidBD2() {
		assemble("BD #-1");
	}
	
	@Test(expected=InvalidInstructionException.class)
	public void invalidBD3() {
		assemble("BD 2");
	}
	
	@Test
	public void testEND() {
		assertEquals("END", assemble("END"));
	}
	
	@Test(expected=InvalidInstructionException.class)
	public void invalidEND() {
		assemble("END 1");
	}
	
	@Test(expected=InvalidInstructionException.class)
	public void invalidEND2() {
		assemble("END #1");
	}
	
	private String assemble(String instr) {
		return this.assembler.assemble(instr).toString();
	}
	
}