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


public class ANDTest {

    @Test
    public void shouldAndBitwise() {

        ControlUnit cu = new ControlUnit(new MainMemory());

        short[] validData = new short[]{42, 1023};
        cu.getMemory().writeData(500, validData[0]);
        cu.getMemory().writeData(502, validData[1]);
        new LWDD(0, 500).run(cu);
        new LWDD(1, 502).run(cu);
        new AND(1).run(cu);
        Integer.toBinaryString(cu.getRegisters()[0].read());
        assertEquals("101010", Integer.toBinaryString(cu.getRegisters()[0].read()));

    }

    @Test
    public void shouldGetBinary() {
        binEquals("0000011000000000", (new AND(1).getBinary()));
        binEquals("0000101000000000", (new AND(2).getBinary()));
        binEquals("0000111000000000", new AND(3).getBinary());
        binEquals("0000001000000000", new AND(0).getBinary());

    }

    @Test
    public void shouldToString() {
        assertEquals("AND 0", new AND(0).toString());
        assertEquals("AND 1", new AND(1).toString());
        assertEquals("AND 2", new AND(2).toString());
        assertEquals("AND 3", new AND(3).toString());

    }


}
