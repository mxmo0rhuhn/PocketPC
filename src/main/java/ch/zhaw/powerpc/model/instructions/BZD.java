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
package ch.zhaw.powerpc.model.instructions;

import ch.zhaw.powerpc.model.ControlUnit;

/**
 * Wenn der Akku 0 ist, verzweige an die durch den Operanden angegebene Speicheradresse; sonst wird das Programm mit dem folgenden Befehl
 * forgesetzt. Mit 10 Bit können 1KiB Speicher adressiert werden.
 *
 * @author Max
 */
public class BZD extends AbstractInstruction {

    private final int address;

    /**
     * Erstellt einen Sprungbefehl auf eine Speicheradresse mit der Bedingung, dass der Akku = 0 ist
     *
     * @param register eine Adresse auf die gesprungen werden soll ... ACHTUNG es wird keineswegs geprüft ob der Inhalt des Speichers Sinn ergibt
     *                 für den Sprung => dies kann schnell im NIRVANA enden... höchst kritisch ...
     */
    public BZD(int address) {
        checkAddressBoundsInstruction(address);
        this.address = address;
    }

    /*
     * (non-Javadoc)
     *
     * @see ch.zhaw.powerpc.model.instructions.Instruction#run(ch.zhaw.powerpc.model.ControlUnit)
     */
    @Override
    public int run(ControlUnit controlUnit) {
        if (controlUnit.getRegisters()[0].read() == 0) {
            return (int) address;
        }
        return controlUnit.getProgramCounter() + 2;
    }

    @Override
    public String toString() {
        return "BZD #" + this.address;
    }

    @Override
    public char getBinary() {
        return genBin("00110", "0", adr(this.address));
    }

}
