package br.atech.workshop.validation.required;

import java.util.Collection;
import java.util.Map;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 
 * @author marcio
 * 
 */
public class RequiredValidator implements ConstraintValidator<Required, Object> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.validation.ConstraintValidator#initialize(java.lang.annotation.
	 * Annotation)
	 */
	@Override
	public void initialize(Required annotation) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.validation.ConstraintValidator#isValid(java.lang.Object,
	 * javax.validation.ConstraintValidatorContext)
	 */
	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		if (value == null) {
			return false;
		} else if (value instanceof String) {
			return !((String) value).trim().isEmpty();
		} else if (value instanceof Collection<?>) {
			return validateBySize(((Collection<?>) value).size(), context);
		} else if (value instanceof Map<?, ?>) {
			return validateBySize(((Map<?, ?>) value).size(), context);
		} else if (value.getClass().isArray()) {
			return validateBySize(((Object[]) value).length, context);
		} else {
			return true;
		}
	}

	/**
	 * 
	 * @param size
	 * @param context
	 * @return
	 */
	private boolean validateBySize(int size, ConstraintValidatorContext context) {
		if (size == 0) {
			// TODO Exercício 1.2
			buildConstraintViolation(context, "<<mensagem>>");
			return false;
		}
		return true;
	}

	/**
	 * 
	 * @param context
	 * @param message
	 */
	private void buildConstraintViolation(ConstraintValidatorContext context,
			String message) {
		context.disableDefaultConstraintViolation();
		context.buildConstraintViolationWithTemplate(message)
				.addConstraintViolation();
	}
}
