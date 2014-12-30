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

/**
 * Löse das Register 'Rxx' (alle Bit auf 0 setzen) und das Carry-Flag (00 bis 11 für: Akku, R-1, R-2, R-3)
 *
 * @author Reto
 */
public class CLR extends AbstractInstruction {

    private final int register;

    public CLR(int register) {
        checkRegisterBounds(register);
        this.register = register;
    }

    @Override
    public int run(ControlUnit controlUnit) {
        controlUnit.getRegisters()[this.register].write(0);
        controlUnit.getAlu().setCarryFlag(false);
        return controlUnit.getProgramCounter() + 2;
    }

    @Override
    public String toString() {
        return "CLR " + this.register;
    }

    @Override
    public char getBinary() {
        return genBin("0000" + reg(this.register) + "1010000000");
    }
}
