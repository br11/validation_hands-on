package br.atech.workshop.validation.required;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * 
 * @author marcio
 * 
 */
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE,
		ElementType.CONSTRUCTOR, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = RequiredValidator.class)
public @interface Required {

	/**
	 * 
	 * @author marcio
	 * 
	 */
	@Target({ ElementType.METHOD, ElementType.FIELD,
			ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR,
			ElementType.PARAMETER })
	@Retention(RetentionPolicy.RUNTIME)
	public static @interface List {
		Class<?>[] groups() default {};

		Class<? extends Payload>[] payload() default {};

		Required[] value();
	}

	// TODO Exercício 1.1
	String message() default "<<message>>";
	
	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
