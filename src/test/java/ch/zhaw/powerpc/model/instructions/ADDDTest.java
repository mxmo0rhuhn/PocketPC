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
import static org.junit.Assert.*;

public class ADDDTest {

    @Test
    public void adddZero() {
        ControlUnit cu = new ControlUnit(new MainMemory());
        cu.getRegisters()[0].write(0);

        ADDD addd = new ADDD(0);
        addd.run(cu);

        assertFalse(cu.getAlu().isCarryFlag());
        assertEquals(0, cu.getRegisters()[0].read());
    }

    @Test
    public void adddOne() {
        ControlUnit cu = new ControlUnit(new MainMemory());
        cu.getRegisters()[0].write(0);

        ADDD addd = new ADDD(1);
        addd.run(cu);

        assertFalse(cu.getAlu().isCarryFlag());
        assertEquals(1, cu.getRegisters()[0].read());
    }

    @Test
    public void adddMinusOneToZero() {
        ControlUnit cu = new ControlUnit(new MainMemory());
        cu.getRegisters()[0].write(0);

        ADDD addd = new ADDD(-1);
        addd.run(cu);

        assertFalse(cu.getAlu().isCarryFlag());
        assertEquals(-1, cu.getRegisters()[0].read());
    }

    @Test
    public void adddMinusOneToOne() {
        ControlUnit cu = new ControlUnit(new MainMemory());
        cu.getRegisters()[0].write(1);

        ADDD addd = new ADDD(-1);
        addd.run(cu);

        assertTrue(cu.getAlu().isCarryFlag());
        assertEquals(0, cu.getRegisters()[0].read());
    }

    @Test
    public void testOverflowNot() {
        ControlUnit cu = new ControlUnit(new MainMemory());
        cu.getRegisters()[0].write(21000);

        ADDD addd = new ADDD(16383);
        addd.run(cu);

        assertFalse(cu.getAlu().isCarryFlag());
        assertEquals(-28153, cu.getRegisters()[0].read());
    }

    @Test
    public void testToString() {
        assertEquals("ADDD #-1", new ADDD(-1).toString());
        assertEquals("ADDD #0", new ADDD(0).toString());
        assertEquals("ADDD #1", new ADDD(1).toString());
        assertEquals("ADDD #2", new ADDD(2).toString());
        assertEquals("ADDD #42", new ADDD(42).toString());
        assertEquals("ADDD #73", new ADDD(73).toString());
        assertEquals("ADDD #16383", new ADDD(16383).toString());
    }

    @Test
    public void testGetBinary() {
        binEquals("1111111111111111", new ADDD(-1).getBinary());
        binEquals("1000000000000000", new ADDD(0).getBinary());
        binEquals("1000000000000001", new ADDD(1).getBinary());
        binEquals("1000000000000010", new ADDD(2).getBinary());
        binEquals("1000000000101010", new ADDD(42).getBinary());
        binEquals("1000000001001001", new ADDD(73).getBinary());
        binEquals("1011111111111111", new ADDD(16383).getBinary());
    }
}
