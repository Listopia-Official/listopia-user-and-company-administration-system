package florian_haas.lucas.database;

public enum EnumQueryComparatorCategory {

	NUMERIC, TEXT, ARRAY, LOGIC;

	public EnumQueryComparator[] getComparators() {
		switch (EnumQueryComparatorCategory.valueOf(this.name())) {
			case ARRAY:
				return EnumQueryComparator.getArrayComparators();
			case NUMERIC:
				return EnumQueryComparator.getNumericComparators();
			case TEXT:
				return EnumQueryComparator.getTextComparators();
			case LOGIC:
				return EnumQueryComparator.getLogicComparators();
			default:
				return null;
		}
	}
}
