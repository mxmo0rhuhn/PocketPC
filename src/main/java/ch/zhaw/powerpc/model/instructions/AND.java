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
 * Akku und Register xx (00 bis 11 fuer Akku, R-1, R-2 bwz R-3) werden bitweise
 * logisch mit AND verknüpft.
 *
 * @author des
 */

public class AND extends AbstractInstruction {

    private final int register;

    public AND(int register) {
        checkRegisterBounds(register);
        this.register = register;
    }

    @Override
    public int run(ControlUnit controlUnit) {
        int akku = controlUnit.getRegisters()[0].read();
        int reg = controlUnit.getRegisters()[this.register].read();
        int result = 0;
        result = akku & reg;
        controlUnit.getRegisters()[0].write(result);
        return controlUnit.getProgramCounter() + 2;
    }

    @Override
    public String toString() {
        return "AND " + this.register;

    }

    @Override
    public char getBinary() {
        return genBin("0000", reg(this.register), "1000000000");
    }

}
