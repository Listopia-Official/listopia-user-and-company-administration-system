package florian_haas.lucas.util;

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
}
