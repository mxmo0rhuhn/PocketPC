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
import org.junit.Test;

import static ch.zhaw.powerpc.model.instructions.TestUtil.binEquals;
import static org.junit.Assert.assertEquals;

public class SWDDTest {

    @Test(expected = InvalidInstructionException.class)
    public void shouldNotAcceptOddMemoryAddress() {
        new SWDD(0, 501);
    }

    @Test(expected = InvalidInstructionException.class)
    public void shouldNotAcceptInvalidRegister() {
        new SWDD(4, 502);
    }

    @Test
    public void shouldWriteFirstRegisterAtSpecifiedPosition() {
        ControlUnit cu = new ControlUnit(new MainMemory());
        cu.getRegisters()[1].write(42);

        SWDD swdd = new SWDD(1, 500);
        swdd.run(cu);

        assertEquals(42, cu.getMemory().readData(500));
    }

    @Test
    public void shouldWriteSecondRegisterAtSpecifiedPosition() {
        ControlUnit cu = new ControlUnit(new MainMemory());
        cu.getRegisters()[2].write(1111);

        SWDD swdd = new SWDD(2, 500);
        swdd.run(cu);

        assertEquals(1111, cu.getMemory().readData(500));
    }

    @Test
    public void shouldWriteThirdRegisterAtSpecifiedPosition() {
        ControlUnit cu = new ControlUnit(new MainMemory());
        cu.getRegisters()[3].write(-1);

        SWDD swdd = new SWDD(3, 500);
        swdd.run(cu);

        assertEquals(-1, cu.getMemory().readData(500));
    }

    @Test
    public void shouldWriteAkkuAtSpecifiedPosition() {
        ControlUnit cu = new ControlUnit(new MainMemory());
        cu.getRegisters()[0].write(73);

        SWDD swdd = new SWDD(0, 500);
        swdd.run(cu);

        assertEquals(73, cu.getMemory().readData(500));
    }

    @Test
    public void testToString() {
        assertEquals("SWDD 0 #500", new SWDD(0, 500).toString());
        assertEquals("SWDD 1 #502", new SWDD(1, 502).toString());
        assertEquals("SWDD 2 #504", new SWDD(2, 504).toString());
        assertEquals("SWDD 3 #506", new SWDD(3, 506).toString());
    }

    @Test
    public void testGetBinary() {
        binEquals("0110000111110100", new SWDD(0, 500).getBinary());
        binEquals("0110010111110110", new SWDD(1, 502).getBinary());
        binEquals("0110100111111000", new SWDD(2, 504).getBinary());
        binEquals("0110110111111010", new SWDD(3, 506).getBinary());
    }
}
