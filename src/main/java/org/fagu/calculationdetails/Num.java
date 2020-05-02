package org.fagu.calculationdetails;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.fagu.calculationdetails.utils.StringUtils;


public class Num {

	private final Number number;

	public Num(Number number) {
		this.number = Objects.requireNonNull(number);
	}

	public Number getNumber() {
		return number;
	}

	public String getIntegerString() {
		return StringUtils.substringBefore(toString(), ",");
	}

	public int getIntegerLength() {
		return getIntegerString().length();
	}

	public String getFloatString() {
		return StringUtils.substringAfter(toString(), ",");
	}

	public int getFloatLength() {
		return getFloatString().length();
	}

	public boolean hasFloat() {
		return getFloatLength() > 0;
	}

	public Num add(Num other) {
		return operate(other,
				(v1, v2) -> new Num(v1 + v2),
				(v1, v2) -> new Num(v1 + v2),
				(v1, v2) -> new Num(v1 + v2),
				(v1, v2) -> new Num(v1 + v2));
	}

	public Num substract(Num other) {
		return operate(other,
				(v1, v2) -> new Num(v1 - v2),
				(v1, v2) -> new Num(v1 - v2),
				(v1, v2) -> new Num(v1 - v2),
				(v1, v2) -> new Num(v1 - v2));
	}

	public Num multiply(Num other) {
		return operate(other,
				(v1, v2) -> new Num(v1 * v2),
				(v1, v2) -> new Num(v1 * v2),
				(v1, v2) -> new Num(v1 * v2),
				(v1, v2) -> new Num(v1 * v2));
	}

	public Num divide(Num other) {
		return operate(other,
				(v1, v2) -> new Num(v1 / v2),
				(v1, v2) -> new Num((double)v1 / v2),
				(v1, v2) -> new Num((double)v1 / v2),
				(v1, v2) -> new Num((double)v1 / v2));
	}

	public boolean isOver(Num other) {
		return is(other,
				(v1, v2) -> Math.abs(v1) > Math.abs(v2),
				(v1, v2) -> Math.abs(v1) > Math.abs(v2),
				(v1, v2) -> Math.abs(v1) > Math.abs(v2),
				(v1, v2) -> Math.abs(v1) > Math.abs(v2));
	}

	public boolean isOverOrEquals(Num other) {
		return is(other,
				(v1, v2) -> Math.abs(v1) >= Math.abs(v2),
				(v1, v2) -> Math.abs(v1) >= Math.abs(v2),
				(v1, v2) -> Math.abs(v1) >= Math.abs(v2),
				(v1, v2) -> Math.abs(v1) >= Math.abs(v2));
	}

	public boolean isUnder(Num other) {
		return is(other,
				(v1, v2) -> Math.abs(v1) < Math.abs(v2),
				(v1, v2) -> Math.abs(v1) < Math.abs(v2),
				(v1, v2) -> Math.abs(v1) < Math.abs(v2),
				(v1, v2) -> Math.abs(v1) < Math.abs(v2));
	}

	public boolean isUnderOrEquals(Num other) {
		return is(other,
				(v1, v2) -> Math.abs(v1) <= Math.abs(v2),
				(v1, v2) -> Math.abs(v1) <= Math.abs(v2),
				(v1, v2) -> Math.abs(v1) <= Math.abs(v2),
				(v1, v2) -> Math.abs(v1) <= Math.abs(v2));
	}

	public Num roundFloor() {
		return round(
				v -> new Num(Math.floor(v.number.doubleValue())),
				v -> new Num((float)Math.floor(v.number.floatValue())),
				v -> v,
				v -> v);
	}

	public Num roundFloor(int countFloat) {
		double tenPow = Math.pow(10, countFloat);
		return round(
				v -> new Num(((long)(v.number.doubleValue() * tenPow)) / tenPow),
				v -> new Num(((long)(v.number.floatValue() * tenPow)) / tenPow),
				v -> v,
				v -> v);
	}

	public int length() {
		return toString().length();
	}

	public List<Num> decompose() {
		List<Num> list = new ArrayList<>();
		String floatStr = getFloatString();
		for(int i = 0; i < floatStr.length(); ++i) {
			int value = Integer.parseInt(Character.toString(floatStr.charAt(i)));
			list.add(new Num(value / Math.pow(10, i + 1)));
		}
		String intStr = getIntegerString();
		int intLength = intStr.length();
		for(int i = 0; i < intLength; ++i) {
			int value = Integer.parseInt(Character.toString(intStr.charAt(i)));
			// list.add(i, new Num(value * (long)Math.pow(10, intLength - i - 1)));
			list.add(i, new Num(value));
		}
		return list;
	}

	@Override
	public String toString() {
		return number.toString().replace('.', ',');
	}

	// ******************************************************

	private Num operate(Num other, OperateDouble doubleOp, OperateFloat floatOp, OperateLong longOp, OperateInt intOp) {
		Number nother = other.getNumber();
		if(number instanceof Double || nother instanceof Double) {
			return doubleOp.operate(number.doubleValue(), nother.doubleValue());
		}
		if(number instanceof Float || nother instanceof Float) {
			return floatOp.operate(number.floatValue(), nother.floatValue());
		}
		if(number instanceof Long || nother instanceof Long) {
			return longOp.operate(number.longValue(), nother.longValue());
		}
		return intOp.operate(number.intValue(), nother.intValue());
	}

	private boolean is(Num other, IsDouble doubleIs, IsFloat floatIs, IsLong longIs, IsInt intIs) {
		Number nother = other.getNumber();
		if(number instanceof Double || nother instanceof Double) {
			return doubleIs.is(number.doubleValue(), nother.doubleValue());
		}
		if(number instanceof Float || nother instanceof Float) {
			return floatIs.is(number.floatValue(), nother.floatValue());
		}
		if(number instanceof Long || nother instanceof Long) {
			return longIs.is(number.longValue(), nother.longValue());
		}
		return intIs.is(number.intValue(), nother.intValue());
	}

	private Num round(RoundDouble doubleRound, RoundFloat floatRound, RoundLong longRound, RoundInt intRound) {
		if(number instanceof Double) {
			return doubleRound.round(this);
		}
		if(number instanceof Float) {
			return floatRound.round(this);
		}
		if(number instanceof Long) {
			return longRound.round(this);
		}
		return intRound.round(this);
	}

	// -------------------------------------------------

	@FunctionalInterface
	private static interface OperateDouble {

		Num operate(double d1, double d2);
	}

	// -------------------------------------------------

	@FunctionalInterface
	private static interface OperateFloat {

		Num operate(float d1, float d2);
	}

	// -------------------------------------------------

	@FunctionalInterface
	private static interface OperateLong {

		Num operate(long d1, long d2);
	}

	// -------------------------------------------------

	@FunctionalInterface
	private static interface OperateInt {

		Num operate(int d1, int d2);
	}

	// -------------------------------------------------

	@FunctionalInterface
	private static interface IsDouble {

		boolean is(double d1, double d2);
	}

	// -------------------------------------------------

	@FunctionalInterface
	private static interface IsFloat {

		boolean is(float d1, float d2);
	}

	// -------------------------------------------------

	@FunctionalInterface
	private static interface IsLong {

		boolean is(long d1, long d2);
	}

	// -------------------------------------------------

	@FunctionalInterface
	private static interface IsInt {

		boolean is(int d1, int d2);
	}
	// -------------------------------------------------

	@FunctionalInterface
	private static interface RoundDouble {

		Num round(Num n);
	}

	// -------------------------------------------------

	@FunctionalInterface
	private static interface RoundFloat {

		Num round(Num n);
	}

	// -------------------------------------------------

	@FunctionalInterface
	private static interface RoundLong {

		Num round(Num n);
	}

	// -------------------------------------------------

	@FunctionalInterface
	private static interface RoundInt {

		Num round(Num n);
	}
}
