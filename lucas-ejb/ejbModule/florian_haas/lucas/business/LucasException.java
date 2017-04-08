package florian_haas.lucas.business;

import javax.ejb.ApplicationException;

@ApplicationException(rollback = true)
public class LucasException extends RuntimeException {

	private static final long serialVersionUID = 8660708175489434160L;

	public LucasException() {
		super();
	}

	public LucasException(Throwable t) {
		super(t);
	}

	public LucasException(String message) {
		super(message);
	}

	public LucasException(String message, Throwable t) {
		super(message, t);
	}

}
