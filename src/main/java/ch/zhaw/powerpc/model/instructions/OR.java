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
import ch.zhaw.powerpc.model.Register;

/**
 * Akku und Register xx (00 bis 11 für Akku, R-1, R-2 bzw R-3) werden bitweise logisch mit OD veknüpft.
 * 
 * @author Reto
 *
 */
public class OR extends AbstractInstruction {
	
	private final int register;
	
	public OR(int register) {
		checkRegisterBounds(register);
		this.register = register;
	}

	@Override
	public int run(ControlUnit controlUnit) {
		Register accu = controlUnit.getRegisters()[0];
		Register reg = controlUnit.getRegisters()[this.register];
		accu.write(accu.read() | reg.read());
		return controlUnit.getProgramCounter() + 2;
	}

	@Override
	public char getBinary() {
		return genBin("0000", reg(this.register), "1100000000");
	}
	
	@Override
	public String toString() {
		return "OR " + this.register;
	}

}
