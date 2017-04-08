package florian_haas.lucas.model.validation;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.*;

import javax.validation.*;

import florian_haas.lucas.model.LoginUser;

@Target({
		FIELD, METHOD, PARAMETER, ANNOTATION_TYPE, CONSTRUCTOR })
@Retention(RUNTIME)
@Constraint(validatedBy = {})
@Documented
@Unique(entityClass = LoginUser.class, columnName = "username", message = "{lucas.application.validationConstraints.unique.message.loginUser.username}")
public @interface UniqueLoginUserUsername {

	String message() default "{lucas.application.validationConstraints.unique.message.loginUser.username}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}
