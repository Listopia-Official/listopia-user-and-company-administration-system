package florian_haas.lucas.model;

import java.util.*;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import florian_haas.lucas.util.validation.*;

@Entity
@DiscriminatorValue(value = "user")
public class User extends AccountOwner {

	private static final long serialVersionUID = -2632198556827472552L;

	@NotBlankString
	private String forename;

	@NotBlankString
	private String surname;

	private EnumSchoolClass schoolClass;

	@Lob
	@Basic(fetch = FetchType.LAZY)
	private byte[] image;

	@ElementCollection(fetch = FetchType.EAGER)
	@NotNull
	private List<@TypeNotNull String> ranks = new ArrayList<>();

	@OneToOne(cascade = CascadeType.ALL, optional = true, orphanRemoval = true)
	@Valid
	private Attendancedata attendancedata;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY, mappedBy = "user")
	@Valid
	@NotNull
	private Set<@TypeNotNull UserCard> userCards = new HashSet<>();

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY, mappedBy = "user")
	@Valid
	@NotNull
	private Set<@TypeNotNull Employment> employments = new HashSet<>();

	User() {}

	public User(String forename, String surname, EnumSchoolClass schoolClass, List<String> ranks) {
		this.forename = forename;
		this.surname = surname;
		this.schoolClass = schoolClass;
		if (ranks != null) this.ranks.addAll(ranks);
		if (getUserType() != EnumUserType.TEACHER) {
			attendancedata = new Attendancedata(this);
		}
	}

	public String getForename() {
		return this.forename;
	}

	public void setForename(String forename) {
		this.forename = forename;
	}

	public String getSurname() {
		return this.surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public EnumUserType getUserType() {
		return (forename == null && surname == null ? EnumUserType.GUEST : (schoolClass == null ? EnumUserType.TEACHER : EnumUserType.PUPIL));
	}

	public EnumSchoolClass getSchoolClass() {
		return this.schoolClass;
	}

	public void setSchoolClass(EnumSchoolClass schoolClass) {
		this.schoolClass = schoolClass;
	}

	public List<String> getRanks() {
		return Collections.unmodifiableList(this.ranks);
	}

	public Boolean addRank(String rank) {
		return this.ranks.add(rank);
	}

	public Boolean removeRank(String rank) {
		return this.ranks.remove(rank);
	}

	@Override
	public EnumAccountOwnerType getOwnerType() {
		return EnumAccountOwnerType.USER;
	}

	public Attendancedata getAttendancedata() {
		return attendancedata;
	}

	public Set<UserCard> getUserCards() {
		return Collections.unmodifiableSet(userCards);
	}

	public Boolean addUserCard(UserCard userCard) {
		return this.userCards.add(userCard);
	}

	public Set<Employment> getEmployments() {
		return Collections.unmodifiableSet(employments);
	}

	public Boolean addEmployment(Employment employment) {
		return employments.add(employment);
	}

	public Boolean removeEmployment(Employment employment) {
		return employments.remove(employment);
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}
}
