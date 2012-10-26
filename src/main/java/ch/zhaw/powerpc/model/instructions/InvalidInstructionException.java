package ch.zhaw.powerpc.model.instructions;

public class InvalidInstructionException extends RuntimeException {

	public InvalidInstructionException(String msg) {
		super(msg);
	}

	private static final long serialVersionUID = 73L;

}
