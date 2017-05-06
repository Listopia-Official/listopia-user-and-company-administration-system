package florian_haas.lucas.persistence;

import java.lang.annotation.*;

import javax.inject.Qualifier;

@Qualifier
@Target({
		ElementType.TYPE, ElementType.METHOD, ElementType.PARAMETER, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface JPADAO {

}
