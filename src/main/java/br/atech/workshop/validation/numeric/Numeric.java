package br.atech.workshop.validation.numeric;

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
@Constraint(validatedBy = NumericValidator.class)
public @interface Numeric {

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

		Numeric[] value();
	}

	public static final String CURRENCY = "#########.##";

	// TODO Exercício 2.1
	String message() default "<<message>>";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	String value();

	double min() default Double.MIN_VALUE;

	double max() default Double.MAX_VALUE;
}
