package florian_haas.lucas.model;

import java.util.*;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
public class Room extends EntityBase {

	private static final long serialVersionUID = -5467840420720996782L;

	@Basic(optional = false)
	@Column(nullable = false, unique = true)
	@NotBlank
	private String name;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER, mappedBy = "room")
	@Valid
	@NotNull
	private List<RoomSection> sections = new ArrayList<>();

	Room() {}

	public Room(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<RoomSection> getSections() {
		return Collections.unmodifiableList(this.sections);
	}

	public Boolean addSection(RoomSection section) {
		return sections.add(section);
	}

	public Boolean removeSection(RoomSection section) {
		return this.sections.remove(section);
	}
}
