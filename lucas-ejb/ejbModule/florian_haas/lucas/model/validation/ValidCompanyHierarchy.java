package florian_haas.lucas.model.validation;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.*;

import javax.validation.*;

@Target({
		TYPE, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = ValidCompanyHierarchyValidator.class)
@Documented
public @interface ValidCompanyHierarchy {
	String message() default "Company Hierarchy isn't valid";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
