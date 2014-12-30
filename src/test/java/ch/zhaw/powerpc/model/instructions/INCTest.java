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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import ch.zhaw.powerpc.model.ControlUnit;
import ch.zhaw.powerpc.model.MainMemory;

public class INCTest {
	
	@Test
	public void lowestPossible() {
		ControlUnit cu = new ControlUnit(new MainMemory());
		cu.getRegisters()[0].write(-32768);
		
		new INC().run(cu);
		
		assertFalse(cu.getAlu().isCarryFlag());
		assertEquals(-32767, cu.getRegisters()[0].read());
	}
	
	@Test
	public void randomLow() {
		ControlUnit cu = new ControlUnit(new MainMemory());
		cu.getRegisters()[0].write(-1337);
		
		new INC().run(cu);
		
		assertFalse(cu.getAlu().isCarryFlag());
		assertEquals(-1336, cu.getRegisters()[0].read());
	}	
	
	
	@Test
	public void lastLow() {
		ControlUnit cu = new ControlUnit(new MainMemory());
		cu.getRegisters()[0].write(-1);
		
		new INC().run(cu);
		
		assertTrue(cu.getAlu().isCarryFlag());
		assertEquals(0, cu.getRegisters()[0].read());
	}
	
	@Test
	public void zero() {
		ControlUnit cu = new ControlUnit(new MainMemory());
		cu.getRegisters()[0].write(0);
		
		new INC().run(cu);
		
		assertFalse(cu.getAlu().isCarryFlag());
		assertEquals(1, cu.getRegisters()[0].read());
	}
	
	@Test
	public void firstHigh() {
		ControlUnit cu = new ControlUnit(new MainMemory());
		cu.getRegisters()[0].write(1);
		
		new INC().run(cu);
		
		assertFalse(cu.getAlu().isCarryFlag());
		assertEquals(2, cu.getRegisters()[0].read());
	}	
	
	@Test
	public void randomHigh() {
		ControlUnit cu = new ControlUnit(new MainMemory());
		cu.getRegisters()[0].write(1337);
		
		new INC().run(cu);
		
		assertFalse(cu.getAlu().isCarryFlag());
		assertEquals(1338, cu.getRegisters()[0].read());
	}
	
	@Test
	public void lastHigh() {
		ControlUnit cu = new ControlUnit(new MainMemory());
		cu.getRegisters()[0].write(32766);
		
		new INC().run(cu);
		
		assertFalse(cu.getAlu().isCarryFlag());
		assertEquals(32767, cu.getRegisters()[0].read());
	}
	
	@Test
	public void testOverflowNo() {
		ControlUnit cu = new ControlUnit(new MainMemory());
		cu.getRegisters()[0].write(32767);
		
		new INC().run(cu);
		
		assertFalse(cu.getAlu().isCarryFlag());
		assertEquals(-32768, cu.getRegisters()[0].read());
	}
	
	@Test
	public void testOverflow() {
		ControlUnit cu = new ControlUnit(new MainMemory());
		cu.getRegisters()[0].write(-1);
		
		new INC().run(cu);
		
		assertTrue(cu.getAlu().isCarryFlag());
		assertEquals(0, cu.getRegisters()[0].read());
	}
	
	@Test
	public void testToString() {
		assertEquals("INC", new INC().toString());
	}
	
	@Test
	public void testBinary() {
		binEquals("0000000100000000", new INC().getBinary());
	}

}
