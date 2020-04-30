package org.fagu.calculationdetails;

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

	public int getIntegerLength() {
		return StringUtils.substringBefore(toString(), ",").length();
	}

	public int getFloatLength() {
		return StringUtils.substringAfter(toString(), ",").length();
	}

	@Override
	public String toString() {
		return number.toString().replace('.', ',');
	}
}
