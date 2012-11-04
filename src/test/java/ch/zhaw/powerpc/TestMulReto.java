package ch.zhaw.powerpc;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import ch.zhaw.powerpc.controller.InputReader;
import ch.zhaw.powerpc.controller.ProgramStarter;
import ch.zhaw.powerpc.model.ControlUnit;

@Ignore
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
		for (int i = Short.MIN_VALUE, j = Short.MAX_VALUE; i <= Short.MAX_VALUE && j >= Short.MIN_VALUE; i++, j--) {
			if (i * j >= Short.MIN_VALUE && i * j <= Short.MAX_VALUE) {
				int res = multiply((short) i, (short) j);
				assertEquals(
						String.format("%d (%s) * %d (%s) ", i, Integer.toBinaryString(i), j, Integer.toBinaryString(j)),
						i * j, res);
			}
		}
	}

	@Test
	public void sternTests() {
		int res = multiply((short) 15, (short) 27);
		assertEquals(
				String.format("%d (%s) * %d (%s) ", 15, Integer.toBinaryString(15), 27, Integer.toBinaryString(27)),
				15 * 27, res);

		res = multiply((short) 0, (short) 23456);
		assertEquals(
				String.format("%d (%s) * %d (%s) ", 0, Integer.toBinaryString(0), 23456, Integer.toBinaryString(23456)),
				0 * 23456, res);

		res = multiply((short) -1234, (short) 4321);
		System.out.println("Res: " + res);
		System.out.println("Mul:" + -1234 * 4321);
		assertEquals(
				String.format("%d (%s) * %d (%s) ", -1234, Integer.toBinaryString(-1234), 4321,
						Integer.toBinaryString(4321)), -1234 * 4321, res);

		res = multiply((short) -222, (short) -333);
		assertEquals(
				String.format("%d (%s) * %d (%s) ", -222, Integer.toBinaryString(-222), -333,
						Integer.toBinaryString(-333)), -222 * -333, res);
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

		System.out.println("Lower: " + cu.getMemory().readData(504));
		System.out.println("Upper: " + cu.getMemory().readData(506));
		return (cu.getMemory().readData(506) << 16) + cu.getMemory().readData(504);
	}

}
