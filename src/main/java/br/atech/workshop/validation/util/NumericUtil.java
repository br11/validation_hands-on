package br.atech.workshop.validation.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;

/**
 * 
 * @author marcio
 * 
 */
public class NumericUtil {

	/**
	 * 
	 * @param value
	 * @param integerSize
	 * @param fractionSize
	 * @return
	 */
	public boolean isValid(Object value, int integerSize, int fractionSize) {
		return isValidInteger(value, integerSize)
				&& isValidFraction(value, fractionSize);
	}

	/**
	 * 
	 * @param value
	 * @param integerSize
	 * @return
	 */
	public boolean isValidInteger(Object value, int integerSize) {
		BigDecimal big = toBig(value);
		int precision = getPrecision(value);

		big = big.round(new MathContext(precision, RoundingMode.HALF_UP));
		BigDecimal multi = new BigDecimal("10").pow(integerSize,
				new MathContext(precision, RoundingMode.HALF_UP));
		return big.compareTo(multi) < 0;
	}

	/**
	 * 
	 * @param value
	 * @param fractionSize
	 * @return
	 */
	public boolean isValidFraction(Object value, int fractionSize) {
		BigDecimal big = toBig(value);
		int precision = getPrecision(value);

		try {
			big = big.round(new MathContext(precision, RoundingMode.HALF_UP));
			int multi = (int) Math.round(Math.pow(10, fractionSize));
			big = big.multiply(new BigDecimal(multi));
			big.longValueExact();
		} catch (ArithmeticException e) {
			return false;
		}

		return true;
	}

	/**
	 * 
	 * @param value
	 * @return
	 */
	public BigDecimal toBig(Object value) {
		BigDecimal big;
		if (value instanceof BigDecimal) {
			big = (BigDecimal) value;
		} else if (value instanceof BigInteger) {
			big = new BigDecimal((BigInteger) value);
		} else if (value instanceof Double) {
			big = new BigDecimal(((Number) value).doubleValue());
		} else if (value instanceof Float) {
			big = new BigDecimal(((Number) value).floatValue());
		} else if (value instanceof Number) {
			big = new BigDecimal(((Number) value).longValue());
		} else {
			big = new BigDecimal(value.toString());
		}
		return big;
	}

	/**
	 * 
	 * @param value
	 * @return
	 */
	public int getPrecision(Object value) {
		if (value instanceof Float) {
			return 7;
		} else {
			return 15;
		}
	}

	/**
	 * 
	 * @param power
	 * @return
	 */
	public BigDecimal powerOfTen(int power) {
		return new BigDecimal(Math.round(Math.pow(10, power)));
	}

	/**
	 * 
	 * @param size
	 * @return
	 */
	public BigDecimal maxValue(int size) {
		return powerOfTen(size).subtract(new BigDecimal(1));
	}
}
