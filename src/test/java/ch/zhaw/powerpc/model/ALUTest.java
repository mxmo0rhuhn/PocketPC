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

package ch.zhaw.powerpc.model;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ALUTest {

    @Test
    public void shouldNotOverflow() {
        ALU alu = new ALU(new Register[]{new Register(), new Register(), new Register(), new Register()});
        alu.addToAccu((short) 28000);
        alu.addToAccu((short) 26642);
        assertFalse("0111000010000000 + 0110100000010010", alu.isCarryFlag());
    }

    @Test
    public void shouldNotOverflow2() {
        ALU alu = new ALU(new Register[]{new Register(), new Register(), new Register(), new Register()});
        alu.addToAccu((short) 0);
        alu.addToAccu((short) 0);
        assertFalse(alu.isCarryFlag());
    }

    @Test
    public void shouldNotOverflow3() {
        ALU alu = new ALU(new Register[]{new Register(), new Register(), new Register(), new Register()});
        alu.addToAccu((short) 2000);
        alu.addToAccu((short) 2642);
        assertFalse(alu.isCarryFlag());
    }

    @Test
    public void shouldNotOverflow4() {
        ALU alu = new ALU(new Register[]{new Register(), new Register(), new Register(), new Register()});
        alu.addToAccu((short) -32768);
        alu.addToAccu((short) 1);
        assertFalse(alu.isCarryFlag());
    }

    @Test
    public void shouldNotOverflow5() {
        ALU alu = new ALU(new Register[]{new Register(), new Register(), new Register(), new Register()});
        alu.addToAccu((short) 32767);
        alu.addToAccu((short) 32767);
        assertFalse("01111111111111111 + 01111111111111111", alu.isCarryFlag());
    }

    @Test
    public void shouldOverflow() {
        ALU alu = new ALU(new Register[]{new Register(), new Register(), new Register(), new Register()});
        alu.addToAccu((short) -32768);
        alu.addToAccu((short) -32768);
        assertTrue(alu.isCarryFlag());
    }

    @Test
    public void shouldOverflow2() {
        ALU alu = new ALU(new Register[]{new Register(), new Register(), new Register(), new Register()});
        alu.addToAccu((short) -16384);
        alu.addToAccu((short) -32768);
        assertTrue(alu.isCarryFlag());
    }

    @Test
    public void testOverFlowFunction() {
        assertFalse(ALU.checkOverflow((short) 0, (short) 0));
    }

    @Test
    public void testOverFlowFunction2() {
        assertFalse(ALU.checkOverflow((short) 32767, (short) 1));
    }

    @Test
    public void testOverFlowFunction3() {
        assertFalse(ALU.checkOverflow((short) 32766, (short) 2));
    }

    @Test
    public void testOverFlowFunction4() {
        assertFalse(ALU.checkOverflow((short) 32765, (short) 3));
    }

    @Test
    public void testOverFlowFunction5() {
        assertFalse(ALU.checkOverflow((short) 16383, (short) 0));
    }

    @Test
    public void testOverFlowFunction6() {
        assertFalse(ALU.checkOverflow((short) 16383, (short) 1));
    }

    @Test
    public void testOverFlowFunction7() {
        assertFalse(ALU.checkOverflow((short) 16383, (short) -16384));
    }

    @Test
    public void testOverFlowFunction8() {
        assertFalse(ALU.checkOverflow((short) 16383, (short) -32786));
    }

    @Test
    public void testOverFlowFunction9() {
        assertTrue(ALU.checkOverflow((short) 16383, (short) -8192));
    }

    @Test
    public void testOverFlowFunction10() {
        assertTrue(ALU.checkOverflow((short) 32767, (short) -16384));
    }

    @Test
    public void testOverFlowFunction11() {
        assertTrue(ALU.checkOverflow((short) -1, (short) 1));
    }

}
