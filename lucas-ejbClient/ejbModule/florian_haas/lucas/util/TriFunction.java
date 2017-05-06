package florian_haas.lucas.util;

@FunctionalInterface
public interface TriFunction<T, U, V, R> {

	public R apply(T param1, U param2, V param3);

}
