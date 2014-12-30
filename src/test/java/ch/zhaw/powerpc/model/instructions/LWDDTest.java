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

public class LWDDTest {

    @Test
    public void testLoad() {
        short[] validData = new short[]{-32768, -11111, -73, -42, -11, -2, -1, 0, 1, 2, 3, 11, 42, 73, 128, 1023,
                1024, 11111, 32767};
        for (short s : validData) {
            ControlUnit cu = new ControlUnit(new MainMemory());
            cu.getMemory().writeData(500, s);
            new LWDD(0, 500).run(cu);
            assertEquals(s, cu.getRegisters()[0].read());
        }
    }

    @Test
    public void testGetBinary() {
        binEquals("0100000111110100", new LWDD(0, 500).getBinary());
        binEquals("0100010111110110", new LWDD(1, 502).getBinary());
        binEquals("0100101000101100", new LWDD(2, 556).getBinary());
        binEquals("0100101000101010", new LWDD(2, 554).getBinary());
        binEquals("0100111111111110", new LWDD(3, 1022).getBinary());
    }
}
