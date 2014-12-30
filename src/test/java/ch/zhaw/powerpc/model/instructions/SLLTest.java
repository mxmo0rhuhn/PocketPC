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

public class SLLTest {

    @Test
    public void shouldShiftAllPositions() {
        ControlUnit cu = new ControlUnit(new MainMemory());
        cu.getRegisters()[0].write(-21846);

        SLL sll = new SLL();
        sll.run(cu);

        assertEquals(21844, cu.getRegisters()[0].read());
    }

    @Test
    public void shouldShiftAllPositions2() {
        ControlUnit cu = new ControlUnit(new MainMemory());
        cu.getRegisters()[0].write(21845);

        SLL sll = new SLL();
        sll.run(cu);

        assertEquals(-21846, cu.getRegisters()[0].read());
    }

    @Test
    public void shouldFillZeroesToLsb2() {
        ControlUnit cu = new ControlUnit(new MainMemory());
        cu.getRegisters()[0].write(-1);

        SLL sll = new SLL();
        sll.run(cu);

        assertEquals(-2, cu.getRegisters()[0].read());
    }

    @Test
    public void shouldFillZeroesToLsb() {
        ControlUnit cu = new ControlUnit(new MainMemory());
        cu.getRegisters()[0].write(0);

        SLL sll = new SLL();
        sll.run(cu);

        assertEquals(0, cu.getRegisters()[0].read());
    }

    @Test
    public void shouldNotSetCarryFlatIfMsbIsZero() {
        ControlUnit cu = new ControlUnit(new MainMemory());
        cu.getRegisters()[0].write(0);

        SLL sll = new SLL();
        sll.run(cu);

        assertFalse(cu.getAlu().isCarryFlag());
    }

    @Test
    public void shouldSetCarryFlagIfMsbIsOne() {
        ControlUnit cu = new ControlUnit(new MainMemory());
        cu.getRegisters()[0].write(-1);

        SLL sll = new SLL();
        sll.run(cu);

        assertTrue(cu.getAlu().isCarryFlag());
    }

    @Test
    public void testToString() {
        assertEquals("SLL", new SLL().toString());
    }

    @Test
    public void testGetBinary() {
        binEquals("0000110000000000", new SLL().getBinary());
    }
}