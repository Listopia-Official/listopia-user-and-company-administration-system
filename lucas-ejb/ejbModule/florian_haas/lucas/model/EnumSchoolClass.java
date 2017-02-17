package florian_haas.lucas.model;

import java.util.*;
import java.util.function.Predicate;

import florian_haas.lucas.database.EnumQueryComparator;
import florian_haas.lucas.util.Utils;

public enum EnumSchoolClass {

	A5,
	B5,
	C5,
	D5,
	E5,
	A6,
	B6,
	C6,
	D6,
	E6,
	A7,
	B7,
	C7,
	D7,
	E7,
	A8,
	B8,
	C8,
	D8,
	E8,
	A9,
	B9,
	C9,
	D9,
	E9,
	A10,
	B10,
	C10,
	D10,
	E10,
	A11,
	B11,
	C11,
	D11,
	E11,
	A12,
	B12,
	C12,
	D12,
	E12;

	private final Integer grade;
	private final String schoolClass;

	private static final List<Integer> grades = new ArrayList<>();
	private static final List<String> classes = new ArrayList<>();

	static {
		for (EnumSchoolClass value : EnumSchoolClass.values()) {
			grades.add(value.grade);
			classes.add(value.schoolClass);
		}
	}

	private EnumSchoolClass() {
		this.grade = Integer.parseInt(this.name().substring(1, this.name().length()));
		this.schoolClass = Character.toString(this.name().charAt(0)).toLowerCase();
	}

	public Integer getGrade() {
		return this.grade;
	}

	public String getSchoolClass() {
		return this.schoolClass;
	}

	@Override
	public String toString() {
		return this.grade.toString() + this.schoolClass;
	}

	private static <T extends Comparable<? super T>> Predicate<T> getPredicateFromQueryComparator(EnumQueryComparator comparator,
			EnumQueryComparator[] possibleValues, T val) {
		Predicate<T> ret = val::equals;
		if (comparator != null && Arrays.asList(possibleValues).contains(comparator)) {
			switch (comparator) {
				case EQUAL:
				case LIKE:
				case MEMBER_OF:
				case NOT_LIKE:
				case NOT_MEMBER_OF:
					break;
				case GREATER_EQUAL:
					ret = (arg) -> {
						return Utils.isGreatherEqual(val, arg);
					};
					break;
				case GREATER_THAN:
					ret = (arg) -> {
						return Utils.isGreatherThan(val, arg);
					};
					break;
				case LESS_EQUAL:
					ret = (arg) -> {
						return Utils.isLessEqual(val, arg);
					};
					break;
				case LESS_THAN:
					ret = (arg) -> {
						return Utils.isLessThan(val, arg);
					};
					break;
				case NOT_EQUAL:
					ret = (arg) -> {
						return !arg.equals(val);
					};
					break;
			}
		}
		return ret;
	}

	public static Set<EnumSchoolClass> getMatchingClasses(Integer schoolGrade, String schoolClass, EnumQueryComparator schoolGradeComparator,
			EnumQueryComparator schoolClassComparator) {
		Set<EnumSchoolClass> ret = new HashSet<>();
		for (EnumSchoolClass value : EnumSchoolClass.values()) {
			Predicate<Integer> gradePred = getPredicateFromQueryComparator(schoolGradeComparator, EnumQueryComparator.getNumericComparators(),
					value.getGrade());
			Predicate<String> schoolClassPred = getPredicateFromQueryComparator(schoolClassComparator, EnumQueryComparator.getNumericComparators(),
					value.getSchoolClass());
			if (schoolGrade != null && schoolClass != null) {
				if ((schoolGrade != null && schoolClass != null && gradePred.test(schoolGrade) && schoolClassPred.test(schoolClass))
						|| (schoolGrade != null && schoolClass == null && gradePred.test(schoolGrade))
						|| (schoolGrade == null && schoolClass != null && schoolClassPred.test(schoolClass))) {
					ret.add(value);
				}
			}
		}
		return ret;
	}

	public static List<Integer> getGrades() {
		return Collections.unmodifiableList(grades);
	}

	public static List<String> getClasses() {
		return Collections.unmodifiableList(classes);
	}

}
