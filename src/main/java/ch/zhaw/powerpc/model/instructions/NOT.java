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

package ch.zhaw.powerpc.model.instructions;

import ch.zhaw.powerpc.model.ControlUnit;
import ch.zhaw.powerpc.model.Register;

/**
 * Alle Bits im Akku werden Bitsweise negiert
 *
 * @author Reto
 */
public class NOT extends AbstractInstruction {

    public NOT() {
        // keine operanden
    }

    @Override
    public int run(ControlUnit controlUnit) {
        Register accu = controlUnit.getRegisters()[0];
        accu.write(~accu.read());
        return controlUnit.getProgramCounter() + 2;
    }

    @Override
    public String toString() {
        return "NOT";
    }

    @Override
    public char getBinary() {
        return genBin("0000000010000000");
    }

}
