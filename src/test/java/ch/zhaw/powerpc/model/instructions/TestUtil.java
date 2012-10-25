package ch.zhaw.powerpc.model.instructions;

import ch.zhaw.powerpc.model.ControlUnit;
import ch.zhaw.powerpc.model.MainMemory;

public final class TestUtil {

	/**
	 * Erstellt eine ControlUnit aus dem spzifizierten Array. Das Array ist pseudo - assoziativ, d.h. jeder zweite Wert
	 * ist ein Index. Bsp:
	 * <pre>{501, 2, 502, 3} --> memory[501] = 2, memory[502] = 3</pre>
	 * 
	 * @param vals
	 * @return
	 */
	public static ControlUnit initCU(int... vals) {
		byte[] initialMemory = new byte[600];
		for (int i = 0; i < vals.length; i += 2) {
			initialMemory[vals[i]] = (byte) vals[i + 1];
		}
		return new ControlUnit(new MainMemory(initialMemory));
	}

}
