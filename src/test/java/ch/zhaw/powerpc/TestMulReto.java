package ch.zhaw.powerpc;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import ch.zhaw.powerpc.controller.InputReader;
import ch.zhaw.powerpc.controller.ProgramStarter;
import ch.zhaw.powerpc.model.ControlUnit;

public class TestMulReto {

	private static long stepsCounter;

	private static long testCounter;

	private static String[] initialInstructions;

	@BeforeClass
	public static void loadInstructions() throws IOException {
		InputReader ir = new InputReader(new BufferedReader(new FileReader("asmfiles/mul_reto.asm")));
		initialInstructions = ir.readContents();
	}

	@Test
	public void testNonOverflow() {
		for (int i = Short.MIN_VALUE, j = Short.MAX_VALUE; i < j; i++, j--) {
			if (i * j >= Short.MIN_VALUE && i * j <= Short.MAX_VALUE) {
				int res = multiply((short) i, (short) j);
				assertEquals(
						String.format("%d (%s) * %d (%s) ", i, Integer.toBinaryString(i), j, Integer.toBinaryString(j)),
						i * j, res);
			}
		}
	}

	@AfterClass
	public static void stats() {
		System.out.println("\n******\n");
		System.out.println("Run " + testCounter + " Tests");
		System.out.println("On Average " + (stepsCounter / testCounter) + " Steps per Run");
		System.out.println("\n******\n");
	}

	private int multiply(short a, short b) {
		ControlUnit cu = new ControlUnit(ProgramStarter.createMainMemory(initialInstructions));
		cu.getMemory().writeData(500, a);
		cu.getMemory().writeData(502, b);
		while (cu.runCycle()) {
			stepsCounter++;
		}
		testCounter++;

		return (cu.getMemory().readData(510) << 16) + cu.getMemory().readData(502);
	}

}
