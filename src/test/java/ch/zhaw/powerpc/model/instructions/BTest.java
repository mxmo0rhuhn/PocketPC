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
 * Testet den unbedingten Sprung auf die Adresse aus einem Register
 * 
 * @author Max
 * 
 */
public class BTest {

	@Test
	public void roundTrip() {
		MainMemory mem = new MainMemory();
		mem.setInstruction(100, new B(03));
		ControlUnit cu = new ControlUnit((mem)); 

		cu.getRegisters()[03].write(122);
		assertEquals(100, cu.getProgramCounter());

		cu.runCycle();
		
		assertEquals(122, cu.getProgramCounter());
	}
	
	@Test
	public void testJumpAkku() {
	
		ControlUnit cu = new ControlUnit(new MainMemory()); 
		cu.getRegisters()[00].write(104);
	
		assertEquals(100, cu.getProgramCounter());
	
		assertEquals(104, new B(00).run(cu));
	}

	@Test
	public void testJumpRegisterOne() {

		ControlUnit cu = new ControlUnit(new MainMemory()); 
		cu.getRegisters()[01].write(222);

		assertEquals(100, cu.getProgramCounter());

		assertEquals(222, new B(01).run(cu));
	}

	@Test
	public void testJumpRegisterTwo() {

		ControlUnit cu = new ControlUnit(new MainMemory()); 
		cu.getRegisters()[02].write(134);

		assertEquals(100, cu.getProgramCounter());

		assertEquals(134, new B(02).run(cu));
	}

	@Test
	public void testJumpRegisterThree() {

		ControlUnit cu = new ControlUnit(new MainMemory()); 
		cu.getRegisters()[03].write(144);

		assertEquals(100, cu.getProgramCounter());

		assertEquals(144, new B(03).run(cu));
	}
	
	@Test
	public void testJumpToLastWord() {

		ControlUnit cu = new ControlUnit(new MainMemory()); 
		cu.getRegisters()[01].write(498);

		assertEquals(100, cu.getProgramCounter());

		assertEquals(498, new B(01).run(cu));
	}

	@Test
	public void testJumpToFirstWord() {

		ControlUnit cu = new ControlUnit(new MainMemory()); 
		cu.getRegisters()[02].write(100);

		assertEquals(100, cu.getProgramCounter());

		assertEquals(100, new B(02).run(cu));
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
	public void badJump(){
		ControlUnit cu = new ControlUnit(new MainMemory()); 
		cu.getRegisters()[03].write(135);
		new B(03).run(cu);
	}
	
	@Test
	public void binary() {
		binEquals("0001000000000000", new B(00).getBinary());
		binEquals("0001010000000000", new B(01).getBinary());
		binEquals("0001100000000000", new B(02).getBinary());
		binEquals("0001110000000000", new B(03).getBinary());
	}
}