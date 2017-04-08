package florian_haas.lucas.model.validation;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.*;

import javax.validation.*;

import florian_haas.lucas.model.Item;

@Target({
		FIELD, METHOD, PARAMETER, ANNOTATION_TYPE, CONSTRUCTOR })
@Retention(RUNTIME)
@Constraint(validatedBy = {})
@Documented
@Unique(entityClass = Item.class, columnName = "name", message = "{lucas.application.validationConstraints.unique.message.item.name}")
public @interface UniqueItemName {

	String message() default "{lucas.application.validationConstraints.unique.message.item.name}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}
