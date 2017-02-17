package florian_haas.lucas.database;

public enum EnumQueryComparator {
	EQUAL, NOT_EQUAL, GREATER_THAN, LESS_THAN, GREATER_EQUAL, LESS_EQUAL, MEMBER_OF, NOT_MEMBER_OF, LIKE, NOT_LIKE;

	public static EnumQueryComparator[] getNumericComparators() {
		return new EnumQueryComparator[] {
				EQUAL, NOT_EQUAL, GREATER_THAN, LESS_THAN, GREATER_EQUAL, LESS_EQUAL };
	}

	public static EnumQueryComparator[] getArrayComparators() {
		return new EnumQueryComparator[] {
				MEMBER_OF, NOT_MEMBER_OF };
	}

	public static EnumQueryComparator[] getTextComparators() {
		return new EnumQueryComparator[] {
				EQUAL, NOT_EQUAL, GREATER_THAN, LESS_THAN, GREATER_EQUAL, LESS_EQUAL, LIKE, NOT_LIKE };
	}
}
