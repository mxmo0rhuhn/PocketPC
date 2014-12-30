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

	private static final short[][] sternTests = new short[][] { new short[] { 15, 27 }, new short[] { 0, 23456 },
			new short[] { -1234, 4321 }, new short[] { -222, -333 } };

	private static final short[][] smokeTests = new short[][] { new short[] { 0, 30000 }, new short[] { 1, 23456 },
			new short[] { 2, 2 }, new short[] { 1, 1 }, new short[] { 3, 3 }, new short[] { 2, 3 },
			new short[] { 31, 27 }, new short[] { -1, -1 }, new short[] { -1, 1 }, new short[] { -2, 1 },
			new short[] { 1, -2 }, new short[] { -3, -4 }, new short[] { -5, 5 }, new short[] { -11111, -2 },
			new short[] { 1111, -4 }, new short[] { -3, 11111 }, new short[] { -1111, -1111 },
			new short[] { -2222, -2222 }, new short[] { -1024, 1024 }, new short[] { 1024, -1024 },
			new short[] { -443, -443 }, new short[] { -1, 32333 }, new short[] { -32333, 32333 },
			new short[] { -11, -2222 }, new short[] { 11, -31222 }, new short[] { -24555, 2 },
			new short[] { -3423, 32111 }, new short[] { -28888, 28888 }, new short[] { -123, 321 },
			new short[] { -21, 13 }, new short[] { 13, 22 }, new short[] { -22, 44 }, new short[] { 0, 0 },
			new short[] { 32, 32 }, new short[] { 64, -64 }, new short[] { 35, 11111 }, new short[] { 32123, -4 },
			new short[] { 32101, -4322 }, new short[] { -3, -11111 }, new short[] { 3, 11111 }, new short[] { -73, 73 } };

	private static long stepsCounter;

	private static long testCounter;

	private static String[] initialInstructions;

	@BeforeClass
	public static void loadInstructions() throws IOException {
		InputReader ir = new InputReader(new BufferedReader(new FileReader("asmfiles/multiplication.asm")));
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
		for (int i = 0; i < sternTests.length; i++) {
			short a = sternTests[i][0];
			short b = sternTests[i][1];
			int res = multiply(a, b);
			assertEquals(
					String.format("%d (%s) * %d (%s) ", a, Integer.toBinaryString(a), b, Integer.toBinaryString(b)), a
							* b, res);
		}
	}

	@Test
	public void testTest() {
		short a = -1;
		short b = 1;
		int res = multiply(a, b);
		int realRes = a * b;
		assertEquals(String.format("%d (%s) * %d (%s) ", a, Integer.toBinaryString(a), b, Integer.toBinaryString(b)),
				realRes, res);
	}

	@Test
	public void smokeTests() {
		for (int i = 0; i < smokeTests.length; i++) {
			short a = smokeTests[i][0];
			short b = smokeTests[i][1];
			int res = multiply(a, b);
			assertEquals(
					String.format("%d (%s) * %d (%s) ", a, Integer.toBinaryString(a), b, Integer.toBinaryString(b)), a
							* b, res);
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

		// System.out.println("Lower: " + cu.getMemory().readData(504));
		// System.out.println("Upper: " + cu.getMemory().readData(506));
		short low = cu.getMemory().readData(504);
		short high = cu.getMemory().readData(506);
//		if (high == -1) {
			return (high << 16) | (0x0000FFFF & low);
//		} else {
//			return (high << 16) + low;
//		}
	}

}
