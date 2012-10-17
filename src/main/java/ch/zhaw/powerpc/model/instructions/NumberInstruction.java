package ch.zhaw.powerpc.model.instructions;

abstract class NumberInstruction implements Instruction {

	private final String name;

	private final int number;

	NumberInstruction(String name, int number) {
		this.name = name;
		this.number = number;
	}

	public String getName() {
		return this.name;
	}

	public int getNumber() {
		return this.number;
	}

	public int hashCode() {
		return 31 * this.name.hashCode() + 37 * this.number;
	}

	public boolean equals(Object other) {
		if (!(other instanceof NumberInstruction)) {
			return false;
		}
		NumberInstruction otherNumber = (NumberInstruction) other;
		return this.name.equals(otherNumber.name) && this.number == otherNumber.number;
	}

}
