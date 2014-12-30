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

/**
 *
 */
package ch.zhaw.powerpc.model.instructions;

import ch.zhaw.powerpc.model.ControlUnit;
import ch.zhaw.powerpc.model.MainMemory;
import org.junit.Test;

import static ch.zhaw.powerpc.model.instructions.TestUtil.binEquals;
import static org.junit.Assert.assertEquals;

/**
 * Testet den durch das carry bit bedingten Sprung auf die Adresse aus einem Register
 *
 * @author Max
 */
public class BCTest {

    @Test
    public void roundTrip() {
        MainMemory mem = new MainMemory();
        mem.setInstruction(100, new BC(02));
        mem.setInstruction(102, new BC(03));

        ControlUnit cu = new ControlUnit((mem));

        cu.getRegisters()[02].write(132);
        cu.getRegisters()[03].write(122);
        assertEquals(100, cu.getProgramCounter());

        cu.getAlu().setCarryFlag(false);
        cu.runCycle();

        assertEquals(102, cu.getProgramCounter());

        cu.getAlu().setCarryFlag(true);
        cu.runCycle();

        assertEquals(122, cu.getProgramCounter());
    }

    @Test
    public void testJumpAkku() {

        ControlUnit cu = new ControlUnit(new MainMemory());
        cu.getRegisters()[00].write(104);

        assertEquals(100, cu.getProgramCounter());

        cu.getAlu().setCarryFlag(true);
        assertEquals(104, new BC(00).run(cu));
    }

    @Test
    public void testNoJumpAkku() {

        ControlUnit cu = new ControlUnit(new MainMemory());
        cu.getRegisters()[00].write(104);

        assertEquals(100, cu.getProgramCounter());

        cu.getAlu().setCarryFlag(false);
        assertEquals(102, new BC(00).run(cu));
    }

    @Test
    public void testJumpRegisterOne() {

        ControlUnit cu = new ControlUnit(new MainMemory());
        cu.getRegisters()[01].write(222);

        assertEquals(100, cu.getProgramCounter());

        cu.getAlu().setCarryFlag(true);
        assertEquals(222, new BC(01).run(cu));
    }

    @Test
    public void testNoJumpRegisterOne() {

        ControlUnit cu = new ControlUnit(new MainMemory());
        cu.getRegisters()[01].write(222);

        assertEquals(100, cu.getProgramCounter());

        cu.getAlu().setCarryFlag(false);
        assertEquals(102, new BC(01).run(cu));
    }

    @Test
    public void testJumpRegisterTwo() {

        ControlUnit cu = new ControlUnit(new MainMemory());
        cu.getRegisters()[02].write(134);

        assertEquals(100, cu.getProgramCounter());

        cu.getAlu().setCarryFlag(true);
        assertEquals(134, new BC(02).run(cu));
    }

    @Test
    public void testNoJumpRegisterTwo() {

        ControlUnit cu = new ControlUnit(new MainMemory());
        cu.getRegisters()[02].write(134);

        assertEquals(100, cu.getProgramCounter());

        cu.getAlu().setCarryFlag(false);
        assertEquals(102, new BC(02).run(cu));
    }

    @Test
    public void testJumpRegisterThree() {

        ControlUnit cu = new ControlUnit(new MainMemory());
        cu.getRegisters()[03].write(144);

        assertEquals(100, cu.getProgramCounter());

        cu.getAlu().setCarryFlag(true);
        assertEquals(144, new BC(03).run(cu));
    }

    @Test
    public void testNoJumpRegisterThree() {

        ControlUnit cu = new ControlUnit(new MainMemory());
        cu.getRegisters()[03].write(144);

        assertEquals(100, cu.getProgramCounter());

        cu.getAlu().setCarryFlag(false);
        assertEquals(102, new BC(03).run(cu));
    }

    @Test
    public void testJumpToLastWord() {

        ControlUnit cu = new ControlUnit(new MainMemory());
        cu.getRegisters()[01].write(498);

        assertEquals(100, cu.getProgramCounter());

        cu.getAlu().setCarryFlag(true);

        assertEquals(498, new BC(01).run(cu));
    }

    @Test
    public void testJumpToFirstWord() {

        ControlUnit cu = new ControlUnit(new MainMemory());
        cu.getRegisters()[02].write(100);

        assertEquals(100, cu.getProgramCounter());

        cu.getAlu().setCarryFlag(true);

        assertEquals(100, new BC(02).run(cu));
    }

    @Test(expected = InvalidInstructionException.class)
    public void badJumpToLow() {
        ControlUnit cu = new ControlUnit(new MainMemory());
        cu.getRegisters()[02].write(50);
        new BC(02).run(cu);
    }

    @Test(expected = InvalidInstructionException.class)
    public void badJumpToHigh() {
        ControlUnit cu = new ControlUnit(new MainMemory());
        cu.getRegisters()[01].write(510);
        new BC(01).run(cu);
    }

    @Test(expected = InvalidInstructionException.class)
    public void badJump() {
        ControlUnit cu = new ControlUnit(new MainMemory());
        cu.getRegisters()[03].write(135);
        new BC(03).run(cu);
    }

    @Test
    public void binary() {
        binEquals("0001001100000000", new BC(00).getBinary());
        binEquals("0001011100000000", new BC(01).getBinary());
        binEquals("0001101100000000", new BC(02).getBinary());
        binEquals("0001111100000000", new BC(03).getBinary());
    }
}