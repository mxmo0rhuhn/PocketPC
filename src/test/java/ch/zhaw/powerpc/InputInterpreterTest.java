package ch.zhaw.powerpc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import ch.zhaw.powerpc.controller.InputInterpreter;
import ch.zhaw.powerpc.model.MainMemory;

public class InputInterpreterTest {
	
	private final InputInterpreter inp = new InputInterpreter();
		
	
	@Test
	public void shouldHandleNOP() {
		assertNotNull(inp.generateMainMemory(new String[0]));
	}
	
	@Test
	public void shouldWriteAtSpecificAddress() {
		MainMemory mem = inp.generateMainMemory(new String[]{"500=33"});
		assertEquals(33, mem.read(500));
	}
	
	@Test
	public void shouldWriteAtSpecificAddressAfterCommands() {
		MainMemory mem = inp.generateMainMemory(new String[]{"END", "500=33"});
		assertEquals(33, mem.read(500));
	}
	
	@Test
	public void shouldWriteFirstCommandAtPosition100() {
		MainMemory mem = inp.generateMainMemory(new String[]{"END"});
		assertEquals(0, mem.read(100));
	}
	
	@Test
	public void shouldWriteProgramSequentially() {
		MainMemory mem = inp.generateMainMemory(new String[]{"ADD 1", "END"});
		assertEquals(Integer.parseInt("0000011111010101", 2), mem.read(100));
		assertEquals(0, mem.read(101));
	}

}
