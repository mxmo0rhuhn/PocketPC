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

package ch.zhaw.powerpc.model;

import ch.zhaw.powerpc.model.instructions.Instruction;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Diese Klasse repräsentiert den Hauptspeicher (RAM). Hier liegen Befehle und Daten.
 *
 * @author Max / Reto
 */
public final class MainMemory {

    private final Map<Integer, Short> data = new HashMap<Integer, Short>();

    private final Map<Integer, Instruction> instructions = new HashMap<Integer, Instruction>();

    /**
     * Die Klasse wird mit einem initialen Set an Daten instanziert. Hier muss z.B. schon das Programm in den Daten
     * sein, sowie allenfalls notwendige Daten für das Programm.
     * <p/>
     * Achtung: Das Array muss hier schon die finale grösse haben, weil Arrays können später in der Grösse nicht mehr
     * verändert werden.
     */
    public MainMemory() {
    }

    /**
     * Lese einen Wert im Speicher von eine bestimmten Adresse.
     *
     * @throws IllegalArgumentException wenn die Adresse zu gross oder zu klein ist.
     */
    public short readData(int address) {
        if (address % 2 == 1 || address < 500 || address > 1023) {
            throw new IllegalArgumentException("Hier darf es keine Daten geben: " + address);
        }
        if (!this.data.containsKey(address)) {
            return 0;
        }
        return this.data.get(address);
    }

    /**
     * Schreibe einen Wert in den Speicher an einer bestimmten Adresse.
     *
     * @throws IllegalArgumentException wenn die Adresse zu gross oder zu klein ist.
     */
    public void writeData(int address, short data) {
        if (address % 2 == 1 || address < 500 || address > 1023) {
            throw new IllegalArgumentException("Hier darf es keine Daten geben: " + address);
        }
        this.data.put(address, data);
    }

    /**
     * Lese eine Instruktion von dieser Adresse.
     *
     * @param address
     * @return Die Instruktion von der Stelle oder null, wenn sie nicht existiert
     */
    public Instruction readInstruction(int address) {
        if (address < 100 || address % 2 == 1 || address > 498) {
            throw new IllegalArgumentException("Hier gibt es keine Instruktionen: " + address);
        }
        return this.instructions.get(address);
    }

    /**
     * Schreibt die Instruktion an die spezifizierte Stelle.
     *
     * @param address
     * @param instruction
     */
    public void setInstruction(int address, Instruction instruction) {
        if (address < 100 || address % 2 == 1 || address > 498) {
            throw new IllegalArgumentException("Hier gibt es keine Instruktionen: " + address);
        }
        this.instructions.put(address, instruction);
    }

    public Map<Integer, Instruction> getInstructions() {
        return Collections.unmodifiableMap(instructions);
    }

    public Map<Integer, Short> getData() {
        return Collections.unmodifiableMap(data);
    }

    @Override
    public String toString() {
        return "\n\tdata:  " + data + "\n" +
                "\tinstr: " + instructions + "";
    }
}