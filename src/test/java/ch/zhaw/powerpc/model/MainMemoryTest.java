package ch.zhaw.powerpc.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import ch.zhaw.powerpc.model.instructions.B;
import ch.zhaw.powerpc.model.instructions.END;

public class MainMemoryTest {

	@Test(expected=IllegalArgumentException.class)
	public void writeToLowInstruction() {
		MainMemory mem = new MainMemory();
		mem.setInstruction(98, new END());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void readToLowInstruction() {
		MainMemory mem = new MainMemory();
		mem.readInstruction(98);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void writeIllegalInstruction() {
		MainMemory mem = new MainMemory();
		mem.setInstruction(103, new END());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void readIllegalInstruction() {
		MainMemory mem = new MainMemory();
		mem.readInstruction(103);
	}
	
	@Test
	public void workingInstruction() {
		MainMemory mem = new MainMemory();
		mem.setInstruction(100, new END());
		assertEquals(new END().toString(), mem.readInstruction(100).toString());
		mem.setInstruction(102, new B(2));
		assertEquals(new B(2).toString(), mem.readInstruction(102).toString());
		mem.setInstruction(498, new END());
		assertEquals(new END().toString(), mem.readInstruction(498).toString());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void writeToHighInstruction() {
		MainMemory mem = new MainMemory();
		mem.setInstruction(500, new END());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void readToHighInstruction() {
		MainMemory mem = new MainMemory();
		mem.readInstruction(500);
	}
	
	
	
	@Test(expected=IllegalArgumentException.class)
	public void writeToLowData() {
		MainMemory mem = new MainMemory();
		mem.writeData(498, (short) 1337);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void readToLowData() {
		MainMemory mem = new MainMemory();
		mem.readData(498);
	}	
	
	@Test(expected=IllegalArgumentException.class)
	public void writeIllegalData() {
		MainMemory mem = new MainMemory();
		mem.writeData(501, (short) 1337);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void readIllegalData() {
		MainMemory mem = new MainMemory();
		mem.readData(501);
	}
	
	@Test
	public void workingData() {
		MainMemory mem = new MainMemory();
		mem.writeData(500, (short) 1337);
		assertEquals((short) 1337, mem.readData(500));
		mem.writeData(530, (short) 1);
		assertEquals((short) 1, mem.readData(530));
		mem.writeData(1500, (short) 17);
		assertEquals((short) 17, mem.readData(1500));
	}
}	