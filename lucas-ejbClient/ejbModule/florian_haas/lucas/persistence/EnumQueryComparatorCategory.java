package florian_haas.lucas.persistence;

public enum EnumQueryComparatorCategory {

	NUMERIC, TEXT, ARRAY, LOGIC;

	public EnumQueryComparator[] getComparators() {
		switch (EnumQueryComparatorCategory.valueOf(this.name())) {
			case ARRAY:
				return EnumQueryComparator.ARRAY_COMPARATORS;
			case NUMERIC:
				return EnumQueryComparator.NUMERIC_COMPARATORS;
			case TEXT:
				return EnumQueryComparator.TEXT_COMPARATORS;
			case LOGIC:
				return EnumQueryComparator.LOGIC_COMPARATORS;
			default:
				return null;
		}
	}
}
