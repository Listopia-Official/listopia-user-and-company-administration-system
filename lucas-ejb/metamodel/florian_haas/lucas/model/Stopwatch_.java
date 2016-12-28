package florian_haas.lucas.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2016-12-28T17:44:31.184+0100")
@StaticMetamodel(Stopwatch.class)
public class Stopwatch_ {
	public static volatile SingularAttribute<Stopwatch, Long> startTime;
	public static volatile SingularAttribute<Stopwatch, Long> tmpStartTime;
	public static volatile SingularAttribute<Stopwatch, Long> duration;
	public static volatile SingularAttribute<Stopwatch, Long> tmpDuration;
	public static volatile SingularAttribute<Stopwatch, Boolean> running;
}
