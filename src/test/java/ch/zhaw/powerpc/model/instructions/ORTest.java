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

public class ORTest {

    @Test
    public void shouldOrSetAllBits() {
        ControlUnit cu = new ControlUnit(new MainMemory());
        cu.getRegisters()[0].write(-1);
        cu.getRegisters()[3].write(21845);

        OR or = new OR(2);
        or.run(cu);

        assertEquals(-1, cu.getRegisters()[0].read());
    }

    @Test
    public void shouldOrWithThirdRegister() {
        ControlUnit cu = new ControlUnit(new MainMemory());
        cu.getRegisters()[0].write(0);
        cu.getRegisters()[3].write(21845);

        OR or = new OR(3);
        or.run(cu);

        assertEquals(21845, cu.getRegisters()[0].read());
    }

    @Test
    public void shouldOrWithSecondRegister() {
        ControlUnit cu = new ControlUnit(new MainMemory());
        cu.getRegisters()[0].write(-1);
        cu.getRegisters()[2].write(-1);

        OR or = new OR(2);
        or.run(cu);

        assertEquals(-1, cu.getRegisters()[0].read());
    }

    @Test
    public void shouldOrWithFirstRegister() {
        ControlUnit cu = new ControlUnit(new MainMemory());
        cu.getRegisters()[0].write(0);
        cu.getRegisters()[1].write(-1);

        OR or = new OR(1);
        or.run(cu);

        assertEquals(-1, cu.getRegisters()[0].read());
    }

    @Test
    public void shouldLeaveZeroAsIs() {
        ControlUnit cu = new ControlUnit(new MainMemory());
        cu.getRegisters()[0].write(0);

        OR or = new OR(0);
        or.run(cu);

        assertEquals(0, cu.getRegisters()[0].read());
    }

    @Test
    public void shouldLeaveZeroAsIs2() {
        ControlUnit cu = new ControlUnit(new MainMemory());
        cu.getRegisters()[0].write(0);
        cu.getRegisters()[1].write(0);

        OR or = new OR(1);
        or.run(cu);

        assertEquals(0, cu.getRegisters()[0].read());
    }

    @Test
    public void shouldLeaveZeroAsIs3() {
        ControlUnit cu = new ControlUnit(new MainMemory());
        cu.getRegisters()[0].write(0);
        cu.getRegisters()[2].write(0);

        OR or = new OR(2);
        or.run(cu);

        assertEquals(0, cu.getRegisters()[0].read());
    }

    @Test
    public void shouldLeaveZeroAsIs4() {
        ControlUnit cu = new ControlUnit(new MainMemory());
        cu.getRegisters()[0].write(0);
        cu.getRegisters()[3].write(0);

        OR or = new OR(3);
        or.run(cu);

        assertEquals(0, cu.getRegisters()[0].read());
    }

    @Test
    public void testToString() {
        assertEquals("OR 0", new OR(0).toString());
        assertEquals("OR 1", new OR(1).toString());
        assertEquals("OR 2", new OR(2).toString());
        assertEquals("OR 3", new OR(3).toString());
    }

    @Test
    public void testGetBinary() {
        binEquals("0000001100000000", new OR(0).getBinary());
        binEquals("0000011100000000", new OR(1).getBinary());
        binEquals("0000101100000000", new OR(2).getBinary());
        binEquals("0000111100000000", new OR(3).getBinary());
    }

}
