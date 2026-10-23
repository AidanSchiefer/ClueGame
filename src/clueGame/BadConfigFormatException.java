package clueGame;

public class BadConfigFormatException extends Exception {

	public BadConfigFormatException() {
		super("Invalid configuration file format.");
	}

	public BadConfigFormatException(String message) {
		super(message);
	}
}