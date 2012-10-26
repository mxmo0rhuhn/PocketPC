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
 * Testet den unbedingten Sprung
 * 
 * @author Max
 * 
 */
public class BDTest {

	@Test
	public void roundTrip() {
		MainMemory mem = new MainMemory();
		
		mem.setInstruction(100, new BD(118));
		
		ControlUnit cu = new ControlUnit((mem));

		assertEquals(100, cu.getProgramCounter());

		cu.runCycle();
		
		assertEquals(118, cu.getProgramCounter());
	}

	@Test
	public void testJumpToLastWord() {
		assertEquals(498, new BD(498).run(new ControlUnit((new MainMemory()))));
	}
	
	@Test
	public void testJumpToFirstWord() {
		assertEquals(100, new BD(100).run(new ControlUnit((new MainMemory()))));
	}

	@Test
	public void testJump() {
		assertEquals(266, new BD(266).run(new ControlUnit((new MainMemory()))));
	}
	
	@Test(expected=InvalidInstructionException.class)	
	public void badJumpToLow(){

		new BD(98).run(new ControlUnit((new MainMemory())));
	}
	
	@Test(expected=InvalidInstructionException.class)	
	public void badJumpToHigh(){

		new BD(500).run(new ControlUnit((new MainMemory())));
	}
	
	@Test(expected=InvalidInstructionException.class)	
	public void badJumpTo(){
		new BD(111).run(new ControlUnit((new MainMemory())));
	}	

	@Test
	public void binary() {
		binEquals("0010000001100100", new BD(100).getBinary());
		binEquals("0010000010100110", new BD(166).getBinary());
		binEquals("0010000111110010", new BD(498).getBinary());
	}
}