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

/**
 * Schreiben des derzeitigen Zustandes in ein textfile.
 *
 * @author Max
 */
public class InputWriter {

    public static void writeState(String filename, Map<Integer, String> data, Map<Integer, String> instructions) throws IOException {

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
        } finally {
            buWr.close();
        }
    }
}
