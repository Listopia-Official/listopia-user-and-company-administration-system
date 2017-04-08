package florian_haas.lucas.model.validation;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.*;

import javax.validation.*;

import florian_haas.lucas.model.LoginUserRole;

@Target({
		FIELD, METHOD, PARAMETER, ANNOTATION_TYPE, CONSTRUCTOR })
@Retention(RUNTIME)
@Constraint(validatedBy = {})
@Documented
@Unique(entityClass = LoginUserRole.class, columnName = "name", message = "{lucas.application.validationConstraints.unique.message.loginUserRole}")
public @interface UniqueLoginUserRoleName {

	String message() default "{lucas.application.validationConstraints.unique.message.loginUserRole}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}
