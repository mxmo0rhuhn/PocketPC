package ch.zhaw.powerpc.model.instructions;

import static ch.zhaw.powerpc.model.instructions.TestUtil.binEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.junit.Test;

import ch.zhaw.powerpc.controller.InputReader;
import ch.zhaw.powerpc.model.ControlUnit;
import ch.zhaw.powerpc.model.MainMemory;



public class ANDTest {
	
	private static final String BASE_RESOURCE = "ch/zhaw/powerpc/model/instructions";
	
	@Test
	public void shouldAndBitwise() {
		
		ControlUnit cu = new ControlUnit(new MainMemory());
		
		short[] validData = new short[] { 42, 1023};
			cu.getMemory().writeData(500, validData[0]);
			cu.getMemory().writeData(502, validData[1]);
			new LWDD(0, 500).run(cu);
			new LWDD(1, 502).run(cu);
			new AND(1).run(cu);
			Integer.toBinaryString(cu.getRegisters()[0].read());
			assertEquals("101010", Integer.toBinaryString(cu.getRegisters()[0].read()));

		
	}
	
	
	private String[] readContents(String filename) {
		filename = BASE_RESOURCE + filename;
		InputStream is = getClass().getClassLoader().getResourceAsStream(filename);
		if (is == null) {
			fail("File " + filename + " does not exist");
		}
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			return new InputReader(br).readContents();
		} catch (Exception e) {
			fail(e.getMessage());
			return null; // never happens
		}
	}

}
