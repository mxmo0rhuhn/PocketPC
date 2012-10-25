package ch.zhaw.powerpc.model;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import ch.zhaw.powerpc.model.instructions.ADD;
import ch.zhaw.powerpc.model.instructions.ADDD;
import ch.zhaw.powerpc.model.instructions.CLR;
import ch.zhaw.powerpc.model.instructions.END;
import ch.zhaw.powerpc.model.instructions.Instruction;

/**
 * 
 * Diese Klasse dekodiert eine Instruktion (int) in ein Objekt vom Typ Instruction.
 * 
 * @author Reto
 * 
 */
public final class Decoder {

	/**
	 * Diese Map mappt ein Pattern auf eine spezifische Klasse. Es wird angenommen, dass die Pattern eindeutig sind und
	 * jedes Pattern genau einem Befehl zugewiesen werden kann. Die Pattern sind wie folgt definiert: Die Zahlen 0 und 1
	 * stehen für tatsächliche Nullen und Einsen, d.h. dies sind die eigentlichen identifizierenden Teile. Ein 'r' steht
	 * für ein Register Wert (d.h. es ist für das Pattern-Matching egal). Selbiges gilt für 'a' (Adresse) und 'n'
	 * (Nummer). Die Unterstriche '_' werden komplett ignoriert.
	 */
	private static final Map<char[], Class<? extends Instruction>> patterns = new HashMap<char[], Class<? extends Instruction>>();

	static {
		patterns.put("0000rr101_______".toCharArray(), CLR.class);
		patterns.put("0000rr111_______".toCharArray(), ADD.class);
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
		patterns.put("0001rr10________".toCharArray(), BZ.class);
		patterns.put("0001rr01________".toCharArray(), BNZ.class);
		patterns.put("0001rr11________".toCharArray(), BC.class);
		patterns.put("0001rr00________".toCharArray(), B.class);
		patterns.put("00110_aaaaaaaaaa".toCharArray(), BZD.class);
		patterns.put("00101_aaaaaaaaaa".toCharArray(), BNZD.class);
		patterns.put("00111_aaaaaaaaaa".toCharArray(), BCD.class);
		patterns.put("00100_aaaaaaaaaa".toCharArray(), BD.class);
		patterns.put("0000000000000000".toCharArray(), END.class);
	}

	/**
	 * Basierend auf dem übergebenen binären array muss ein Objekt der Klasse Instruction erstellt werden. Im Package
	 * instructions gibt es eine Anzahl davon - von dort muss eine genommen werden!
	 * 
	 * @param binInstruction
	 *            character array aus Nullen und Einsen
	 * @return eine Instruktion, die ausgeführt werden kann.
	 */
	public static Instruction instructionDecode(int instruction) {
		char[] binInstruction = convertToBin(instruction);
		Map.Entry<char[], Class<? extends Instruction>> entry = lookupPattern(binInstruction);
		char[][] ops = decodeOperands(entry.getKey(), binInstruction);

		Class<? extends Instruction> klass = entry.getValue();
		if (ops[0] != null && ops[1] != null) {
			// Register und Adresse
			return invokeBConstructor(klass, readUnsigned(ops[0]), readUnsigned(ops[1]));
		} else if (ops[0] != null) {
			// Register
			return invokeUConstructor(klass, readUnsigned(ops[0]));
		} else if (ops[1] != null) {
			// Adresse
			return invokeUConstructor(klass, readUnsigned(ops[1]));
		} else if (ops[2] != null) {
			// Nummer
			return invokeUConstructor(klass, readSigned(ops[2]));
		} else {
			// Kein Parameter
			return invokeDConstructor(klass);
		}
	}

	/**
	 * Konvertiere einen Int in ein char - array. Integer.toBinaryString sollte nicht direkt verwendet werden, weil dort
	 * das Array so klein als möglich sein wird (oder immer 32 Bit?). Auf jeden Fall wollen wir genau 16 Bit und das
	 * stellt diese Methode sicher.
	 */
	private static char[] convertToBin(int instr) {
		char[] binInstr = Integer.toBinaryString(instr).toCharArray();
		if (binInstr.length > 16) {
			throw new IllegalStateException("Instruction [" + instr + "] doesn't fit in 16 Bit");
		}
		char[] resArr = new char[16];
		// Fuelle vorne mit Nullen
		for (int i = 0; i < 16-binInstr.length; i++) {
			resArr[i] = '0';
		}
		System.arraycopy(binInstr, 0, resArr, (16 - binInstr.length), binInstr.length);
		return resArr;
	}

	/**
	 * Sucht die gegebene Instruktion in den definierten Patterns. Wenn es nicht existiert, ist die Instruktion
	 * ungültig, oder das Pattern wurde noch nicht erfasst.
	 * 
	 */
	private static Map.Entry<char[], Class<? extends Instruction>> lookupPattern(char[] instr) {
		/*
		 * Der Algorithmus ist hoch ineffizient. Es wird jedes Pattern einfach durchgetestet, d.h. es wird so lange über
		 * das Pattern ignoriert, wie die fest vorgegebenen Zeichen vom Pattern (0 und 1) mit der Instruktion
		 * übereinstimmen.
		 * 
		 * Verbesserung: Falls die Performance dieser Methode je zum Problem werden würde, könnte man es folgenderweise
		 * optimieren (wurde aus Zeitgründen nicht gemacht): Man erstellt einen Baum, wo jeweils ein Knoten ein Zeichen
		 * aus den Pattern ist, also '0', '1', 'r', 'a', 'n' oder '_'. Zu 'r', 'a', 'n' wird jeweils auf den Knoten noch
		 * ein Potenzfaktor gesetzt, d.h. beim jeweils ersten Auftreten von 'r' würde der Potenzfaktor 1 gesetzt werden
		 * und beim zweiten der Faktor 0. Somit könnte man während dem Traversieren des Baumes direkt die zugehörige
		 * Zahl mit dem entsprechenden Faktor potenzieren.
		 * 
		 * Dieser Algorithmus hätte eine Komplexität von O(1) und würde ausserdem die Methode decodeOperands überflüssig
		 * machen.
		 */
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
			case '_':
				// fall through
			case '0':
				// fall through
			case '1':
				break;
			default:
				throw new IllegalArgumentException("Instruction [" + Arrays.toString(instr) + "] does not match Pattern ["+Arrays.toString(pat)+"]");
			}
		}
		return new char[][] { register, address, number };
	}

	/**
	 * Konvertiere eine vorzeichenlose Bitfolge in einen int.
	 */
	private static int readUnsigned(char[] vals) {
		int val = 0;
		for (int i = vals.length - 1, n = 0; i >= 0; i--, n++) {
			if (vals[i] == '1') {
				val += Math.pow(2, n);
			}
		}
		return val;
	}

	private static int readSigned(char[] vals) {
		/*
		 * Die Methoden von Java (Integer.parseInt resp. Short.parseShort) können nicht verwendet werden, da sie eine
		 * bestimmte Länge des Arrays erwarten um das Vorzeichen richtig zu interpretieren.
		 */
		if (vals[0] == '0') {
			// positive Zahl
			return readUnsigned(vals);
		} else {
			// negative Zahl

			// Rechne das Zweierkomplement um
			// Invertiere alle Bits
			for (int i = 0; i < vals.length; i++) {
				if (vals[i] == '1') {
					vals[i] = '0';
				} else {
					vals[i] = '1';
				}
			}

			// Inkrementiere die Bitfolge um 1. Könnte bestimmt effizienter geschehen. Aber wie oft kommt dieser Code
			// zum Zug?
			vals = Integer.toString(Integer.parseInt(new String(vals), 2) + 1, 2).toCharArray();

			// Nun wird die Bitfolge ohne Vorzeichen interpretiert und negiert
			return (-1) * readUnsigned(vals);
		}
	}

	/**
	 * Invoke Binary: Rufe einen Konstruktor auf mit zwei int Parametern.
	 */
	private static Instruction invokeBConstructor(Class<? extends Instruction> klass, int param1, int param2) {
		try {
			Constructor<? extends Instruction> c = klass.getConstructor(int.class, int.class);
			return c.newInstance(param1, param2);
		} catch (Exception e) {
			throw new IllegalStateException("Failed to create an Instance of " + klass.getName() + " with Arguments "
					+ param1 + " and " + param2, e);
		}
	}

	/**
	 * Invoke Unary: Rufe einen Konstruktor mit einem int Parametern auf
	 */
	private static Instruction invokeUConstructor(Class<? extends Instruction> klass, int param1) {
		try {
			Constructor<? extends Instruction> c = klass.getConstructor(int.class);
			return c.newInstance(param1);
		} catch (Exception e) {
			throw new IllegalStateException("Failed to create an Instance of " + klass.getName() + " with Argument "
					+ param1, e);
		}
	}

	/**
	 * Invoke Default: Rufe einen Konstruktor ohne Parameter auf.
	 */
	private static Instruction invokeDConstructor(Class<? extends Instruction> klass) {
		try {
			return (Instruction) klass.newInstance();
		} catch (Exception e) {
			throw new IllegalStateException("Failed to create an Instance of " + klass.getName(), e);
		}
	}

}
