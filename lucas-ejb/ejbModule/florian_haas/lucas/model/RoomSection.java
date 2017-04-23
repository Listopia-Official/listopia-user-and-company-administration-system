package florian_haas.lucas.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class RoomSection extends EntityBase {

	private static final long serialVersionUID = -7109692305948870006L;

	@ManyToOne
	@JoinColumn(nullable = false)
	@NotNull
	private Room room;

	@JoinColumn(unique = true, nullable = true)
	@OneToOne(optional = true, mappedBy = "section")
	private Company company;

	RoomSection() {}

	public RoomSection(Room room) {
		this.room = room;
	}

	public Room getRoom() {
		return room;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public Integer getSectionIndex() {
		return this.getRoom().getSections().indexOf(this) + 1;
	}
}
