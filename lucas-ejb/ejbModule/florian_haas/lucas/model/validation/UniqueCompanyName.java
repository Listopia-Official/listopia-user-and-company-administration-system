package florian_haas.lucas.model.validation;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.*;

import javax.validation.*;

import florian_haas.lucas.model.Company;

@Target({
		FIELD, METHOD, PARAMETER, ANNOTATION_TYPE, CONSTRUCTOR })
@Retention(RUNTIME)
@Constraint(validatedBy = {})
@Documented
@Unique(entityClass = Company.class, columnName = "name", message = "{lucas.application.validationConstraints.unique.message.company.name}")
public @interface UniqueCompanyName {

	String message() default "{lucas.application.validationConstraints.unique.message.company.name}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}
