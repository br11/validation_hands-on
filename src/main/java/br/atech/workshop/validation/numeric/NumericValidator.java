package br.atech.workshop.validation.numeric;

import java.math.BigDecimal;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import br.atech.workshop.validation.required.RequiredValidator;
import br.atech.workshop.validation.util.NumericUtil;

public class NumericValidator implements ConstraintValidator<Numeric, Object> {

	private NumericUtil util = new NumericUtil();

	private RequiredValidator requiredValidator = new RequiredValidator();

	private int integerSize;
	private int fractionSize;

	private BigDecimal min;
	private BigDecimal max;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.validation.ConstraintValidator#initialize(java.lang.annotation.
	 * Annotation)
	 */
	@Override
	public void initialize(Numeric annotation) {
		this.requiredValidator.initialize(null);

		String[] parts = annotation.value().split("\\.");

		String integerPart = parts[0];
		integerSize = integerPart.length();

		String fractionPart = parts.length == 2 ? parts[1] : "";
		fractionSize = fractionPart.length();

		min = util.toBig(annotation.min());

		max = util.toBig(annotation.max()).min(util.maxValue(integerSize));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.validation.ConstraintValidator#isValid(java.lang.Object,
	 * javax.validation.ConstraintValidatorContext)
	 */
	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		if (!requiredValidator.isValid(value, context)) {
			return true;
		}

		BigDecimal big = util.toBig(value);

		if (big.compareTo(min) < 0 && big.compareTo(max) > 0) {
			buildConstraintViolation(context,
					"{br.atech.workshop.validation.Range.message}");
			return false;
		} else if (big.compareTo(min) < 0) {
			// TODO Exercício 2.2
			buildConstraintViolation(context, "<<message>>");
			return false;
		} else if (big.compareTo(max) > 0) {
			// TODO Exercício 2.2
			buildConstraintViolation(context, "<<message>>");
			return false;
		} else if (!util.isValid(value, integerSize, fractionSize)) {
			return false;
		}

		return true;
	}

	private void buildConstraintViolation(ConstraintValidatorContext context,
			String message) {
		context.disableDefaultConstraintViolation();
		context.buildConstraintViolationWithTemplate(message)
				.addConstraintViolation();
	}
}
