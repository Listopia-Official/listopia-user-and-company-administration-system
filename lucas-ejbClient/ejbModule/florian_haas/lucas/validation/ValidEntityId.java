package florian_haas.lucas.validation;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.*;

import javax.validation.*;

import florian_haas.lucas.model.ReadOnlyEntity;

@Target({
		FIELD, METHOD, PARAMETER, ANNOTATION_TYPE, CONSTRUCTOR, TYPE_USE })
@Retention(RUNTIME)
@Constraint(validatedBy = ValidEntityIdValidator.class)
@Documented
public @interface ValidEntityId {

	String message() default "EntityId doesn't exist!";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	Class<? extends ReadOnlyEntity> entityClass();

	boolean nullable() default false;
}
