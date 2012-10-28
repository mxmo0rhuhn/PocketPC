/**
 * 
 */
package ch.zhaw.powerpc.model.instructions;

import static ch.zhaw.powerpc.model.instructions.TestUtil.binEquals;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import ch.zhaw.powerpc.model.ControlUnit;
import ch.zhaw.powerpc.model.MainMemory;

/**
 * Testet den durch das carry bit bedingten Sprung auf die Adresse aus einem Register
 * 
 * @author Max
 * 
 */
public class BCTest {

	@Test
	public void roundTrip() {
		MainMemory mem = new MainMemory();
		mem.setInstruction(100, new B(02));
		mem.setInstruction(102, new B(03));
		
		ControlUnit cu = new ControlUnit((mem)); 

		cu.getRegisters()[02].write(132);
		cu.getRegisters()[03].write(122);
		assertEquals(100, cu.getProgramCounter());

		cu.getAlu().setCarryFlag(false);
		cu.runCycle();
		
		assertEquals(102, cu.getProgramCounter());

		cu.getAlu().setCarryFlag(true);
		cu.runCycle();
		
		assertEquals(122, cu.getProgramCounter());
	}

	@Test
	public void testJumpAkku() {
		
		ControlUnit cu = new ControlUnit(new MainMemory()); 
		cu.getRegisters()[00].write(104);
		
		assertEquals(100, cu.getProgramCounter());
		
		cu.getAlu().setCarryFlag(true);
		assertEquals(104, new B(00).run(cu));
	}
	
	@Test
	public void testNoJumpAkku() {
		
		ControlUnit cu = new ControlUnit(new MainMemory()); 
		cu.getRegisters()[00].write(104);
		
		assertEquals(100, cu.getProgramCounter());
		
		cu.getAlu().setCarryFlag(false);
		assertEquals(102, new B(00).run(cu));
	}
	
	@Test
	public void testJumpRegisterOne() {

		ControlUnit cu = new ControlUnit(new MainMemory()); 
		cu.getRegisters()[01].write(222);

		assertEquals(100, cu.getProgramCounter());

		cu.getAlu().setCarryFlag(true);
		assertEquals(222, new B(01).run(cu));
	}

	@Test
	public void testNoJumpRegisterOne() {

		ControlUnit cu = new ControlUnit(new MainMemory()); 
		cu.getRegisters()[01].write(222);

		assertEquals(100, cu.getProgramCounter());

		cu.getAlu().setCarryFlag(false);
		assertEquals(102, new B(01).run(cu));
	}

	@Test
	public void testNoJumpRegisterTwo() {

		ControlUnit cu = new ControlUnit(new MainMemory()); 
		cu.getRegisters()[02].write(134);

		assertEquals(100, cu.getProgramCounter());

		cu.getAlu().setCarryFlag(false);
		assertEquals(102, new B(02).run(cu));
	}

	@Test
	public void testJumpRegisterTwo() {

		ControlUnit cu = new ControlUnit(new MainMemory()); 
		cu.getRegisters()[02].write(134);

		assertEquals(100, cu.getProgramCounter());

		cu.getAlu().setCarryFlag(true);
		assertEquals(134, new B(02).run(cu));
	}

	@Test
	public void testNoJumpRegisterThree() {

		ControlUnit cu = new ControlUnit(new MainMemory()); 
		cu.getRegisters()[03].write(144);

		assertEquals(100, cu.getProgramCounter());

		cu.getAlu().setCarryFlag(false);
		assertEquals(102, new B(03).run(cu));
	}
	
	@Test
	public void testJumpToLastWord() {

		ControlUnit cu = new ControlUnit(new MainMemory()); 
		cu.getRegisters()[01].write(498);

		assertEquals(100, cu.getProgramCounter());

		cu.getAlu().setCarryFlag(true);
		
		assertEquals(498, new B(01).run(cu));
	}

	@Test
	public void testJumpToFirstWord() {

		ControlUnit cu = new ControlUnit(new MainMemory()); 
		cu.getRegisters()[02].write(100);

		assertEquals(100, cu.getProgramCounter());

		cu.getAlu().setCarryFlag(true);
		
		assertEquals(100, new B(02).run(cu));
	}

	@Test
	public void testJumpRegisterThree() {

		ControlUnit cu = new ControlUnit(new MainMemory()); 
		cu.getRegisters()[03].write(144);

		assertEquals(100, cu.getProgramCounter());

		cu.getAlu().setCarryFlag(true);
		assertEquals(144, new B(03).run(cu));
	}
	
	@Test(expected=InvalidInstructionException.class)	
	public void badJumpToLow(){
		ControlUnit cu = new ControlUnit(new MainMemory()); 
		cu.getRegisters()[02].write(50);
		new B(02).run(cu);
	}
	
	@Test(expected=InvalidInstructionException.class)	
	public void badJumpToHigh(){
		ControlUnit cu = new ControlUnit(new MainMemory()); 
		cu.getRegisters()[01].write(510);
		new B(01).run(cu);
	}
	
	@Test(expected=InvalidInstructionException.class)	
	public void badJumpTo(){
		ControlUnit cu = new ControlUnit(new MainMemory()); 
		cu.getRegisters()[03].write(135);
		new B(03).run(cu);
	}
	
	@Test
	public void binary() {
		binEquals("0001001100000000", new B(00).getBinary());
		binEquals("0001011100000000", new B(01).getBinary());
		binEquals("0001101100000000", new B(02).getBinary());
		binEquals("0001111100000000", new B(03).getBinary());
	}
}