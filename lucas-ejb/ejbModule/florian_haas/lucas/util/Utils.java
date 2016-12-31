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

	public static boolean isLessThanZero(BigDecimal decimal) {
		return decimal.compareTo(BigDecimal.ZERO) < 0;
	}

	public static boolean isGreatherThanZero(BigDecimal decimal) {
		return decimal.compareTo(BigDecimal.ZERO) > 0;
	}

	public static boolean isGreatherEqualZero(BigDecimal decimal) {
		return decimal.compareTo(BigDecimal.ZERO) >= 0;
	}

	public static boolean isLessEqualZero(BigDecimal decimal) {
		return decimal.compareTo(BigDecimal.ZERO) <= 0;
	}

	public static boolean isZero(BigDecimal decimal) {
		return decimal.compareTo(BigDecimal.ZERO) == 0;
	}
}
