package florian_haas.lucas.model;

import java.io.Serializable;

public abstract class EntityBase implements Serializable {

	private static final long serialVersionUID = -3022778277072552040L;

	private Long id;

	public Long getId() {
		return id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.id == null) ? 0 : this.id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		EntityBase other = (EntityBase) obj;
		if (this.id == null) {
			if (other.id != null) return false;
		} else if (!this.id.equals(other.id)) return false;
		return true;
	}

}
