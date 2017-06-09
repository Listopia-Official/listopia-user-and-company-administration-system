package florian_haas.lucas.persistence;

public enum EnumQueryComparator {
	EQUAL, NOT_EQUAL, GREATER_THAN, LESS_THAN, GREATER_EQUAL, LESS_EQUAL, MEMBER_OF, NOT_MEMBER_OF, LIKE, NOT_LIKE, NULL, NOT_NULL, EMPTY, NOT_EMPTY;

	public static final EnumQueryComparator[] NUMERIC_COMPARATORS = new EnumQueryComparator[] {
			EQUAL, NOT_EQUAL, GREATER_THAN, LESS_THAN, GREATER_EQUAL, LESS_EQUAL, NULL, NOT_NULL };

	public static final EnumQueryComparator[] NUMERIC_COMPARATORS_NOT_NULL = new EnumQueryComparator[] {
			EQUAL, NOT_EQUAL, GREATER_THAN, LESS_THAN, GREATER_EQUAL, LESS_EQUAL };

	public static final EnumQueryComparator[] ARRAY_COMPARATORS = new EnumQueryComparator[] {
			MEMBER_OF, NOT_MEMBER_OF, EMPTY, NOT_EMPTY };

	public static final EnumQueryComparator[] TEXT_COMPARATORS = new EnumQueryComparator[] {
			EQUAL, NOT_EQUAL, GREATER_THAN, LESS_THAN, GREATER_EQUAL, LESS_EQUAL, LIKE, NOT_LIKE, NOT_NULL, NULL };

	public static final EnumQueryComparator[] TEXT_COMPARATORS_NOT_NULL = new EnumQueryComparator[] {
			EQUAL, NOT_EQUAL, GREATER_THAN, LESS_THAN, GREATER_EQUAL, LESS_EQUAL, LIKE, NOT_LIKE };

	public static final EnumQueryComparator[] LOGIC_COMPARATORS = new EnumQueryComparator[] {
			EQUAL, NOT_EQUAL, NOT_NULL, NULL };

	public static final EnumQueryComparator[] LOGIC_COMPARATORS_NOT_NULL = new EnumQueryComparator[] {
			EQUAL, NOT_EQUAL };
}
