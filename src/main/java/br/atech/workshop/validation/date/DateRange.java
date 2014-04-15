package br.atech.workshop.validation.date;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Calendar;

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
@Constraint(validatedBy = DateRangeValidator.class)
public @interface DateRange {

	/**
	 * 
	 */
	public enum Ranges {
		Any(), //

		Now(Calendar.MINUTE, 0, 0), //

		Past(Calendar.DAY_OF_MONTH, -365 * 2500, -1), //
		Future(Calendar.DAY_OF_MONTH, 1, +365 * 2500), //

		Today(Calendar.DAY_OF_MONTH, 0, 0), //
		Yesterday(Calendar.DAY_OF_MONTH, -1, -1), //
		Tomorrow(Calendar.DAY_OF_MONTH, +1, +1), //

		ThisWeek(Calendar.WEEK_OF_YEAR, 0, 0), //
		LastWeek(Calendar.WEEK_OF_YEAR, -1, -1), //
		NextWeek(Calendar.WEEK_OF_YEAR, +1, +1), //

		ThisMonth(Calendar.MONTH, 0, 0), //
		LastMonth(Calendar.MONTH, -1, -1), //
		NextMonth(Calendar.MONTH, +1, +1), //

		ThisYear(Calendar.YEAR, 0, 0), //
		LastYear(Calendar.YEAR, -1, -1), //
		NextYear(Calendar.YEAR, +1, +1); //

		private int predefGapField;
		private int predefGapAmountMin;
		private int predefGapAmountMax;
		private int gapField;

		private Ranges() {

		}

		/**
		 * 
		 * @param predefGapField
		 * @param predefGapAmountMin
		 * @param predefGapAmountMax
		 */
		private Ranges(int predefGapField, int predefGapAmountMin,
				int predefGapAmountMax) {
			this(predefGapField, predefGapAmountMin, predefGapAmountMax,
					predefGapField);
		}

		/**
		 * 
		 * @param predefGapField
		 * @param predefGapAmountMin
		 * @param predefGapAmountMax
		 * @param gapField
		 */
		private Ranges(int predefGapField, int predefGapAmountMin,
				int predefGapAmountMax, int gapField) {
			this.predefGapField = predefGapField;
			this.predefGapAmountMin = predefGapAmountMin;
			this.predefGapAmountMax = predefGapAmountMax;
			this.gapField = gapField;
		}

		/**
		 * @return the errorMessage
		 */
		public String getErrorMessage() {
			return getClass().getName() + "." + name();
		}

		/**
		 * @return the predefGapField
		 */
		public int getPredefGapField() {
			return predefGapField;
		}

		/**
		 * @param predefGapField
		 *            the predefGapField to set
		 */
		public void setPredefGapField(int predefGapField) {
			this.predefGapField = predefGapField;
		}

		/**
		 * @return the predefGapAmountMin
		 */
		public int getPredefGapAmountMin() {
			return predefGapAmountMin;
		}

		/**
		 * @param predefGapAmountMin
		 *            the predefGapAmountMin to set
		 */
		public void setPredefGapAmountMin(int predefGapAmountMin) {
			this.predefGapAmountMin = predefGapAmountMin;
		}

		/**
		 * @return the predefGapAmountMax
		 */
		public int getPredefGapAmountMax() {
			return predefGapAmountMax;
		}

		/**
		 * @param predefGapAmountMax
		 *            the predefGapAmountMax to set
		 */
		public void setPredefGapAmountMax(int predefGapAmountMax) {
			this.predefGapAmountMax = predefGapAmountMax;
		}

		/**
		 * @return the gapField
		 */
		public int getGapField() {
			return gapField;
		}

		/**
		 * @param gapField
		 *            the gapField to set
		 */
		public void setGapField(int gapField) {
			this.gapField = gapField;
		}

	}

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

		DateRange[] value();
	}

	// TODO Exercício 3.1
	String message() default "<<mensagem>>";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	Ranges min() default Ranges.Past;

	Ranges max() default Ranges.Future;

	Ranges[] value() default Ranges.Any;

	int minGap() default 0;

	int maxGap() default 0;
}
