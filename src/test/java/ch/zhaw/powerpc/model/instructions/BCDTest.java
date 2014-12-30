/*
 * Copyright (c) 2012 - Reto Hablützel, Max Schrimpf, Désirée Sacher
 *
 * Licensed under the EUPL, Version 1.1 or – as soon they will be approved by the European Commission - subsequent versions of the EUPL (the "Licence");
 * You may not use this work except in compliance with the Licence.
 * You may obtain a copy of the Licence at:
 *
 * https://joinup.ec.europa.eu/software/page/eupl5
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the Licence is distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Licence for the specific language governing permissions and limitations under the Licence.
 */

package ch.zhaw.powerpc.model.instructions;

import static ch.zhaw.powerpc.model.instructions.TestUtil.binEquals;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import ch.zhaw.powerpc.model.ControlUnit;
import ch.zhaw.powerpc.model.MainMemory;

/**
 * Testet den durch das cary bit bedingten direkten Sprung
 * 
 * @author Max
 * 
 */
public class BCDTest {

	@Test
	public void roundTrip() {
		
		MainMemory mem = new MainMemory();
		mem.setInstruction(100, new BCD(132));
		mem.setInstruction(102, new BCD(122));
		
		ControlUnit cu = new ControlUnit((mem)); 

		assertEquals(100, cu.getProgramCounter());

		cu.getAlu().setCarryFlag(false);
		cu.runCycle();
		
		assertEquals(102, cu.getProgramCounter());

		cu.getAlu().setCarryFlag(true);
		cu.runCycle();
		
		assertEquals(122, cu.getProgramCounter());
	}

	@Test
	public void testJump() {
		ControlUnit cu = new ControlUnit(new MainMemory()); 
		
		assertEquals(100, cu.getProgramCounter());
		
		cu.getAlu().setCarryFlag(true);
		assertEquals(104, new BCD(104).run(cu));
	}
	
	@Test
	public void testNoJump() {
		
		ControlUnit cu = new ControlUnit(new MainMemory()); 
		
		assertEquals(100, cu.getProgramCounter());
		
		cu.getAlu().setCarryFlag(false);
		assertEquals(102, new BCD(102).run(cu));
	}
	
	@Test
	public void testJumpToLastWord() {

		ControlUnit cu = new ControlUnit(new MainMemory()); 

		assertEquals(100, cu.getProgramCounter());

		cu.getAlu().setCarryFlag(true);
		assertEquals(498, new BCD(498).run(cu));
	}

	@Test
	public void testNoJumpLastWord() {

		ControlUnit cu = new ControlUnit(new MainMemory()); 

		assertEquals(100, cu.getProgramCounter());

		cu.getAlu().setCarryFlag(false);
		assertEquals(102, new BCD(498).run(cu));
	}

	@Test
	public void testJumpToFirstWord() {

		ControlUnit cu = new ControlUnit(new MainMemory()); 

		assertEquals(100, cu.getProgramCounter());

		cu.getAlu().setCarryFlag(true);
		assertEquals(100, new BCD(100).run(cu));
	}
	
	@Test
	public void testNoJumpToFirstWord() {
	
		ControlUnit cu = new ControlUnit(new MainMemory()); 
	
		assertEquals(100, cu.getProgramCounter());
	
		cu.getAlu().setCarryFlag(false);
		assertEquals(102, new BCD(100).run(cu));
	}

	@Test(expected=InvalidInstructionException.class)	
	public void badJumpToLow(){
		ControlUnit cu = new ControlUnit(new MainMemory()); 
		new BCD(50).run(cu);
	}
	
	@Test(expected=InvalidInstructionException.class)	
	public void badJumpToHigh(){
		ControlUnit cu = new ControlUnit(new MainMemory()); 
		new BCD(510).run(cu);
	}
	
	@Test(expected=InvalidInstructionException.class)	
	public void badJump(){
		ControlUnit cu = new ControlUnit(new MainMemory()); 
		new BCD(135).run(cu);
	}
	
	@Test
	public void binary() {
		binEquals("0011100001100100", new BCD(100).getBinary());
		binEquals("0011100010100110", new BCD(166).getBinary());
		binEquals("0011100111110010", new BCD(498).getBinary());
	}
}