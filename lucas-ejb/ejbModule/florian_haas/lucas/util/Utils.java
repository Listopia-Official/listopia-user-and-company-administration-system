package florian_haas.lucas.util;

import java.io.*;
import java.lang.reflect.ParameterizedType;
import java.math.BigDecimal;
import java.time.*;
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

	public static <T> Class<T> getClassFromArgs(Class<?> clazz) {
		return getClassFromArgs(clazz, 0);
	}

	@SuppressWarnings("unchecked")
	public static <T> Class<T> getClassFromArgs(Class<?> clazz, int arg) {
		return (Class<T>) ((ParameterizedType) clazz.getGenericSuperclass()).getActualTypeArguments()[arg];
	}

	public static Date asDate(LocalDate localDate) {
		return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
	}

	public static Date asDate(LocalDateTime localDateTime) {
		return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
	}

	public static LocalDate asLocalDate(Date date) {
		return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
	}

	public static LocalDateTime asLocalDateTime(Date date) {
		return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
	}

	public static final String getStackTraceAsString(Throwable t) {
		String ret = null;
		try (StringWriter writer = new StringWriter(); PrintWriter writer2 = new PrintWriter(writer)) {
			t.printStackTrace(writer2);
			ret = writer.toString();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return ret;
	}
}
