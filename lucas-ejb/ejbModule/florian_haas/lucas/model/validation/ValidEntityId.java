package florian_haas.lucas.model.validation;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.*;

import javax.validation.*;
import javax.validation.constraints.NotNull;

import florian_haas.lucas.model.EntityBase;

@Target({
		FIELD, METHOD, PARAMETER, ANNOTATION_TYPE, CONSTRUCTOR, TYPE_USE })
@Retention(RUNTIME)
@Constraint(validatedBy = ValidEntityIdValidator.class)
@Documented
@NotNull
public @interface ValidEntityId {

	String message() default "AccountId doesn't exist!";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	Class<? extends EntityBase> entityClass();
}
