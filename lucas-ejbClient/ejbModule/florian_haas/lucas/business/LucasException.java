package florian_haas.lucas.business;

import java.util.*;

import javax.ejb.ApplicationException;

@ApplicationException(rollback = true)
public class LucasException extends RuntimeException {

	private static final long serialVersionUID = 8660708175489434160L;

	private String mark = null;

	private List<Object> params = new ArrayList<>();

	public LucasException() {
		super();
	}

	public LucasException(Throwable t) {
		super(t);
	}

	public LucasException(String message) {
		super(message);
	}

	public LucasException(String message, String mark) {
		super(message);
		this.mark = mark;
	}

	public LucasException(String message, String mark, Object... params) {
		super(message);
		this.mark = mark;
		this.params.addAll(Arrays.asList(params));
	}

	public LucasException(String message, Throwable t) {
		super(message, t);
	}

	public String getMark() {
		return this.mark;
	}

	public List<Object> getParams() {
		return Collections.unmodifiableList(params);
	}

}
