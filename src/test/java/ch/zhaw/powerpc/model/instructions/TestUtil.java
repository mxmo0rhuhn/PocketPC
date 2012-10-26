package ch.zhaw.powerpc.model.instructions;

import static org.junit.Assert.assertEquals;

public final class TestUtil {

	/**
	 * Helper-Funktion, die sicherstellt, dass ein String (also eine binaere char folge) dem Wert vom char entspricht.
	 * Fuer Tests zu verwenden wie assertEquals.
	 * 
	 */
	public static void binEquals(String expected, char actual) {
		assertEquals(Integer.parseInt(expected, 2), actual);
	}
}
