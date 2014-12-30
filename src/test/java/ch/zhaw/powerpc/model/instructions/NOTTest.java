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
import ch.zhaw.powerpc.model.MainMemory;
import ch.zhaw.powerpc.model.Register;
import org.junit.Test;

import static ch.zhaw.powerpc.model.instructions.TestUtil.binEquals;
import static org.junit.Assert.assertEquals;

public class NOTTest {

    @Test
    public void invertZero() {
        ControlUnit cu = new ControlUnit(new MainMemory());
        Register accu = cu.getRegisters()[0];

        accu.write(0);
        new NOT().run(cu);
        assertEquals(-1, accu.read());
    }

    @Test
    public void invertOne() {
        ControlUnit cu = new ControlUnit(new MainMemory());
        Register accu = cu.getRegisters()[0];

        accu.write(-1);
        new NOT().run(cu);
        assertEquals(0, accu.read());
    }

    @Test
    public void invertZeroOne() {
        ControlUnit cu = new ControlUnit(new MainMemory());
        Register accu = cu.getRegisters()[0];

        accu.write(Short.parseShort("0101010101010101", 2));
        new NOT().run(cu);

        // 1010101010101010
        assertEquals(-21846, accu.read());
    }

    @Test
    public void testGetBinary() {
        NOT not = new NOT();
        binEquals("0000000010000000", not.getBinary());
    }

    @Test
    public void testToString() {
        NOT not = new NOT();
        assertEquals("NOT", not.toString());
    }

}
