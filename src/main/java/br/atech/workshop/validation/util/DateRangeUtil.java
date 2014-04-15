package br.atech.workshop.validation.util;

import java.util.Calendar;
import java.util.Date;

import javax.validation.ConstraintValidatorContext;

import br.atech.workshop.validation.date.DateRange;
import br.atech.workshop.validation.date.DateRange.Ranges;

/**
 * 
 * @author marcio
 * 
 */
public class DateRangeUtil {

	private int[] fields = { Calendar.YEAR, Calendar.MONTH,
			Calendar.DAY_OF_MONTH, Calendar.HOUR_OF_DAY, Calendar.MINUTE,
			Calendar.SECOND, Calendar.MILLISECOND };

	/**
	 * 
	 * @param date
	 * @param field
	 * @return
	 */
	public Date ceil(Date date, int field) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		for (int currentField : fields) {
			if (currentField > field) {
				if (currentField == Calendar.DAY_OF_MONTH
						&& (field == Calendar.WEEK_OF_MONTH || field == Calendar.WEEK_OF_YEAR)) {
					continue;
				}
				cal.set(currentField, cal.getActualMaximum(currentField));
			}
		}
		if (field == Calendar.WEEK_OF_MONTH || field == Calendar.WEEK_OF_YEAR) {
			if (cal.getFirstDayOfWeek() == Calendar.MONDAY) {
				cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
				cal.add(Calendar.DAY_OF_WEEK, 6);
			}
		}
		return cal.getTime();
	}

	/**
	 * 
	 * @param date
	 * @param field
	 * @return
	 */
	public Date floor(Date date, int field) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		for (int currentField : fields) {
			if (currentField > field) {
				if (currentField == Calendar.DAY_OF_MONTH
						&& (field == Calendar.WEEK_OF_MONTH || field == Calendar.WEEK_OF_YEAR)) {
					continue;
				}
				cal.set(currentField, cal.getActualMinimum(currentField));
			}
		}
		if (field == Calendar.WEEK_OF_MONTH || field == Calendar.WEEK_OF_YEAR) {
			cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
		}
		return cal.getTime();
	}

	/**
	 * 
	 * @param date
	 * @param field
	 * @param amount
	 * @return
	 */
	public Date add(Date date, int field, int amount) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(field, amount);
		return cal.getTime();
	}

	/**
	 * 
	 * @param date
	 * @param field
	 * @param amount
	 * @return
	 */
	public Date set(Date date, int field, int amount) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(field, amount);
		return cal.getTime();
	}

	/**
	 * 
	 * @param range
	 * @param minGap
	 * @return
	 */
	public Date getMinDate(DateRange.Ranges range, int minGap) {
		Calendar cal = Calendar.getInstance();
		cal.add(range.getPredefGapField(), range.getPredefGapAmountMin());
		cal.add(range.getGapField(), minGap);
		return floor(cal.getTime(), range.getPredefGapField());
	}

	/**
	 * 
	 * @param range
	 * @param maxGap
	 * @return
	 */
	public Date getMaxDate(DateRange.Ranges range, int maxGap) {
		Calendar cal = Calendar.getInstance();
		cal.add(range.getPredefGapField(), range.getPredefGapAmountMax());
		cal.add(range.getGapField(), maxGap);
		return ceil(cal.getTime(), range.getPredefGapField());
	}

	/**
	 * 
	 * @param annotation
	 * @param value
	 * @param context
	 * @return
	 */
	public boolean isValidMin(DateRange annotation, Date value,
			ConstraintValidatorContext context) {
		return isValidMin(annotation.min(), annotation.minGap(), value, context);
	}

	/**
	 * 
	 * @param min
	 * @param minGap
	 * @param value
	 * @param context
	 * @return
	 */
	private boolean isValidMin(DateRange.Ranges min, int minGap, Date value,
			ConstraintValidatorContext context) {
		if (value == null || min.equals(DateRange.Ranges.Any)) {
			return true;
		}
		return value.compareTo(getMinDate(min, minGap)) >= 0;
	}

	/**
	 * 
	 * @param annotation
	 * @param value
	 * @param context
	 * @return
	 */
	public boolean isValidMax(DateRange annotation, Date value,
			ConstraintValidatorContext context) {
		return isValidMax(annotation.max(), annotation.maxGap(), value, context);
	}

	/**
	 * 
	 * @param max
	 * @param maxGap
	 * @param value
	 * @param context
	 * @return
	 */
	private boolean isValidMax(DateRange.Ranges max, int maxGap, Date value,
			ConstraintValidatorContext context) {
		if (value == null || max.equals(DateRange.Ranges.Any)) {
			return true;
		}
		return value.compareTo(getMaxDate(max, maxGap)) <= 0;
	}

	/**
	 * 
	 * @param annotation
	 * @param value
	 * @param context
	 * @return
	 */
	public boolean isValid(DateRange annotation, Date value,
			ConstraintValidatorContext context) {
		for (Ranges predef : annotation.value()) {
			if (!isValidMax(predef, annotation.maxGap(), value, context)
					|| !isValidMin(predef, annotation.minGap(), value, context)) {
				return false;
			}
		}
		return true;
	}
}
