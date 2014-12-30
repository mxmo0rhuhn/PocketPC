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
 * Testet den direkten, unbedingten Sprung
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
	public void badJump(){
		new BD(111).run(new ControlUnit((new MainMemory())));
	}	

	@Test
	public void binary() {
		binEquals("0010000001100100", new BD(100).getBinary());
		binEquals("0010000010100110", new BD(166).getBinary());
		binEquals("0010000111110010", new BD(498).getBinary());
	}
}