package ch.zhaw.powerpc;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import ch.zhaw.powerpc.controller.Assembler;
import ch.zhaw.powerpc.controller.InputReader;
import ch.zhaw.powerpc.controller.ProgramStarter;
import ch.zhaw.powerpc.model.ControlUnit;
import ch.zhaw.powerpc.model.MainMemory;
import ch.zhaw.powerpc.model.instructions.Instruction;

public class TestMulReto {

	private static String[] initialInstructions;

	@BeforeClass
	public static void loadInstructions() throws IOException {
		InputReader ir = new InputReader(new BufferedReader(new FileReader("asmfiles/mul_reto.asm")));
		initialInstructions = ir.readContents();
	}

//	@Test
//	public void testZero() {
//		for (int i = Short.MIN_VALUE; i <= Short.MAX_VALUE; i++) {
//			assertEquals(0, multiply((short) i, (short) 0));
//			assertEquals(0, multiply((short) 0, (short) i));
//		}
//	}

	@Test
	public void testNonOverflow() {
		for (int i = Short.MIN_VALUE, j = Short.MAX_VALUE; i < j; i++, j--) {
			if (Math.abs(i * j) < 32678) {
				int res = multiply((short) i, (short) j);
				assertEquals(String.format("%d (%s) * %d (%s) ", i, Integer.toBinaryString(i), j, Integer.toBinaryString(j)), i * j, res);
			}
		}
	}

//	@Test
//	public void testEven() {
//		for (int i = Short.MIN_VALUE, j = Short.MAX_VALUE; i < j; i++, j--) {
//			assertEquals(i * j, multiply((short) i, (short) j));
//		}
//	}

	private int multiply(short a, short b) {
		ControlUnit cu = new ControlUnit(ProgramStarter.createMainMemory(initialInstructions));
		cu.getMemory().writeData(500, a);
		cu.getMemory().writeData(502, b);
		while (cu.runCycle())
			;
		return cu.getMemory().readData(510) << 16 + cu.getMemory().readData(502);
	}

}
