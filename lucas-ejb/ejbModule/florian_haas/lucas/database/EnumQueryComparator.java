package florian_haas.lucas.database;

public enum EnumQueryComparator {
	EQUAL, NOT_EQUAL, GREATER_THAN, LESS_THAN, GREATER_EQUAL, LESS_EQUAL, MEMBER_OF, NOT_MEMBER_OF, LIKE, NOT_LIKE;

	private static final EnumQueryComparator[] numericComparators = new EnumQueryComparator[] {
			EQUAL, NOT_EQUAL, GREATER_THAN, LESS_THAN, GREATER_EQUAL, LESS_EQUAL };

	private static final EnumQueryComparator[] arrayComparators = new EnumQueryComparator[] {
			MEMBER_OF, NOT_MEMBER_OF };

	private static final EnumQueryComparator[] textComparators = new EnumQueryComparator[] {
			EQUAL, NOT_EQUAL, GREATER_THAN, LESS_THAN, GREATER_EQUAL, LESS_EQUAL, LIKE, NOT_LIKE };

	private static final EnumQueryComparator[] logicComparators = new EnumQueryComparator[] {
			EQUAL, NOT_EQUAL };

	public static EnumQueryComparator[] getNumericComparators() {
		return numericComparators;
	}

	public static EnumQueryComparator[] getArrayComparators() {
		return arrayComparators;
	}

	public static EnumQueryComparator[] getTextComparators() {
		return textComparators;
	}

	public static EnumQueryComparator[] getLogicComparators() {
		return logicComparators;
	}
}
