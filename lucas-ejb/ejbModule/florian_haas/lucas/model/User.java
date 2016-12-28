package florian_haas.lucas.model;

import java.util.*;

import javax.persistence.*;

@Entity
public class User extends AccountOwner {

	private static final long serialVersionUID = -2632198556827472552L;

	@Column(nullable = true)
	private String forename;

	@Column(nullable = true)
	private String surname;

	@Column(nullable = true)
	private Integer schoolGrade;

	@Column(nullable = true)
	private String schoolClass;

	@ElementCollection(fetch = FetchType.EAGER)
	private List<String> ranks = new ArrayList<>();

	@OneToOne(cascade = CascadeType.ALL, optional = true, orphanRemoval = true)
	private Attendancedata attendancedata;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY, mappedBy = "user")
	private Set<UserCard> userCards = new HashSet<>();

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY, mappedBy = "user")
	private Set<Visa> visa = new HashSet<>();

	User() {}

	public User(String forename, String surname, Integer schoolGrade, String schoolClass, String... ranks) {
		this.forename = forename;
		this.surname = surname;
		this.schoolGrade = schoolGrade;
		this.schoolClass = schoolClass;
		this.ranks.addAll(Arrays.asList(ranks));
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
		return (forename == null && surname == null ? EnumUserType.GUEST
				: (schoolGrade == null && schoolClass == null ? EnumUserType.TEACHER : EnumUserType.PUPIL));
	}

	public Integer getSchoolGrade() {
		return this.schoolGrade;
	}

	public void setSchoolGrade(Integer schoolGrade) {
		this.schoolGrade = schoolGrade;
	}

	public String getSchoolClass() {
		return this.schoolClass;
	}

	public void setSchoolClass(String schoolClass) {
		this.schoolClass = schoolClass;
	}

	public List<String> getRanks() {
		return Collections.unmodifiableList(this.ranks);
	}

	public boolean addRank(String rank) {
		return this.ranks.add(rank);
	}

	public boolean removeRank(String rank) {
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

	public boolean addUserCard(UserCard userCard) {
		return this.userCards.add(userCard);
	}

	public Set<Visa> getVisa() {
		return Collections.unmodifiableSet(visa);
	}

	public boolean addVisa(Visa visa) {
		return this.visa.add(visa);
	}
}
