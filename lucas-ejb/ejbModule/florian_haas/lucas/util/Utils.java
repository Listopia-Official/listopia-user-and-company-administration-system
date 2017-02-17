package florian_haas.lucas.util;

import java.math.BigDecimal;
import java.util.*;

public class Utils {

	@SuppressWarnings("unchecked")
	public static <K, V> Map<K, V> deepUnmodifiableMap(Map<K, V> map) {
		Map<K, V> tmp = new HashMap<>();
		map.forEach((key, value) -> {
			Object valueO = value;
			if (value instanceof Map<?, ?>) {
				Map<?, ?> val = deepUnmodifiableMap((Map<?, ?>) value);
				valueO = val;
			}
			tmp.put(key, (V) valueO);
		});
		return Collections.unmodifiableMap(tmp);
	}

	public static <T extends Comparable<? super T>> Boolean isEqual(T comp1, T comp2) {
		return comp1.compareTo(comp2) == 0;
	}

	public static <T extends Comparable<? super T>> Boolean isLessThan(T comp1, T comp2) {
		return comp1.compareTo(comp2) < 0;
	}

	public static <T extends Comparable<? super T>> Boolean isGreatherThan(T comp1, T comp2) {
		return comp1.compareTo(comp2) > 0;
	}

	public static <T extends Comparable<? super T>> Boolean isGreatherEqual(T comp1, T comp2) {
		return comp1.compareTo(comp2) >= 0;
	}

	public static <T extends Comparable<? super T>> Boolean isLessEqual(T comp1, T comp2) {
		return comp1.compareTo(comp2) <= 0;
	}

	public static Boolean isZero(BigDecimal decimal) {
		return isEqual(decimal, BigDecimal.ZERO);
	}

	public static Boolean isLessThanZero(BigDecimal decimal) {
		return isLessThan(decimal, BigDecimal.ZERO);
	}

	public static Boolean isGreatherThanZero(BigDecimal decimal) {
		return isGreatherThan(decimal, BigDecimal.ZERO);
	}

	public static Boolean isGreatherEqualZero(BigDecimal decimal) {
		return isGreatherEqual(decimal, BigDecimal.ZERO);
	}

	public static Boolean isLessEqualZero(BigDecimal decimal) {
		return isLessEqual(decimal, BigDecimal.ZERO);
	}

	public static boolean isInRangeInclusive(long start, long end, long value) {
		return value <= end && value >= start;
	}

	public static boolean isInRangeExclusive(long start, long end, long value) {
		return value < end && value > start;
	}

	public static boolean isInRangeInclusive(int start, int end, int value) {
		return value <= end && value >= start;
	}

	public static boolean isInRangeExclusive(int start, int end, int value) {
		return value < end && value > start;
	}
}
