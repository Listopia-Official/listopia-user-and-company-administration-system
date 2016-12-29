package florian_haas.lucas.util;

@FunctionalInterface
public interface QuadFunction<T, U, V, W, R> {

	public R apply(T param1, U param2, V param3, W param4);

}
