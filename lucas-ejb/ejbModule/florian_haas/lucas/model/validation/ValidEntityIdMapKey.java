package florian_haas.lucas.model.validation;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.*;

import javax.validation.*;

import florian_haas.lucas.model.EntityBase;

@Target({
		FIELD, METHOD, PARAMETER, ANNOTATION_TYPE, CONSTRUCTOR })
@Retention(RUNTIME)
@Constraint(validatedBy = ValidEntityIdMapKeyValidator.class)
@Documented
public @interface ValidEntityIdMapKey {

	String message() default "EntityId doesn't exist!";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	Class<? extends EntityBase> entityClass();
}
