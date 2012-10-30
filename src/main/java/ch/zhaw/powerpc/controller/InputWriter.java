/**
 * 
 */
package ch.zhaw.powerpc.controller;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

import ch.zhaw.powerpc.model.instructions.Instruction;

/**
 * Schreiben des derzeitigen Zustandes in ein textfile.
 * 
 * @author Max
 *
 */
public class InputWriter {
	
	public static void writeState(String filename, Map<Integer, Short> data, Map<Integer, Instruction> instructions) throws IOException {
		
		BufferedWriter buWr = new BufferedWriter(new FileWriter(filename));
		try {
		
		ArrayList<Integer> sortetInstructionKeys = new ArrayList<Integer>(instructions.keySet());
		Collections.sort(sortetInstructionKeys);
		
		for (Integer curInstruction : sortetInstructionKeys) {
			buWr.write(instructions.get(curInstruction).toString());
			buWr.newLine();
		}
		
		ArrayList<Integer> sortetDataKeys = new ArrayList<Integer>(data.keySet());
		Collections.sort(sortetDataKeys);
		
		for (Integer curDataRow : sortetDataKeys) {
			buWr.write(curDataRow + "=" + data.get(curDataRow).toString());
			buWr.newLine();
		}
	 	}finally {
	 		buWr.close();
	 	}
	}
}
