package ch.zhaw.powerpc.model;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;

import ch.zhaw.powerpc.model.instructions.ADDD;
import ch.zhaw.powerpc.model.instructions.Instruction;

/**
 * 
 * @author Max / Reto
 * 
 */
public final class Decoder {

	private static final Map<char[], Class<? extends Instruction>> patterns = new TreeMap<char[], Class<? extends Instruction>>();

	static {
		patterns.put("0000rr101_______".toCharArray(), Instruction.class);
		patterns.put("0000rr111_______".toCharArray(), Instruction.class);
		patterns.put("1nnnnnnnnnnnnnnn".toCharArray(), ADDD.class);
		patterns.put("00000001________".toCharArray(), Instruction.class);
		patterns.put("00000100________".toCharArray(), Instruction.class);
		patterns.put("010_rraaaaaaaaaa".toCharArray(), Instruction.class);
		patterns.put("011_rraaaaaaaaaa".toCharArray(), Instruction.class);
		patterns.put("00000101________".toCharArray(), Instruction.class);
		patterns.put("00001000________".toCharArray(), Instruction.class);
		patterns.put("00001001________".toCharArray(), Instruction.class);
		patterns.put("00001100________".toCharArray(), Instruction.class);
		patterns.put("0000rr100_______".toCharArray(), Instruction.class);
		patterns.put("0000rr110_______".toCharArray(), Instruction.class);
		patterns.put("000000001_______".toCharArray(), Instruction.class);
		patterns.put("0001rr10________".toCharArray(), Instruction.class);
		patterns.put("0001rr01________".toCharArray(), Instruction.class);
		patterns.put("0001rr11________".toCharArray(), Instruction.class);
		patterns.put("0001rr00________".toCharArray(), Instruction.class);
		patterns.put("00110_aaaaaaaaaa".toCharArray(), Instruction.class);
		patterns.put("00101_aaaaaaaaaa".toCharArray(), Instruction.class);
		patterns.put("00111_aaaaaaaaaa".toCharArray(), Instruction.class);
		patterns.put("00100_aaaaaaaaaa".toCharArray(), Instruction.class);
		patterns.put("0000000000000000".toCharArray(), Instruction.class);
	}

	/**
	 * Basierend auf dem übergebenen binären array muss ein Objekt der Klasse Instruction erstellt werden. Im Package
	 * instructions gibt es eine Anzahl davon - von dort muss eine genommen werden!
	 * 
	 * @param binInstruction
	 *            character array aus Nullen und Einsen
	 * @return eine Instruktion, die ausgeführt werden kann.
	 */
	public static Instruction instructionDecode(char[] binInstruction) {
		Map.Entry<char[], Class<? extends Instruction>> entry = lookupPattern(binInstruction);
		char[][] ops = decodeOperands(entry.getKey(), binInstruction);

		Class<? extends Instruction> klass = entry.getValue();
		if (ops[0] != null && ops[1] != null) {
			return invokeBConstructor(klass, readUnsigned(ops[0]), readUnsigned(ops[1]));
		} else if (ops[0] != null) {
			return invokeUConstructor(klass, readUnsigned(ops[0]));
		} else if (ops[1] != null) {
			return invokeUConstructor(klass, readUnsigned(ops[1]));
		} else if (ops[2] != null) {
			return invokeUConstructor(klass, readSigned(ops[2]));
		} else {
			return invokeDConstructor(klass);
		}
	}

	private static Map.Entry<char[], Class<? extends Instruction>> lookupPattern(char[] instr) {
		withPatterns: for (Map.Entry<char[], Class<? extends Instruction>> entry : patterns.entrySet()) {
			char[] pat = entry.getKey();
			for (int i = 0; i < 16; i++) {
				// eine zahl, aber nicht gleich
				if (pat[i] < 58 && pat[i] != instr[i]) {
					continue withPatterns;
				}
			}
			return entry;
		}
		throw new IllegalArgumentException("Instruction [" + Arrays.toString(instr) + "] matches none of the pattern!");
	}

	/**
	 * Diese Methode liest die Operanden nach dem vorgegebenen Muster aus der Instruktion. Dabei wird nach den
	 * jeweiligen Buchstaben gesucht und dann die Werte gelesen.
	 * 
	 * @return ein array aus char-arrays. immer drei! {Register, Adresse, Nummer}. Wenn ein Operand nicht dabei war, ist
	 *         das array an der stelle null.
	 */
	private static char[][] decodeOperands(char[] pat, char[] instr) {
		char[] register = null, address = null, number = null;
		for (int i = 0; i < 16; i++) {
			switch (pat[i]) {
			case 'r':
				register = new char[] { instr[i], instr[i + 1] };
				i += 2;
				break;
			case 'a':
				address = new char[] { instr[i], instr[i + 1], instr[i + 2], instr[i + 3], instr[i + 4], instr[i + 5],
						instr[i + 6], instr[i + 7], instr[i + 8], instr[i + 9] };
				i += 10;
				break;
			case 'n':
				number = new char[] { instr[i], instr[i + 1], instr[i + 2], instr[i + 3], instr[i + 4], instr[i + 5],
						instr[i + 6], instr[i + 7], instr[i + 8], instr[i + 9], instr[i + 10], instr[i + 11],
						instr[i + 12], instr[i + 13], instr[i + 14] };
				i += 15;
				break;
			case '0':
				// fall through
			case '1':
				break;
			default:
				throw new IllegalArgumentException("Unrecognized Pattern " + Arrays.toString(pat));
			}
		}
		return new char[][] { register, address, number };
	}

	private static int readUnsigned(char[] vals) {
		int val = 0;
		for (int i = vals.length - 1, n = 0; i >= 0; i--, n++) {
			if (vals[i] == '1') {
				val += 2 ^ n;
			}
		}
		return val;
	}

	private static int readSigned(char[] vals) {
		if (vals[0] == '0') {
			return readUnsigned(vals);
		}
		return Integer.parseInt(new String(vals), 2);
	}
	

	private static Instruction invokeBConstructor(Class<? extends Instruction> klass, int param1,
			int param2) {
		try {
			Constructor<? extends Instruction> c = klass.getConstructor(Integer.class, Integer.class);
			return c.newInstance(param1, param2);
		} catch (Exception e) {
			throw new IllegalStateException("Failed to create an Instance of " + klass.getName() + " with Arguments " + param1 + " and " + param2);
		}
	}
	
	private static Instruction invokeUConstructor(Class<? extends Instruction> klass, int param1) {
		try {
			Constructor<? extends Instruction> c = klass.getConstructor(Integer.class);
			return c.newInstance(param1);
		} catch (Exception e) {
			throw new IllegalStateException("Failed to create an Instance of " + klass.getName() + " with Argument " + param1);
		}
	}
	
	private static Instruction invokeDConstructor(Class<? extends Instruction> klass) {
		try {
			return (Instruction) klass.newInstance();
		} catch (Exception e) {
			throw new IllegalStateException("Failed to create an Instance of " + klass.getName());
		}
	}


}
