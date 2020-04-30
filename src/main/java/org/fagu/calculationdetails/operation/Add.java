package org.fagu.calculationdetails.operation;

import org.fagu.calculationdetails.Num;
import org.fagu.calculationdetails.Operation;
import org.fagu.calculationdetails.utils.StringUtils;


public class Add extends Operation {

	public Add(Num operandLeft, Num operandRight) {
		super(operandLeft, operandRight);
	}

	@Override
	public void showDetails() {
		final int globalPadding = 6;

		String s1 = toString(operandLeft);
		String s2 = toString(operandRight);

		int int1 = StringUtils.substringBefore(s1, ",").length();
		int int2 = StringUtils.substringBefore(s2, ",").length();
		int maxIntLength = Math.max(operandLeft.getIntegerLength(), operandRight.getIntegerLength());
		int maxFloatLength = Math.max(operandLeft.getFloatLength(), operandRight.getFloatLength());

		String result = toString(addNumbers(operandLeft.getNumber(), operandRight.getNumber()));
		int paddingResult = globalPadding + maxIntLength - StringUtils.substringBefore(result, ",").length();

		System.out.println(StringUtils.repeat(' ', globalPadding) + StringUtils.repeat(' ', maxIntLength - int1) + s1);
		System.out.println(StringUtils.repeat(' ', globalPadding - 2) + "+ " + StringUtils.repeat(' ', maxIntLength - int2) + s2);
		System.out.println(StringUtils.repeat(' ', globalPadding - 2) + "──" + StringUtils.repeat('─', maxLength));
		System.out.println(StringUtils.repeat(' ', paddingResult) + result);
	}

	private static Number addNumbers(Number a, Number b) {
		if(a instanceof Double || b instanceof Double) {
			return a.doubleValue() + b.doubleValue();
		}
		if(a instanceof Float || b instanceof Float) {
			return a.floatValue() + b.floatValue();
		}
		if(a instanceof Long || b instanceof Long) {
			return a.longValue() + b.longValue();
		}
		return a.intValue() + b.intValue();
	}
}
